package com.hdhelper.injector;

import com.hdhelper.agent.ClientCanvas;
import com.hdhelper.agent.bridge.RenderSwitch;
import com.hdhelper.injector.util.ASMUtil;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;

import java.awt.*;

//Responsible for acquiring CNI interfaces for dependencies throughout the CNI implement.
public class CNIAccess {

    /**
     * @see com.hdhelper.injector.bs.scripts.Client#render_switch
     * @return The bytecode logic for acquiring the reference to the rendering-switch interface
     */
    public static InsnList getRenderSwitch() {
        InsnList stack = new InsnList();
        stack.add(new FieldInsnNode(Opcodes.GETSTATIC,"client","render_switch",Type.getDescriptor(RenderSwitch.class)));
        return stack;
    }

    public static InsnList makeCanvas() {
        InsnList stack = new InsnList();
        stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC,"client","createClientCanvas", ASMUtil.getMethodDescriptor(ClientCanvas.class, Component.class), false));
        return stack;
    }
}
