package com.hdhelper.agent.util;

import jdk.internal.org.objectweb.asm.ClassWriter;

public class ClassWriterFix extends ClassWriter {

    private final ClassLoader loader;

    public ClassWriterFix(int i, ClassLoader loader) {
        super(i);
        this.loader = loader;
    }

    @Override
    protected String getCommonSuperClass(String var1, String var2) {

        ClassLoader var5 = this.loader;

        Class var3;
        Class var4;
        try {
            var3 = Class.forName(var1.replace('/', '.'), false, var5);
            var4 = Class.forName(var2.replace('/', '.'), false, var5);
        } catch (Exception var7) {
            throw new RuntimeException(var7.toString());
        }

        if (var3.isAssignableFrom(var4)) {
            return var1;
        } else if (var4.isAssignableFrom(var3)) {
            return var2;
        } else if (!var3.isInterface() && !var4.isInterface()) {
            do {
                var3 = var3.getSuperclass();
            } while (!var3.isAssignableFrom(var4));

            return var3.getName().replace('.', '/');
        } else {
            return "java/lang/Object";
        }
    }

}