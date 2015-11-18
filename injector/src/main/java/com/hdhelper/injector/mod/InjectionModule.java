package com.hdhelper.injector.mod;

import com.hdhelper.injector.InjectorConfig;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.util.Map;

public abstract class InjectionModule implements Opcodes {

    protected final Map<String,ClassNode> classes;
    protected final InjectorConfig cfg;

    public InjectionModule(Map<String,ClassNode> classes, InjectorConfig cfg) {
        this.classes = classes;
        this.cfg     = cfg;
    }

    public abstract void inject();

}
