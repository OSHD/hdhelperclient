package com.hdhelper.agent.mod;

import com.hdhelper.Environment;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.tree.*;

import java.util.Map;

//Disables main-screen rendering
public class RenderMod extends InjectionModule {

    public static final String LANDSCAPE = "ci";


    @Override
    public void inject(Map<String, ClassNode> classes) {

        for(MethodNode mn : classes.get(LANDSCAPE).methods) {
            if(mn.name.equals("ad")) {

                LabelNode A = new LabelNode(new Label());
                mn.visitLabel(A.getLabel());

                InsnList stack = new InsnList();
                stack.add(new FieldInsnNode(GETSTATIC, Type.getInternalName(Environment.class), "RENDER_LANDSCAPE","Z" ));
                stack.add(new JumpInsnNode(IFNE,A));
                stack.add(new InsnNode(RETURN));
                stack.add(A);

                mn.instructions.insertBefore(mn.instructions.getFirst(),stack);

            }
        }

        //AIO method for drawing hitbar, hitsplats, overheadText
        for(MethodNode mn : classes.get("client").methods) {
            if(mn.name.equals("aa")) {

                LabelNode A = new LabelNode(new Label());
                mn.visitLabel(A.getLabel());

                InsnList stack = new InsnList();
                stack.add(new FieldInsnNode(GETSTATIC, Type.getInternalName(Environment.class), "RENDER_LANDSCAPE","Z" ));
                stack.add(new JumpInsnNode(IFNE,A));
                stack.add(new InsnNode(RETURN));
                stack.add(A);

                mn.instructions.insertBefore(mn.instructions.getFirst(),stack);

            }
        }


    }

}
