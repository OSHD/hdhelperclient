package com.hdhelper.agent.mod.mem;

import com.hdhelper.agent.util.ASMUtil;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Map;

public class MethodMember {

    private final String owner;
    private final String name;
    private final String desc;
    private final int dummy;
    private final boolean stat;

    public MethodMember(String owner, String name, String desc, int dummy) {
        this(owner,name,desc,dummy,false);
    }

    public MethodMember(String owner, String name, String desc, int dummy, boolean stat) {
        this.owner = owner;
        this.name = name;
        this.desc = desc;
        this.dummy = dummy;
        this.stat = stat;
    }


    public boolean match(MethodNode mn) {
        return mn.name.equals(name)
            && mn.desc.equals(desc);
    }

    public boolean match(MethodInsnNode min) {
        return min.owner.equals(owner)
            && min.name.equals(name)
            && min.desc.equals(desc);
    }

    public boolean matchNoOwner(MethodInsnNode min) {
        return min.name.equals(name)
            && min.desc.equals(desc);
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isStatic() {
        return stat;
    }

    public int getDummy() {
        return dummy;
    }

    public MethodNode get(Map<String,ClassNode> classes) {
        return ASMUtil.get(classes, this);
    }

}
