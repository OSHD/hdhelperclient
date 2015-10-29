package com.hdhelper.agent.mod;

import com.hdhelper.agent.mod.mem.FieldMember;
import com.hdhelper.agent.util.ASMUtil;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * Created by Jamie on 10/21/2015.
 */
public class ObjectDefintionMod extends InjectionModule {

    public static final String OBJECT_DEFINTION = "af";
    public static final String OBJECT_DEFINTION_DESC = "L" + OBJECT_DEFINTION + ";";

    public static final FieldMember NAME;

    static {

        NAME = new FieldMember(OBJECT_DEFINTION,"z", Type.getDescriptor(String.class));

    }

    @Override
    public void inject(Map<String, ClassNode> classes) {
        ClassNode cn = classes.get(OBJECT_DEFINTION);
        cn.methods.add(ASMUtil.mkGetter("getName",NAME));
    }
}
