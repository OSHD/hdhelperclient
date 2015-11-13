package com.hdhelper.agent.util;

import com.hdhelper.agent.mod.mem.FieldMember;
import com.hdhelper.agent.mod.mem.MethodMember;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class ASMUtil {


    public static MethodNode get(Map<String,ClassNode> classes, MethodMember mem) {
        ClassNode cn = classes.get(mem.getOwner());
        if(cn == null) return null;
        for(MethodNode mn : (List<MethodNode>) cn.methods) {
            if(mem.match(mn)) return mn;
        }
        return null;
    }

    public static MethodNode mkGetter(String owner, String name, FieldNode fn) {
        boolean is_static = Modifier.isStatic(fn.access);
        final MethodNode mn = new MethodNode( Opcodes.ACC_PUBLIC, name, "()" + fn.desc, null, null);
        if (!is_static) mn.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
        mn.instructions.add(new FieldInsnNode(is_static ? Opcodes.GETSTATIC : Opcodes.GETFIELD, owner, fn.name, fn.desc));
        mn.instructions.add(new InsnNode(getReturnOpcode(fn.desc)));
        mn.visitMaxs(0, 0);
        mn.visitEnd();
        return mn;
    }

    public static MethodNode mkGetter(String name, FieldMember field) {
        return mkGetter(name,"()" + field.getDesc(),field.isStatic(), field.getOwner(), field.getName(), field.getDesc(), field.getDecoder());
    }

    public static MethodNode mkGetter(String name, String method_desc, FieldMember field) {
        return mkGetter(name,method_desc,field.isStatic(), field.getOwner(), field.getName(), field.getDesc(), field.getDecoder());
    }

    public static MethodNode mkGetter ( String method_name, String method_desc,
                                        boolean static_field, String field_owner,
                                        String field_name, String field_desc, int multiplier ) {
        MethodNode mn = new MethodNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, method_name, method_desc, null, null);
        InsnList stack = mn.instructions;
        if(!static_field) stack.add(new VarInsnNode(Opcodes.ALOAD, 0));
        stack.add(new FieldInsnNode(static_field ? Opcodes.GETSTATIC : Opcodes.GETFIELD, field_owner, field_name, field_desc));
        if (multiplier != 0) {
            stack.add(new LdcInsnNode(multiplier));
            stack.add( new InsnNode(Opcodes.IMUL));
        }
        stack.add(new InsnNode(getReturnOpcode(method_desc)));
        mn.visitMaxs(0, 0);
        mn.visitEnd();
        return mn;
    }


    public static int getReturnOpcode(String desc) {
        desc = desc.substring(desc.indexOf(")") + 1);
        if (desc.length() > 1) {
            return Opcodes.ARETURN;
        }
        final char c = desc.charAt(0);
        switch (c) {
            case 'I':
            case 'Z':
            case 'B':
            case 'S':
            case 'C': {
                return Opcodes.IRETURN;
            }
            case 'J': {
                return Opcodes.LRETURN;
            }
            case 'F': {
                return Opcodes.FRETURN;
            }
            case 'D': {
                return Opcodes.DRETURN;
            }
            case 'V': {
                return Opcodes.RETURN;
            }
        }
        throw new RuntimeException("bad_return@" + desc);
    }

    public static String getDescriptor(Class<?> clazz) {
        return "L" + Type.getInternalName(clazz) + ";";
    }

    public static int getOpenVar(MethodNode mn) {
        int next = Type.getArgumentTypes(mn.desc).length;
        if(!Modifier.isStatic(mn.access)) next++;
        for(AbstractInsnNode ain0 : mn.instructions.toArray()) {
            if(ain0 instanceof VarInsnNode ) {
                VarInsnNode vin = (VarInsnNode) ain0;
                if(vin.var > next) next = vin.var;
            }
        }
        return next+1;
    }

    public static int getInverse ( int coder ) {
        try {
            final BigInteger num = BigInteger.valueOf(coder);
            return num.modInverse(new BigInteger(String.valueOf(1L << 32))).intValue();
        } catch (final Exception e) {
            return 0;
        }
    }

    public static FieldNode find(ClassNode cn, String name) {
        for (final FieldNode fn : ((List<FieldNode>) cn.fields)) {
            if (fn.name.equals(name)) {
                return fn;
            }
        }

        return null;
    }

    public static boolean matchesFieldInsn(AbstractInsnNode ain, int opcode, String owner, String name) {
        if (ain.getOpcode() != opcode) {
            return false;
        }

        FieldInsnNode fin = (FieldInsnNode) ain;
        return fin.owner.equals(owner) && fin.name.equals(name);
    }

    public static String getMethodDescriptor(Class ret, Class... args) {
        Type ret0 = Type.getType(ret);
        Type[] args0 = new Type[args.length];
        for(int i = 0; i < args.length; i++) {
            args0[i] = Type.getType(args[i]);
        }
        return Type.getMethodDescriptor(ret0,args0);
    }
}
