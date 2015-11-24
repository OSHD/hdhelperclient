package com.hdhelper.injector.mod;

import com.hdhelper.injector.InjectorConfig;
import com.hdhelper.injector.util.ASMUtil;
import com.hdhelper.injector.util.ArrayStoreSearcher;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.AnalyzerException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SkillMod extends InjectionModule {

    public SkillMod(Map<String, ClassNode> classes, InjectorConfig cfg) {
        super(classes, cfg);
    }

    @Override
    public void inject() {

        final ArrayStoreSearcher.Entry REAL = new ArrayStoreSearcher.Entry("client","hj","[I");
        final ArrayStoreSearcher.Entry TEMP = new ArrayStoreSearcher.Entry("client","hk","[I");
        final ArrayStoreSearcher.Entry EXP  = new ArrayStoreSearcher.Entry("client","ho","[I");

        ArrayStoreSearcher ass = new ArrayStoreSearcher(REAL,TEMP,EXP);

        for(ClassNode cn : classes.values()) { //TODO hook in updater and not waist time analyzing
            for(MethodNode mn : (List<MethodNode>) cn.methods) {

                Analyzer a = new Analyzer(ass);
                try {
                    a.analyze(cn.name,mn);
                } catch (AnalyzerException e) {
                    e.printStackTrace();
                }

                for(Map.Entry<ArrayStoreSearcher.Entry,Set<InsnNode>> entry : ass.getResult().entrySet()) {

                    ArrayStoreSearcher.Entry CASE = entry.getKey();
                    Set<InsnNode> results = entry.getValue();

                    if(CASE == REAL) {
                        for(InsnNode insn : results) {
                            InsnList stack = new InsnList();
                            stack.add(new MethodInsnNode(INVOKESTATIC,"client","setRealSkillLvl", ASMUtil.getMethodDescriptor(Void.TYPE, int[].class, int.class, int.class), false));
                            mn.instructions.insertBefore(insn, stack);
                            mn.instructions.remove(insn);
                        }
                    } else if(CASE == TEMP) {
                        for(InsnNode insn : results) {
                            InsnList stack = new InsnList();
                            stack.add(new MethodInsnNode(INVOKESTATIC,"client","setTempSkillLvl", ASMUtil.getMethodDescriptor(Void.TYPE, int[].class, int.class, int.class), false));
                            mn.instructions.insertBefore(insn, stack);
                            mn.instructions.remove(insn);
                        }
                    } else {
                        for(InsnNode insn : results) {
                            InsnList stack = new InsnList();
                            stack.add(new MethodInsnNode(INVOKESTATIC,"client","setExp", ASMUtil.getMethodDescriptor(Void.TYPE, int[].class, int.class, int.class), false));
                            mn.instructions.insertBefore(insn, stack);
                            mn.instructions.remove(insn);
                        }
                    }


                }


                ass.clear();

            }
        }

    }
}
