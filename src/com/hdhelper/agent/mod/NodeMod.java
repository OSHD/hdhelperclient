package com.hdhelper.agent.mod;

import com.hdhelper.agent.mod.mem.FieldMember;
import com.hdhelper.agent.util.ASMUtil;
import com.hdhelper.peer.RSNode;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.nashorn.internal.codegen.types.Type;

import java.util.Map;

/**
 * Created by Jamie on 10/21/2015.
 */
public class NodeMod extends InjectionModule {

    public static final String NODE = "go";
    public static final String NODE_DESC = "L" + NODE + ";";


    public static final FieldMember KEY;
    public static final FieldMember NEXT;
    public static final FieldMember PREV;

    static {


        KEY = new FieldMember(NODE,"eg","J");
        NEXT = new FieldMember(NODE,"ek",NODE_DESC);
        PREV = new FieldMember(NODE,"eo",NODE_DESC);

    }

    @Override
    public void inject(Map<String, ClassNode> classes) {

        ClassNode cn = classes.get(NODE);
        cn.interfaces.add(Type.getInternalName(RSNode.class));

        cn.methods.add(ASMUtil.mkGetter("getKey", KEY));
        cn.methods.add(ASMUtil.mkGetter("getNext", Type.getMethodDescriptor(RSNode.class),NEXT));
        cn.methods.add(ASMUtil.mkGetter("getPrev", Type.getMethodDescriptor(RSNode.class),PREV));

    }
}
