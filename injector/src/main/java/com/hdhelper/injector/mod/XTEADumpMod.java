package com.hdhelper.injector.mod;

import com.hdhelper.injector.InjectorConfig;
import org.objectweb.asm.tree.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Jamie on 11/16/2015.
 */
public class XTEADumpMod extends InjectionModule {

    public XTEADumpMod(Map<String, ClassNode> classes, InjectorConfig cfg) {
        super(classes, cfg);
    }

    @Override
    public void inject() {
        for (ClassNode cn : classes.values()) {
            for (MethodNode mn : (List<MethodNode>) cn.methods) {

                for (AbstractInsnNode ain : mn.instructions.toArray()) {
                    if (ain.getOpcode() == INVOKEVIRTUAL) {
                        MethodInsnNode min = (MethodInsnNode) ain;
                        if (min.owner.equals("fb") && min.name.equals("r") && min.desc.equals("(Ljava/lang/String;B)I")) {

                            boolean hit = false;

                            AbstractInsnNode cur = ain;
                            while ((cur = cur.getPrevious()) != null) {
                                if (cur.getOpcode() == LDC) {
                                    LdcInsnNode ldc = (LdcInsnNode) cur;
                                    if (ldc.cst.toString().equals("l")) {
                                        hit = true;
                                        break;
                                    }
                                }
                            }

                            if (!hit) continue;

                      //      System.out.println("XTEA @ " + cn.name + "#" + mn.name + "@" + mn.desc);

                            //Copy the array-store index
                            InsnList stack = new InsnList();
                            stack.add(new InsnNode(DUP2));
                            stack.add(new InsnNode(POP));
                            stack.add(new MethodInsnNode(INVOKESTATIC, "com/hdhelper/client/SuperDirtyHacks", "dump", "(I)V", false));

                            mn.instructions.insert(ain, stack);

                        }
                    }
                }

            }
        }
    }

}
