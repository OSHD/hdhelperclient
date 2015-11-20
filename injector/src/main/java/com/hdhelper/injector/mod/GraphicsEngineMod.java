package com.hdhelper.injector.mod;

import com.hdhelper.agent.SharedAgentSecrets;
import com.hdhelper.agent.ClientCanvas;
import com.hdhelper.agent.ClientCanvasAccess;
import com.hdhelper.injector.InjectorConfig;
import com.hdhelper.injector.util.ASMUtil;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class GraphicsEngineMod extends InjectionModule {

    public GraphicsEngineMod(Map<String, ClassNode> classes, InjectorConfig cfg) {
        super(classes, cfg);
    }

    void hack(ClassNode cn) {
        for(MethodNode mn : (List<MethodNode>) cn.methods) {
            for(AbstractInsnNode ain : mn.instructions.toArray()) {
                if(ain.getOpcode() == PUTFIELD) {
                    FieldInsnNode fin = (FieldInsnNode) ain;
                    if(fin.owner.equals(cn.name) && fin.name.equals("a")) {

                        InsnList stack = new InsnList();

                        /**
                         * AgentSecrets.getClientCanvasAccess().setBitMap(Client.getCanvas(), this.raster, this.image);
                         */

                        stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC,Type.getInternalName(SharedAgentSecrets.class),
                                "getClientCanvasAccess",ASMUtil.getMethodDescriptor(ClientCanvasAccess.class),false));
                        stack.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "client", "getCanvas", ASMUtil.getMethodDescriptor(ClientCanvas.class), false));

                        stack.add(new VarInsnNode(ALOAD,0));
                        stack.add(new FieldInsnNode(GETFIELD,cn.name,"f","[I"));

                        stack.add(new VarInsnNode(ALOAD, 0));
                        stack.add(new FieldInsnNode(GETFIELD, cn.name, "a", "L" + Type.getInternalName(Image.class) + ";"));

                        stack.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE,Type.getInternalName(ClientCanvasAccess.class),"setBitmap",
                                ASMUtil.getMethodDescriptor(Void.TYPE,ClientCanvas.class,int[].class,Image.class), true));

                        mn.instructions.insert(fin, stack);

                       // System.out.println("RENDER-HACK");

                    }
                }
            }
        }
    }



    @Override
    public void inject() {
        for(ClassNode cn : classes.values()) {
            if(cn.superName.equals("bv")) {
                hack(cn);
            }
        }
    }
}
