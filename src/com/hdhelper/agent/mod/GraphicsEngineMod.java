package com.hdhelper.agent.mod;

import com.hdhelper.agent.Callback;
import com.hdhelper.agent.mod.mem.MethodMember;
import jdk.internal.org.objectweb.asm.tree.*;
import jdk.nashorn.internal.codegen.types.Type;

import java.awt.*;
import java.util.Map;

public class GraphicsEngineMod extends InjectionModule {

    public static final String TEXT_RENDER = "hv";
    public static final String TEXT_RENDER_DESC = "L" + TEXT_RENDER + ";";

    public static final MethodMember DRAW_STRING;

    static {

        DRAW_STRING = new MethodMember(TEXT_RENDER,"y",
                Type.getMethodDescriptor(Void.TYPE,String.class,int.class,int.class,int.class,int.class),0);


    }

    @Override
    public void inject(Map<String, ClassNode> classes) {

        hack(classes.get("cr"));
        hack(classes.get("bv"));


    }

    void hack(ClassNode cn) {
        for(MethodNode mn : cn.methods) {
            for(AbstractInsnNode ain : mn.instructions.toArray()) {
                if(ain.getOpcode() == PUTFIELD) {
                    FieldInsnNode fin = (FieldInsnNode) ain;
                    if(fin.owner.equals(cn.name) && fin.name.equals("q")) {
                        InsnList stack = new InsnList();
                        stack.add(new VarInsnNode(ALOAD,0));
                        stack.add(new FieldInsnNode(GETFIELD,cn.name,"r","[I"));
                        stack.add(new VarInsnNode(ALOAD,0));
                        stack.add(new FieldInsnNode(GETFIELD,cn.name,"q","L" + Type.getInternalName(Image.class) + ";"));
                        stack.add(new MethodInsnNode(INVOKESTATIC,Type.getInternalName(Callback.class),"reshape",Type.getMethodDescriptor(Void.TYPE, int[].class, Image.class),false));
                        mn.instructions.insert(fin, stack);

                        System.out.println("RENDER-HACK");
                    }
                }

            }
        }
    }

}
