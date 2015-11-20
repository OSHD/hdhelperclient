package com.hdhelper.injector.mod;

import com.hdhelper.injector.InjectorConfig;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.List;
import java.util.Map;

public class MessageMod extends InjectionModule {

    public MessageMod(Map<String, ClassNode> classes, InjectorConfig cfg) {
        super(classes, cfg);
    }

    //TODO hook:
    @Override
    public void inject() {

        // Event support
        for(MethodNode mn : (List<MethodNode>)classes.get("cx").methods) {
            if(mn.name.equals("m")) {
                for(AbstractInsnNode ain : mn.instructions.toArray()) {
                    if(ain.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                        MethodInsnNode min = (MethodInsnNode) ain;
                        if(min.owner.equals("ao") && min.name.equals("j")) {



                            InsnList stack = new InsnList();
                            stack.add(new InsnNode(DUP));
                            stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"client","onMessage","(Lav;)V",false));
                            mn.instructions.insert(min, stack);
                      //      System.out.println("HACK");
                        }
                    }
                }
            }
        }

        // Visibility culling
        for(MethodNode mn : (List<MethodNode>) classes.get("ao").methods) {
            if(mn.name.equals("m")) {
                for(AbstractInsnNode ain : mn.instructions.toArray()) {
                    if(ain.getOpcode() == ARETURN && ain.getPrevious().getOpcode() != ACONST_NULL) {

                        LabelNode A = new LabelNode(new Label());
                        mn.visitLabel(A.getLabel());

                        InsnList stack = new InsnList();
                        stack.add(new InsnNode(DUP));
                        stack.add(new FieldInsnNode(GETFIELD,"av","invisible","Z"));
                        stack.add(new JumpInsnNode(IFEQ,A));
                        stack.add(new InsnNode(ACONST_NULL));
                        stack.add(new InsnNode(ARETURN));
                        stack.add(A);

                       // System.out.println("VISIBLITY HACK");

                        mn.instructions.insertBefore(ain,stack);

                    }
                }
            }

        }

    }
}
