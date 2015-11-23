package com.hdhelper.injector.mod;

import com.hdhelper.agent.BasicAction;
import com.hdhelper.injector.InjectorConfig;
import com.hdhelper.injector.util.ASMUtil;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

public class ActionMod extends InjectionModule {

    public ActionMod(Map<String, ClassNode> classes, InjectorConfig cfg) {
        super(classes, cfg);
    }

    @Override
    public void inject() {

        for (ClassNode cn : classes.values()) {
            for (final MethodNode mn : (List<MethodNode>) cn.methods) {
                if (!Modifier.isStatic(mn.access)) continue;
                if (!mn.desc.endsWith("V")) continue;
                if (!mn.desc.startsWith("(IIIILjava/lang/String;Ljava/lang/String;II")) continue;

                InsnList stack = new InsnList();
                stack.add(new TypeInsnNode(NEW, Type.getInternalName(BasicAction.class)));
                stack.add(new InsnNode(DUP));
                stack.add(new VarInsnNode(ILOAD, 0));
                stack.add(new VarInsnNode(ILOAD, 1));
                stack.add(new VarInsnNode(ILOAD, 2));
                stack.add(new VarInsnNode(ILOAD, 3));
                stack.add(new VarInsnNode(ALOAD, 4));
                stack.add(new VarInsnNode(ALOAD, 5));
                stack.add(new VarInsnNode(ILOAD, 6));
                stack.add(new VarInsnNode(ILOAD, 7));
                stack.add(new MethodInsnNode(INVOKESPECIAL, Type.getInternalName(BasicAction.class), "<init>",
                        ASMUtil.getMethodDescriptor(Void.TYPE, int.class, int.class, int.class, int.class, String.class, String.class, int.class, int.class), false));
                stack.add(new MethodInsnNode(INVOKESTATIC, "client",
                        "actionPerformed", ASMUtil.getMethodDescriptor(Void.TYPE,BasicAction.class), false));
                mn.instructions.insert(mn.instructions.getFirst(), stack);

           //     System.out.println("    -> Menu Injected @ " + cn.name + "." + mn.name);

            }
        }
    }


}
