package com.hdhelper.agent.mod;

import com.hdhelper.agent.mod.mem.FieldMember;
import com.hdhelper.agent.util.ASMUtil;
import com.hdhelper.peer.RSNode;
import com.hdhelper.peer.RSNodeTable;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.nashorn.internal.codegen.types.Type;

import java.util.Map;

/**
 * Created by Jamie on 10/21/2015.
 */
public class NodeTableMod extends InjectionModule {

    public static final String NODE_TABLE = "gm";
    public static final String NODE_TABLE_DESC = "L" + NODE_TABLE+ ";";


    public static final FieldMember BUCKETS;
    public static final FieldMember CAPACITY;

    static {

        BUCKETS = new FieldMember(NODE_TABLE,"v","[" + NodeMod.NODE_DESC);
        CAPACITY = new FieldMember(NODE_TABLE,"i","I"); //no-multi

    }

    @Override
    public void inject(Map<String, ClassNode> classes) {
        ClassNode tbl = classes.get(NODE_TABLE);
        tbl.interfaces.add( Type.getInternalName(RSNodeTable.class) );

        tbl.methods.add(ASMUtil.mkGetter("getBuckets", Type.getMethodDescriptor(RSNode[].class), BUCKETS));
        tbl.methods.add(ASMUtil.mkGetter("getCapacity", CAPACITY));
    }
}
