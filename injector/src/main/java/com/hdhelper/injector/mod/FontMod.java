package com.hdhelper.injector.mod;

import com.hdhelper.injector.InjectorConfig;
import com.hdhelper.injector.util.ASMUtil;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.List;
import java.util.Map;

public class FontMod extends InjectionModule {

    public FontMod(Map<String, ClassNode> classes, InjectorConfig cfg) {
        super(classes, cfg);
    }

    @Override
    public void inject() {

        // CreateFont.... (unpack images) //TODO hook (look for "p12_full"..)
        for(MethodNode mn : (List<MethodNode>)classes.get("dt").methods) {
            if(mn.name.equals("h") && mn.desc.endsWith("Lhj;")) {
                InsnList stack = new InsnList();
                stack.add(new VarInsnNode(ALOAD,2));
                stack.add(new FieldInsnNode(PUTSTATIC,"client","curFont", Type.getDescriptor(String.class)));
                mn.instructions.insertBefore(mn.instructions.getFirst(),stack);
            }
        }

        // createFont
        for(MethodNode mn : (List<MethodNode>)classes.get("hj").methods) {
            if(mn.name.equals("<init>") && mn.desc.endsWith("[[B)V")) {
                InsnList stack = new InsnList();
                stack.add(new VarInsnNode(ALOAD,1));
                stack.add(new VarInsnNode(ALOAD,2));
                stack.add(new VarInsnNode(ALOAD,3));
                stack.add(new VarInsnNode(ALOAD,4));
                stack.add(new VarInsnNode(ALOAD,5));
                stack.add(new VarInsnNode(ALOAD,6));
                stack.add(new VarInsnNode(ALOAD,7));
                stack.add(new MethodInsnNode(INVOKESTATIC,"client","captureGlyphVector",
                        ASMUtil.getMethodDescriptor(Void.TYPE,
                                byte[].class,
                                int[].class, int[].class,
                                int[].class, int[].class,
                                int[].class, byte[][].class), false
                        )
                );
                mn.instructions.insertBefore(mn.instructions.getFirst(),stack);

            }
        }



    }
}
