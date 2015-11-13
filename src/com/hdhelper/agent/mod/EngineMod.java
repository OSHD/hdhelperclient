package com.hdhelper.agent.mod;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.List;

public class EngineMod {


    public static void inject(ClassNode game_engine) {
        for(MethodNode mn : (List<MethodNode>) game_engine.methods) {
            if(mn.name.equals("run") && mn.desc.equals("()V")) {
                InsnList stack = new InsnList();
                stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "client","onBoot","()V",false));
                mn.instructions.insertBefore(mn.instructions.getFirst(),stack);
            }
        }
    }

}
