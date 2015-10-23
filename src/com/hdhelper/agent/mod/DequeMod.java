package com.hdhelper.agent.mod;

import com.hdhelper.agent.mod.mem.FieldMember;
import com.hdhelper.agent.util.ASMUtil;
import com.hdhelper.peer.RSDeque;
import com.hdhelper.peer.RSNode;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * Created by Jamie on 10/21/2015.
 */
public class DequeMod extends InjectionModule {

    public static final String DEQUE = "gb";
    public static final String DEQUE_DESC = "L" + DEQUE + ";";

    public static final FieldMember HEAD;
    public static final FieldMember TAIL;

    static {

        HEAD = new FieldMember(DEQUE,"i",NodeMod.NODE_DESC);
        TAIL = new FieldMember(DEQUE,"v",NodeMod.NODE_DESC);

    }

    @Override
    public void inject(Map<String, ClassNode> classes) {

        ClassNode cn = classes.get(DEQUE);
        cn.interfaces.add(jdk.nashorn.internal.codegen.types.Type.getInternalName(RSDeque.class));

        cn.methods.add(ASMUtil.mkGetter("getHead", jdk.nashorn.internal.codegen.types.Type.getMethodDescriptor(RSNode.class), HEAD));
        cn.methods.add(ASMUtil.mkGetter("getTail", jdk.nashorn.internal.codegen.types.Type.getMethodDescriptor(RSNode.class),TAIL));

    }
}
