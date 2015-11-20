package com.hdhelper.injector.mod;


import com.hdhelper.agent.RenderSwitch;
import com.hdhelper.injector.CNIAccess;
import com.hdhelper.injector.InjectorConfig;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.List;
import java.util.Map;

//Disables main-screen rendering
public class RenderMod extends InjectionModule {


    public RenderMod(Map<String, ClassNode> classes, InjectorConfig cfg) {
        super(classes, cfg);
    }

    @Override
    public void inject() {

        for(MethodNode mn : (List<MethodNode>) classes.get("ch").methods) {
            if(mn.name.equals("al")) {

                /**
                 * if(!{@link CNIAccess#getRenderSwitch()}#doRenderLandscape()) {
                 *      return;
                 * }
                 * ... Normal execution
                 * @see RenderSwitch#doRenderOverlays()
                 */
                LabelNode A = new LabelNode(new Label());
                mn.visitLabel(A.getLabel());

                InsnList stack = new InsnList();
                stack.add(CNIAccess.getRenderSwitch());
                stack.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, Type.getInternalName(RenderSwitch.class),"doRenderLandscape","()Z",false));
                stack.add(new JumpInsnNode(IFNE, A));
                stack.add(new InsnNode(RETURN));
                stack.add(A);

                mn.instructions.insertBefore(mn.instructions.getFirst(),stack);

            }
        }

        //AIO method for drawing hitbar, hitsplats, overheadText
        for(MethodNode mn : (List<MethodNode>) classes.get("x").methods) {
            if(mn.name.equals("b")) {

                /**
                 * if(!{@link CNIAccess#getRenderSwitch()}#doRenderOverlays()) {
                 *      return;
                 * }
                 * ... Normal execution
                 * @see RenderSwitch#doRenderOverlays()
                 */
                LabelNode A = new LabelNode(new Label());
                mn.visitLabel(A.getLabel());

                InsnList stack = new InsnList();
                stack.add(CNIAccess.getRenderSwitch());
                stack.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, Type.getInternalName(RenderSwitch.class),"doRenderOverlays","()Z",false));
                stack.add(new JumpInsnNode(IFNE, A));
                stack.add(new InsnNode(RETURN));
                stack.add(A);

                mn.instructions.insertBefore(mn.instructions.getFirst(),stack);


            }
        }

    }
}
