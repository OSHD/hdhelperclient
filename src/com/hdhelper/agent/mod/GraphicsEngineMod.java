package com.hdhelper.agent.mod;

import com.hdhelper.agent.Callback;
import jdk.internal.org.objectweb.asm.tree.*;
import jdk.nashorn.internal.codegen.types.Type;

import java.awt.*;
import java.util.Map;

public class GraphicsEngineMod extends InjectionModule {


    @Override
    public void inject(Map<String, ClassNode> classes) {


        for(ClassNode cn : classes.values()) {
            if(cn.superName.equals("bv")) {
                hack(cn);
            }
        }


    }

    void hack(ClassNode cn) {
        for(MethodNode mn : cn.methods) {
            for(AbstractInsnNode ain : mn.instructions.toArray()) {
                if(ain.getOpcode() == PUTFIELD) {
                    FieldInsnNode fin = (FieldInsnNode) ain;
                    if(fin.owner.equals(cn.name) && fin.name.equals("s")) {
                        InsnList stack = new InsnList();
                        stack.add(new VarInsnNode(ALOAD,0));
                        stack.add(new FieldInsnNode(GETFIELD,cn.name,"f","[I"));
                        stack.add(new VarInsnNode(ALOAD,0));
                        stack.add(new FieldInsnNode(GETFIELD,cn.name,"s","L" + Type.getInternalName(Image.class) + ";"));
                        stack.add(new MethodInsnNode(INVOKESTATIC,Type.getInternalName(Callback.class),"reshape",Type.getMethodDescriptor(Void.TYPE, int[].class, Image.class),false));
                        mn.instructions.insert(fin, stack);

                        System.out.println("RENDER-HACK");
                    }
                }

            }
        }
    }

}
