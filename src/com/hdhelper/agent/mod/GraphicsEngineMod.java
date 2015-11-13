package com.hdhelper.agent.mod;

import com.hdhelper.agent.Callback;
import com.hdhelper.agent.util.ASMUtil;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class GraphicsEngineMod extends InjectionModule {


    @Override
    public void inject(Map<String, ClassNode> classes) {


        for(ClassNode cn : classes.values()) {
            if(cn.superName.equals("ba")) {
                hack(cn);
            }
        }


    }

    void hack(ClassNode cn) {
        for(MethodNode mn : (List<MethodNode>) cn.methods) {
            for(AbstractInsnNode ain : mn.instructions.toArray()) {
                if(ain.getOpcode() == PUTFIELD) {
                    FieldInsnNode fin = (FieldInsnNode) ain;
                    if(fin.owner.equals(cn.name) && fin.name.equals("l")) {
                        InsnList stack = new InsnList();
                        stack.add(new VarInsnNode(ALOAD,0));
                        stack.add(new FieldInsnNode(GETFIELD,cn.name,"q","[I"));
                        stack.add(new VarInsnNode(ALOAD,0));
                        stack.add(new FieldInsnNode(GETFIELD,cn.name,"m","L" + Type.getInternalName(Image.class) + ";"));
                        stack.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(Callback.class),"reshape", ASMUtil.getMethodDescriptor(Void.TYPE, int[].class, Image.class),false));
                        mn.instructions.insert(fin, stack);

                        System.out.println("RENDER-HACK");
                    }
                }

            }
        }
    }

}
