package com.hdhelper.agent.mod;

import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.InsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

import java.util.Map;

//Disables main-screen rendering
public class RenderMod extends InjectionModule {

    public static final String LANDSCAPE = "co";


    @Override
    public void inject(Map<String, ClassNode> classes) {
        for(MethodNode mn : classes.get(LANDSCAPE).methods) {
            if(mn.name.equals("an")) {
                InsnList stack = new InsnList();
                stack.add(new InsnNode(RETURN));
                mn.instructions.insertBefore(mn.instructions.getFirst(),stack);
            }
        }
    }

}
