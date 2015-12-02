package com.hdhelper.injector.mod;

import com.hdhelper.agent.SharedAgentSecrets;
import com.hdhelper.agent.CanvasFactory;
import com.hdhelper.agent.ClientCanvas;
import com.hdhelper.agent.ClientCanvasAccess;
import com.hdhelper.injector.CNIAccess;
import com.hdhelper.injector.InjectorConfig;
import com.hdhelper.injector.util.ASMUtil;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.awt.*;
import java.util.*;

public class ClientCanvasMod extends InjectionModule {

    public ClientCanvasMod(Map<String, ClassNode> classes, InjectorConfig cfg) {
        super(classes, cfg);
    }

    @Override
    public void inject() {
       
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

                            TypeInsnNode make = (TypeInsnNode) ain.getPrevious().getPrevious().getPrevious(); //TODO we assert allot... REDO
                            if(make.getOpcode() != Opcodes.NEW) throw new Error("eek");


                            InsnList createCanvas = new InsnList();
                            createCanvas.add(CNIAccess.getCanvasFactory());
                            createCanvas.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE,Type.getInternalName(CanvasFactory.class),"createClientCanvas",ASMUtil.getMethodDescriptor(ClientCanvas.class),true));
                            mn.instructions.insertBefore(make, createCanvas);
                            mn.instructions.remove(make);

                            InsnList stack = new InsnList();
                            stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC,Type.getInternalName(SharedAgentSecrets.class),
                                    "getClientCanvasAccess", ASMUtil.getMethodDescriptor(ClientCanvasAccess.class),false));
                            stack.add(new InsnNode(DUP_X2));
                            stack.add(new InsnNode(POP));
                            stack.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, Type.getInternalName(ClientCanvasAccess.class),
                                    "setDelegate", ASMUtil.getMethodDescriptor(Void.TYPE, ClientCanvas.class, Component.class), true));

                            mn.instructions.insertBefore(min, stack);
                            mn.instructions.remove(min);

                        }

                    }
                }

            }
        }

    }

}
