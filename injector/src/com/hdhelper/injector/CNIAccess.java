package com.hdhelper.injector;

import com.hdhelper.agent.bridge.RenderSwitch;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;

//Responsible for acquiring CNI interfaces for dependencies throughout the CNI.
public class CNIAccess {

    public static InsnList getRenderSwitch() {
        InsnList stack = new InsnList();
        stack.add(new FieldInsnNode(Opcodes.GETSTATIC,"client","render_switch", Type.getInternalName(RenderSwitch.class)));
        return stack;
    }

}
