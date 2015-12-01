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

public class VarChangeMod extends InjectionModule {

    public VarChangeMod(Map<String, ClassNode> classes, InjectorConfig cfg) {
        super(classes, cfg);
    }

    @Override
    public void inject() {


        final ArrayStoreSearcher.Entry E = new ArrayStoreSearcher.Entry("fj","s","[I");

        ArrayStoreSearcher ass = new ArrayStoreSearcher(E);

        for(ClassNode cn : classes.values()) { //TODO hook in updater and not waist time analyzing (which is costly)
            for(MethodNode mn : (List<MethodNode>) cn.methods) {

                Analyzer a = new Analyzer(ass);
                try {
                    a.analyze(cn.name,mn);
                } catch (AnalyzerException e) {
                    e.printStackTrace();
                }

                Set<InsnNode> results = ass.getResult().get(E);

                if(results == null) continue;

                for(InsnNode insn : results) {
                    InsnList stack = new InsnList();
                    stack.add(new MethodInsnNode(INVOKESTATIC,"client","setVar", ASMUtil.getMethodDescriptor(Void.TYPE, int[].class, int.class, int.class), false));
                    mn.instructions.insertBefore(insn, stack);
                    mn.instructions.remove(insn);
                }

                ass.clear();

            }
        }
    }
}
