package com.hdhelper.injector.util;


import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.objectweb.asm.tree.analysis.BasicValue;

import java.util.HashSet;
import java.util.Set;

//If you have an array reference in which you want to know when anything is loaded from that array,
//this can help
public class ArrayStoreSearcher extends BasicInterpreter {

    protected final String owner;
    protected final String name;
    protected final String desc;
    protected final Type type;

    private Set<InsnNode> stores = new HashSet<InsnNode>();

    public ArrayStoreSearcher(String owner, String name, String desc) {
        if(owner == null)
            throw new IllegalArgumentException("owner == null");
        if(name == null)
            throw new IllegalArgumentException("name == null");
        type = Type.getType(desc);
        if(type.getSort() != Type.ARRAY)
            throw new IllegalArgumentException(desc + " is not of an array type");
        this.owner = owner;
        this.name  = name;
        this.desc  = desc;
    }

    public Set<InsnNode> getResult() {
        return stores;
    }

    public void clear() {
        stores.clear();
    }

    @Override
    public BasicValue ternaryOperation(final AbstractInsnNode insn,
                                       final BasicValue value1, final BasicValue value2,
                                       final BasicValue value3) throws AnalyzerException {
        if(insn.getOpcode() == IASTORE && value1 instanceof ArrayElement) {
            stores.add((InsnNode) insn);
        }
        return null;
    }

    @Override
    public BasicValue unaryOperation(AbstractInsnNode insn, BasicValue value)
            throws AnalyzerException {
        if( insn.getOpcode() == GETFIELD ) {
            FieldInsnNode fin = (FieldInsnNode) insn;
            if(fin.owner.equals(owner) && fin.name.equals(name) && fin.desc.equals(desc)) {
                return new ArrayElement();
            }
        }
        return super.unaryOperation(insn, value);
    }

    @Override
    public BasicValue newOperation(final AbstractInsnNode insn) throws AnalyzerException {
        final int nop = insn.getOpcode();
        if( nop == GETSTATIC ) {
            FieldInsnNode fin = (FieldInsnNode) insn;
            if(fin.owner.equals(owner) && fin.name.equals(name) && fin.desc.equals(desc)) {
                return new ArrayElement();
            }
        }
        return super.newOperation(insn);
    }


    private final class ArrayElement extends BasicValue {
        ArrayElement() {
            super(type);
        }
    }

}
