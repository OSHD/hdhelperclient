package com.bytescript.compiler;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.RemappingClassAdapter;

public class BSAdapter extends RemappingClassAdapter {

    BSRemaper bsr;

    public BSAdapter(ClassVisitor classVisitor, BSRemaper remapper) {
        super(classVisitor, remapper);
        this.bsr = remapper;
    }

    @Override
    public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
        if(bsr.profiler.getFieldDef(className,var2,var3) != null) return null;
        return super.visitField(var1, var2, var3, var4, var5);
    }

    @Override
    public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {

        if(var2.equals("<init>")) {
            return null;
            //if(bsr.profiler.getConstructorDef(className,var2,var3) != null) return null;
        } else {
            if(bsr.profiler.getMethodDef(className,var2,var3) != null) return null;
        }


        return super.visitMethod(var1, var2, var3, var4, var5);
    }

}
