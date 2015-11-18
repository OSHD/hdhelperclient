package com.hdhelper.injector.mod;

import com.hdhelper.injector.InjectorConfig;
import com.hdhelper.injector.mod.InjectionModule;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.awt.*;
import java.util.Map;

public class ClientCanvasMod extends InjectionModule {

    public ClientCanvasMod(Map<String, ClassNode> classes, InjectorConfig cfg) {
        super(classes, cfg);
    }

    @Override
    public void inject() {

        String super_clazz = cfg.getClientCanvasImplementName().replace('.','/');
        // Replace the super class of Canvas with the defined implement (in which extends canvas)
        for (final ClassNode cn : classes.values()) {
            if (!cn.superName.equals(Type.getInternalName(Canvas.class))) continue;
            cn.superName = super_clazz;
            for (final MethodNode mn : (java.util.List<MethodNode>) cn.methods) {
                if (!mn.name.equals("<init>")) continue;
                for (final AbstractInsnNode ain : mn.instructions.toArray()) {
                    if (ain.getOpcode() != INVOKESPECIAL) continue;
                    final MethodInsnNode min = (MethodInsnNode) ain;
                    if (min.owner.equals(Type.getInternalName(Canvas.class)) && mn.name.equals("<init>")) {
                        min.owner = super_clazz;
                    }
                }
            }
        }
    }

}
