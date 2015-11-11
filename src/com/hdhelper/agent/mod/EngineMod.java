package com.hdhelper.agent.mod;

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

public class EngineMod {


    public static void inject(ClassNode game_engine) {
        for(MethodNode mn : game_engine.methods) {
            if(mn.name.equals("run") && mn.desc.equals("()V")) {
                InsnList stack = new InsnList();
                stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "client","onBoot","()V",false));
                mn.instructions.insertBefore(mn.instructions.getFirst(),stack);
            }
        }
    }

}
