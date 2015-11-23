package com.hdhelper.injector.util;


import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.objectweb.asm.tree.analysis.BasicValue;

import java.util.*;

//If you have an array reference in which you want to know when anything is loaded from that array,
//this can help
public class ArrayStoreSearcher extends BasicInterpreter {

    public static class Entry {
        public final String owner;
        public final String name;
        public final String desc;

        private final Type type;

        public Entry(String owner, String name, String desc) {

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

        @Override
        public boolean equals(Object other) {
            if(other == this) return true;
            if(!(other instanceof Entry)) return false;
            Entry o = (Entry) other;
            return this.owner.equals(o.owner) && this.name.equals(o.name) && this.desc.equals(o.desc);
        }
    }


    private Set<Entry> entries;
    private Map<Entry,Set<InsnNode>> stores = new HashMap<Entry,Set<InsnNode>>();

    public ArrayStoreSearcher(Entry... entries) {
        this.entries = new HashSet(Arrays.asList(entries));
    }

    public Map<Entry,Set<InsnNode>> getResult() {
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
            Entry entry = ((ArrayElement)value1).entry;
            Set<InsnNode> sets = stores.get(entry);
            if(sets == null) {
                sets = new HashSet<InsnNode>();
                stores.put(entry,sets);
            }
            sets.add((InsnNode)insn);
        }
        return null;
    }

    @Override
    public BasicValue unaryOperation(AbstractInsnNode insn, BasicValue value)
            throws AnalyzerException {
        if( insn.getOpcode() == GETFIELD ) {
            FieldInsnNode fin = (FieldInsnNode) insn;
            for(Entry e : entries) {
                if(fin.owner.equals(e.owner) && fin.name.equals(e.name) && fin.desc.equals(e.desc)) {
                    return new ArrayElement(e);
                }
            }
        }
        return super.unaryOperation(insn, value);
    }

    @Override
    public BasicValue newOperation(final AbstractInsnNode insn) throws AnalyzerException {
        final int nop = insn.getOpcode();
        if( nop == GETSTATIC ) {
            FieldInsnNode fin = (FieldInsnNode) insn;
            for(Entry e : entries) {
                if (fin.owner.equals(e.owner) && fin.name.equals(e.name) && fin.desc.equals(e.desc)) {
                    return new ArrayElement(e);
                }
            }

        }
        return super.newOperation(insn);
    }


    private final class ArrayElement extends BasicValue {
        public final Entry entry;
        ArrayElement(Entry entry) {
            super(entry.type);
            this.entry = entry;
        }
    }

}
