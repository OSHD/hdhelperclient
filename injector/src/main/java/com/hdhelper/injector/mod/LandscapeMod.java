package com.hdhelper.injector.mod;

import com.hdhelper.injector.patch.GClass;
import com.hdhelper.injector.patch.GField;
import com.hdhelper.injector.patch.GMethod;
import com.hdhelper.injector.patch.GPatch;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.*;

import java.util.List;
import java.util.Map;

import static org.objectweb.asm.Opcodes.*;

public class LandscapeMod {

    private Map<String,ClassNode> classes;
    private GPatch patch;

    private String landscape;

    public LandscapeMod(Map<String,ClassNode> classes, GPatch patch) {
        this.classes = classes;
        this.patch = patch;




        this.landscape = patch.getGClass("Landscape").getName();
    }

    public void inject(Map<String,ClassNode> classes, GPatch patch) {



        GClass ls_g = patch.getGClass("Landscape");
        GClass lsTile = patch.getGClass("LandscapeTile");
        GClass marker = patch.getGClass("EntityMarker");

        ClassNode ls = classes.get(ls_g.getName());

        GField td = lsTile.getField("tileDecorationStub");
        GField b = lsTile.getField("boundaryStub");
        GField bd = lsTile.getField("boundaryDecorationStub");

        for(MethodNode mn : (List<MethodNode>) ls.methods) {
            addFieldChangePipe(mn, td,"setTileDeco", "(L" + lsTile.getName() + ";" + td.getDesc() + ")V");
            addFieldChangePipe(mn, b,"setBoundary", "(L" + lsTile.getName() + ";" + b.getDesc() + ")V");
            addFieldChangePipe(mn, bd,"setBoundaryDeco", "(L" + lsTile.getName() + ";" + bd.getDesc() + ")V");
        }





        MethodNode mn = get(ls, ls_g.getMethod("addEntityMarker"));

        int marker_var = -1;
        for(AbstractInsnNode ain : mn.instructions.toArray()) {
            if(ain.getOpcode() == INVOKESPECIAL) {
                MethodInsnNode min = (MethodInsnNode) ain;
                if(min.name.equals("<init>") && min.owner.equals(marker.getName())) {
                    marker_var = ((VarInsnNode)ain.getNext()).var;
                    break;
                }
            }
        }
        if(marker_var == -1) throw new Error("eek");

        for(AbstractInsnNode ain : mn.instructions.toArray()) {

            if(ain.getOpcode() == IRETURN) {
                boolean f = ain.getPrevious().getOpcode() == ICONST_0;
                if(f) {
                    InsnList stack = new InsnList();
                    stack.add(new InsnNode(ACONST_NULL));
                    stack.add(new FieldInsnNode(PUTSTATIC,landscape,"ret","L" + marker.getName() + ";"));
                    stack.add(new VarInsnNode(ILOAD,11));
                    stack.add(new FieldInsnNode(PUTSTATIC,landscape,"temp","Z"));
                    mn.instructions.insertBefore(ain,stack);
                } else {
                    InsnList stack = new InsnList();
                    stack.add(new VarInsnNode(ALOAD,marker_var));
                    stack.add(new FieldInsnNode(PUTSTATIC,landscape,"ret","L" + marker.getName() + ";"));
                    stack.add(new VarInsnNode(ILOAD,11));
                    stack.add(new FieldInsnNode(PUTSTATIC,landscape,"temp","Z"));
                    mn.instructions.insertBefore(ain,stack);
                }
            }

        }

        GMethod addObject = ls_g.getMethod("addObject");
        MethodNode addObject0 = get(ls, addObject);
        LabelNode A = new LabelNode(new Label());
        addObject0.visitLabel(A.getLabel());
        for(AbstractInsnNode ain : addObject0.instructions.toArray()) {
            if(ain.getOpcode() == IRETURN) {
                if(ain.getPrevious().getOpcode() != ICONST_1) { // default return without trying to add
                    InsnList stack = new InsnList();
                    stack.add(new InsnNode(DUP));
                    stack.add(new JumpInsnNode(IFEQ, A)); //failed to add the object
                    stack.add(new MethodInsnNode(INVOKESTATIC,landscape,"objectAdded","()V",false));
                    stack.add(A);
                    mn.instructions.insertBefore(ain,stack);
                }
            }
        }


        GMethod removeObject = ls_g.getMethod("removeObject");
        MethodNode removeObject0 = get(ls,removeObject);
        for(AbstractInsnNode ain : removeObject0.instructions.toArray()) {
            if(ain.getOpcode() == INVOKEVIRTUAL) {
                InsnList stack = new InsnList();
                stack.add(new VarInsnNode(ALOAD,6)); //TODO might not be constant
                stack.add(new MethodInsnNode(INVOKESTATIC,landscape,"objectRemoved","(L" + marker.getName() + ";)V",false));
                mn.instructions.insert(ain,stack);
            }
        }

    }

    private static MethodNode get(ClassNode cn, GMethod m) {
        if(!m.getOwner().equals(cn.name)) return null;
        for(MethodNode mn : (List<MethodNode>) cn.methods) {
            if(mn.name.equals(m.getName()) && mn.desc.equals(m.getDesc())) {
                return mn;
            }
        }
        return null;
    }

    void addFieldChangePipe(MethodNode mn, GField field, String methodName, String methodDesc) {
        for(AbstractInsnNode ain : mn.instructions.toArray()) {
            if(ain.getOpcode() == PUTFIELD) {
                FieldInsnNode fin = (FieldInsnNode) ain;
                if(fin.owner.equals(field.getOwner())
                        && fin.name.equals(field.getName())
                        && fin.desc.equals(field.getDesc())) {

                    InsnList stack = new InsnList();
                    stack.add(new InsnNode(DUP2));
                    stack.add(new MethodInsnNode(INVOKESTATIC, landscape, methodName, methodDesc, false));
                    mn.instructions.insertBefore(ain,stack);

                }
            }
        }
    }

}
