package com.hdhelper.agent.mod.mem;

import com.hdhelper.agent.util.ASMUtil;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;
import jdk.internal.org.objectweb.asm.tree.FieldNode;

public class FieldMember {

    private final String owner;
    private final String name;
    private final String desc;
    private final int decoder;
    private final boolean stat;

    private int encoder;

    public FieldMember(String owner, String name, String desc) {
        this(owner,name,desc,0);
    }

    public FieldMember(String owner, String name, String desc, boolean stat) {
        this(owner,name,desc,0,stat);
    }

    public FieldMember(String owner, String name, String desc, int multi) {
        this(owner,name,desc,multi,false);
    }

    public FieldMember(String owner, String name, String desc, int multi, boolean stat) {
        this.owner = owner;
        this.name = name;
        this.desc = desc;
        this.decoder = multi;
        this.stat = stat;
    }

    public boolean match(FieldNode fn) {
        return fn.name.equals(name)
            && fn.desc.equals(desc);
    }

    public boolean match(FieldInsnNode fin) {
        return fin.owner.equals(owner)
            && fin.name.equals(name)
            && fin.desc.equals(fin.desc);
    }

    public boolean match(String owner, String name, String desc) {
        return this.owner.equals(owner)
            && this.name.equals(name) 
            && this.desc.equals(desc);
    }

    public FieldInsnNode get() {
        return new FieldInsnNode(stat ? Opcodes.GETSTATIC : Opcodes.GETFIELD,owner,name,desc);
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

    public int getDecoder() {
        return decoder;
    }

    public int getEncoder() {
        if(encoder != 0) return encoder;
        encoder = ASMUtil.getInverse(decoder);
        return encoder;
    }

    public boolean hasMulti() {
        return decoder != 0;
    }

    public boolean isStatic() {
        return stat;
    }

}
