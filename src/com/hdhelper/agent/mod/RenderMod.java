package com.hdhelper.agent.mod;

import com.hdhelper.Environment;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.List;
import java.util.Map;

//Disables main-screen rendering
public class RenderMod extends InjectionModule {

    public static final String LANDSCAPE = "cp";


    @Override
    public void inject(Map<String, ClassNode> classes) {


        for(MethodNode mn : (List<MethodNode>) classes.get(LANDSCAPE).methods) {
            if(mn.name.equals("ag")) {

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
        for(MethodNode mn : (List<MethodNode>) classes.get("es").methods) {
            if(mn.name.equals("ax")) {

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
