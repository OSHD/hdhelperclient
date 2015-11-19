package com.hdhelper.injector.mod;

import com.hdhelper.injector.CNIAccess;
import com.hdhelper.injector.InjectorConfig;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.awt.*;
import java.util.Map;

public class ClientCanvasMod extends InjectionModule {

    public ClientCanvasMod(Map<String, ClassNode> classes, InjectorConfig cfg) {
        super(classes, cfg);
    }

    @Override
    public void inject() {
        // Replace the super class of Canvas with the defined implement (in which extends canvas)

        String canvas_clazz = null; //TODO define in the updater
        for (final ClassNode cn : classes.values()) {
            if (!cn.superName.equals(Type.getInternalName(Canvas.class))) continue;
            canvas_clazz = cn.name;
            break;
        }

        if(canvas_clazz == null) throw new Error("eek");

        for(ClassNode cn : classes.values()) {
            for(MethodNode mn : (java.util.List<MethodNode>) cn.methods) {
                for(AbstractInsnNode ain : mn.instructions.toArray()) {
                    if(ain.getOpcode() == Opcodes.INVOKESPECIAL) {
                        MethodInsnNode min = (MethodInsnNode) ain;
                        if(min.name.equals("<init>") && min.owner.equals(canvas_clazz)) {

                            TypeInsnNode make = (TypeInsnNode) ain.getPrevious().getPrevious().getPrevious();
                            if(make.getOpcode() != Opcodes.NEW) throw new Error("eek");
/*
                            InsnList mkCanvas = CNIAccess.makeCanvas();
                            mn.instructions.insertBefore(make, mkCanvas);
                            mn.instructions.remove(make);*/

                            InsnList stack = new InsnList();
                            stack.add(new InsnNode(DUP2));
                            stack.add(new InsnNode(SWAP));
                            stack.add(new InsnNode(POP));
                            stack.add(CNIAccess.makeCanvas());

                            mn.instructions.insertBefore(min,stack);
                            mn.instructions.remove(min);
/*
                            InsnList setDelegate = new InsnList();
                            setDelegate.add(new InsnNode(POP));
                            setDelegate.add(new MethodInsnNode(Opcodes.INVOKESTATIC,Type.getInternalName(AgentSecrets.class),
                                    "getClientCanvasAccess", ASMUtil.getMethodDescriptor(ClientCanvasAccess.class),false));
                            setDelegate.add(new InsnNode(SWAP));
                            setDelegate.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, Type.getInternalName(ClientCanvasAccess.class), "setDelegate",
                                   ASMUtil.getMethodDescriptor(Void.TYPE, ClientCanvas.class, Component.class), true));

                            mn.instructions.insert(min, setDelegate);
                            mn.instructions.remove(min);*/

                        }

                    }
                }

            }
        }
    }

}
