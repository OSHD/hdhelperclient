package com.hdhelper.injector;

import com.hdhelper.agent.CanvasFactory;
import com.hdhelper.agent.RenderSwitch;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;

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

    /**
     * @see com.hdhelper.injector.bs.scripts.Client#canvas_factory
     * @return The bytecode logic for acquiring the reference to the canvas-factory interface.
     */
    public static InsnList getCanvasFactory() {
        InsnList stack = new InsnList();
        stack.add(new FieldInsnNode(Opcodes.GETSTATIC,"client","canvas_factory",Type.getDescriptor(CanvasFactory.class)));
        return stack;
    }
}
