package com.bytescript.compiler;

import com.bytescript.lang.BField;
import com.bytescript.lang.BMethod;
import com.bytescript.lang.ByteScript;
import com.bytescript.util.RSUtil;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.StaticInitMerger;
import org.objectweb.asm.tree.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class BCompiler {

    BSProfiler profiler;
    BSResolver resolver;
    BSRemaper remaper;

    public BCompiler(BSProfiler profiler, BSResolver resolver) {
        this.profiler = profiler;
        this.resolver = resolver;
        this.remaper = new BSRemaper(profiler, resolver);
    }

    private void verify(Class bs, ClassNode target) {
        if (!remaper.map(Type.getInternalName(bs)).equals(target.name))
            throw new IllegalArgumentException("eek");
        if (!remaper.map(Type.getInternalName(bs.getSuperclass())).equals(target.superName))
            throw new IllegalStateException("eek");
    }

    public ClassNode inject(Class bs, ClassNode target) throws IOException {
        verify(bs, target);
        ClassNode script = load(bs);

        //TODO convert to proper visitor chain

        fixStaticOwners(script);
        appyCodec(script);

        BSAdapter adapter = new BSAdapter(target, remaper);
        script.accept(adapter);

        ClassNode dest = new ClassNode();
        target.accept(new StaticInitMerger(target.name,dest)); //TODO kinda hacky

        return dest;


    }

    public void inject(Class bs, Map<String,ClassNode> classes) throws IOException {
        ByteScript def = (ByteScript) bs.getAnnotation(ByteScript.class);
        if(def == null) throw new Error("Fuck off");
        String name0 = def.name();
        if(name0.equals("this")) name0 = Type.getInternalName(bs);
        String name = resolver.resolveName(name0);
        ClassNode cn = classes.get(name);
        if(cn == null) throw new Error(name0 + " is missing ");
        ClassNode injected = inject(bs,cn);
        classes.put(name,injected);
    }

    private String getFieldName(FieldInsnNode fin) {
        BField field = profiler.getFieldDef(fin.owner, fin.name, fin.desc);
        if (field == null) {
            return fin.name;
        } else {
            String field_name = field.name();
            if (field_name.equals("this")) {
                return fin.name;
            }
            return field_name;
        }
    }

    private String getMethodName(MethodInsnNode min) {
        BMethod method = profiler.getMethodDef(min.owner, min.name, min.desc);
        if (method == null) {
            return min.name;
        } else {
            String method_name = method.name();
            if (method_name.equals("this")) {
                return min.name;
            }
            return method_name;
        }
    }

    private void genPredicates(ClassNode cn) {
        for (MethodNode mn : (List<MethodNode>) cn.methods) {
            for (AbstractInsnNode ain : mn.instructions.toArray()) {
                if (ain instanceof MethodInsnNode) {

                    MethodInsnNode min = (MethodInsnNode) ain;

                    if (profiler.getMethodDef(min.owner, min.name, min.desc) == null) { // Not invoking a rs-method
                        continue;
                    }

                    boolean is_static = min.getOpcode() == Opcodes.INVOKESTATIC;

                    assert is_static == profiler.isStaticMethod(min.owner, min.name, min.desc);

                    String owner = remaper.map0(min.owner);
                    String name  = getMethodName(min);
                    String desc  = remaper.mapMethodDesc0(min.desc);

                    if (resolver.hasPredicate(owner, name, desc, is_static)) {

                        Type ptype = resolver.getPredicateType(owner,name,desc,true);
                        min.desc = RSUtil.addPredicate(min.desc,ptype);


                        Number pred = resolver.getPredicate(owner, name, desc, is_static);
                        if (pred == null) throw new Error();

                        AbstractInsnNode ldc;
                        switch (ptype.getSort()) {
                            case Type.BYTE:
                                ldc = new IntInsnNode(Opcodes.BIPUSH, pred.byteValue());
                                break;
                            case Type.SHORT:
                                ldc = new IntInsnNode(Opcodes.SIPUSH, pred.shortValue());
                                break;
                            case Type.INT:
                                ldc = new LdcInsnNode(pred.intValue());
                                break;
                            default:
                                throw new Error("Invalid pred type: " + ptype.getSort());
                        }

                        mn.instructions.insertBefore(min,ldc);

                    }

                }
            }
        }
    }

    private void fixStaticOwners(ClassNode cn) {

        for (MethodNode mn : (List<MethodNode>) cn.methods) {

            for (AbstractInsnNode ain : mn.instructions.toArray()) {

                if(ain.getOpcode() == Opcodes.INVOKESTATIC) {

                    MethodInsnNode min = (MethodInsnNode) ain;

                    BMethod def = profiler.getMethodDef(min.owner,min.name,min.desc);

                    if(def == null) continue;

                    String owner = remaper.map0(min.owner);
                    String name  = getMethodName(min);
                    String desc  = remaper.mapMethodDesc0(min.desc);


                    min.owner = resolver.resolveStaticMethodOwner(owner, name, desc);
                    min.name  = resolver.resolveMethodName(owner, name, desc, false);
                    min.desc  = remaper.mapMethodDesc(min.desc);

                    if (resolver.hasPredicate(owner, name, desc, true)) {

                        Type ptype = resolver.getPredicateType(owner,name,desc,true);
                        min.desc = RSUtil.addPredicate(min.desc, ptype);

                        Number pred = resolver.getPredicate(owner, name, desc, true);
                        if (pred == null) throw new Error();

                        AbstractInsnNode ldc;
                        switch (ptype.getSort()) {
                            case Type.BYTE:
                                ldc = new IntInsnNode(Opcodes.BIPUSH, pred.byteValue());
                                break;
                            case Type.SHORT:
                                ldc = new IntInsnNode(Opcodes.SIPUSH, pred.shortValue());
                                break;
                            case Type.INT:
                                ldc = new LdcInsnNode(pred.intValue());
                                break;
                            default:
                                throw new Error("Invalid pred type: " + ptype.getSort());
                        }

                        mn.instructions.insertBefore(min,ldc);
                    }

                } else if(ain.getOpcode() == Opcodes.GETSTATIC) {

                    FieldInsnNode fin = (FieldInsnNode) ain;

                    if (profiler.getFieldDef(fin.owner, fin.name, fin.desc) == null) { // Not referencing a rs-field
                        continue;
                    }

                    String owner = remaper.map0(fin.owner);
                    String name = getFieldName(fin);
                    String desc = remaper.mapDesc0(fin.desc);

                    fin.owner = resolver.resolveStaticFieldOwner(owner, name, desc);
                    fin.name  = resolver.resolveFieldName(owner, name, desc, false);
                    fin.desc  = remaper.mapDesc(fin.desc);

                    if (resolver.hasCodec(owner, name, desc, true)) {
                        Number codec = resolver.getDecoder(owner, name, desc, true);
                        if (codec == null) throw new Error();
                        codec = RSUtil.toJavaNumber(codec);
                        if (!(codec instanceof Integer || codec instanceof Long)) throw new Error();
                        long v = codec.longValue();
                        boolean imul = v >= Integer.MIN_VALUE && v <= Integer.MAX_VALUE;
                        InsnList stack = new InsnList();
                        if(imul) stack.add(new LdcInsnNode(new Integer((int)v)));
                        else     stack.add(new LdcInsnNode(new Long(v)));
                        stack.add(new InsnNode(imul ? Opcodes.IMUL : Opcodes.LMUL));
                        mn.instructions.insert(ain, stack);
                    }

                } else if(ain.getOpcode() == Opcodes.PUTSTATIC) {

                    FieldInsnNode fin = (FieldInsnNode) ain;

                    if (profiler.getFieldDef(fin.owner, fin.name, fin.desc) == null) { // Not referencing a rs-field
                        continue;
                    }

                    String owner = remaper.map0(fin.owner);
                    String name = getFieldName(fin);
                    String desc = remaper.mapDesc0(fin.desc);

                    fin.owner = resolver.resolveStaticFieldOwner(owner,name,desc);
                    fin.name  = resolver.resolveFieldName(owner,name,desc,false);
                    fin.desc  = remaper.mapDesc(fin.desc);

                    if (resolver.hasCodec(owner, name, desc, true)) {
                        Number codec = resolver.getEncoder(owner, name, desc, true);
                        if (codec == null) throw new Error();
                        codec = RSUtil.toJavaNumber(codec);
                        if (!(codec instanceof Integer || codec instanceof Long)) throw new Error();
                        long v = codec.longValue();
                        boolean imul = v >= Integer.MIN_VALUE && v <= Integer.MAX_VALUE;
                        InsnList stack = new InsnList();
                        if(imul) stack.add(new LdcInsnNode(new Integer((int)v)));
                        else     stack.add(new LdcInsnNode(new Long(v)));
                        stack.add(new InsnNode(imul ? Opcodes.IMUL : Opcodes.LMUL));
                        mn.instructions.insertBefore(ain, stack);
                    }



                }

            }
        }

    }


    private void appyCodec(ClassNode cn) {
        for (MethodNode mn : (List<MethodNode>) cn.methods) {
            for (AbstractInsnNode ain : mn.instructions.toArray()) {
                int op = ain.getOpcode();
                if (op == Opcodes.GETFIELD) {

                    FieldInsnNode fin = (FieldInsnNode) ain;

                    if (profiler.getFieldDef(fin.owner, fin.name, fin.desc) == null) { // Not referencing a rs-field
                        continue;
                    }

                    boolean is_static = op == Opcodes.GETSTATIC;

                    assert is_static == profiler.isStaticField(fin.owner, fin.name, fin.desc);

                    String owner = remaper.map0(fin.owner);
                    String name = getFieldName(fin);
                    String desc = remaper.mapDesc0(fin.desc);

                    if (resolver.hasCodec(owner, name, desc, is_static)) {
                        Number codec = resolver.getDecoder(owner, name, desc, is_static);
                        if (codec == null) throw new Error();
                        codec = RSUtil.toJavaNumber(codec);
                        if (!(codec instanceof Integer || codec instanceof Long)) throw new Error();
                        long v = codec.longValue();
                        boolean imul = v >= Integer.MIN_VALUE && v <= Integer.MAX_VALUE;
                        InsnList stack = new InsnList();
                        if(imul) stack.add(new LdcInsnNode(new Integer((int)v)));
                        else     stack.add(new LdcInsnNode(new Long(v)));
                        stack.add(new InsnNode(imul ? Opcodes.IMUL : Opcodes.LMUL));
                        mn.instructions.insert(ain, stack);
                    }

                } else if (op == Opcodes.PUTFIELD) {
                    FieldInsnNode fin = (FieldInsnNode) ain;

                    if (profiler.getFieldDef(fin.owner, fin.name, fin.desc) == null) { // Not referencing a rs-field
                        continue;
                    }

                    boolean is_static = op == Opcodes.PUTSTATIC;

                    assert is_static == profiler.isStaticField(fin.owner, fin.name, fin.desc);

                    String owner = remaper.map0(fin.owner);
                    String name = getFieldName(fin);
                    String desc = remaper.mapDesc0(fin.desc);

                    if (resolver.hasCodec(owner, name, desc, is_static)) {
                        Number codec = resolver.getEncoder(owner, name, desc, is_static);
                        if (codec == null) throw new Error();
                        codec = RSUtil.toJavaNumber(codec);
                        if (!(codec instanceof Integer || codec instanceof Long)) throw new Error();
                        long v = codec.longValue();
                        boolean imul = v >= Integer.MIN_VALUE && v <= Integer.MAX_VALUE;
                        InsnList stack = new InsnList();
                        if(imul) stack.add(new LdcInsnNode(new Integer((int)v)));
                        else     stack.add(new LdcInsnNode(new Long(v)));
                        stack.add(new InsnNode(imul ? Opcodes.IMUL : Opcodes.LMUL));
                        mn.instructions.insertBefore(ain, stack);
                    }

                }
            }
        }
    }

    private static ClassNode load(Class clazz) throws IOException {
        InputStream in = clazz.getResourceAsStream(clazz.getSimpleName() + ".class");
        ClassNode cn = new ClassNode();
        ClassReader reader = new ClassReader(in);
        reader.accept(cn, ClassReader.EXPAND_FRAMES);
        return cn;
    }
    
    public static void main(String[] args) {
    	System.out.println("HI");
    }


}
