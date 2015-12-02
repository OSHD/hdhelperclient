package com.hdhelper.injector.bs;

import com.bytescript.compiler.BSResolver;
import com.hdhelper.injector.patch.GClass;
import com.hdhelper.injector.patch.GField;
import com.hdhelper.injector.patch.GMethod;
import com.hdhelper.injector.patch.GPatch;
import org.objectweb.asm.Type;

import java.util.HashMap;
import java.util.Map;

public class ResolverImpl implements BSResolver {


    private final GPatch resolver;

    Map<String,GField> fieldCache = new HashMap<String,GField>();
    Map<String,GMethod> methodCache = new HashMap<String,GMethod>();

    public ResolverImpl(GPatch resolver) {
        this.resolver = resolver;

    }

    private static String fieldKey(String owner,String name,String desc) {
        return owner + '.' + name + desc;
    }

    private static String methodKey(String owner,String name,String desc) {
        return owner + '.' + name + desc;
    }

    @Override
    public String resolveName(String bs) {
        GClass clazz = resolver.getGClass(bs);
        if(clazz == null) return null;
        return clazz.getName();
    }


    private GField getField(String owner, String name, String desc, boolean stat) {

        String key = fieldKey(owner, name, desc);
        GField cache = fieldCache.get(key);
        if(cache != null) return cache;

        GClass clazz = resolver.getGClass(owner);
        if(clazz == null) {
            fieldCache.put(key,null);
            return null;
        }

        GField field = clazz.getField(name, desc, resolver);
        fieldCache.put(key,field);
        return field;

    }

    private GMethod getMethod(String owner, String name, String desc, boolean stat) {

        String key = methodKey(owner, name, desc);
        GMethod cache = methodCache.get(key);
        if(cache != null) return cache;

        GClass clazz = resolver.getGClass(owner);
        if(clazz == null) {
            methodCache.put(key,null);
            return null;
        }



        GMethod method = clazz.getMethod(name, desc, resolver);




        methodCache.put(key, method);

        return method;

    }


    @Override
    public String resolveFieldName(String owner, String name, String desc, boolean stat) {
        if(stat) return name;
        GField field = getField(owner,name,desc,stat);
        return field != null ? field.getName() : null;
    }



    @Override
    public String resolveStaticMethodOwner(String owner, String name, String desc) {
        GClass clazz = resolver.getGClass(owner);
        GMethod method = clazz.getMethod(name, desc, resolver);
        if(method == null) return null;
        return method.getOwner();
    }

    @Override
    public String resolveStaticFieldOwner(String owner, String name, String desc) {
        GClass clazz = resolver.getGClass(owner);
        GField field = clazz.getField(name, desc, resolver);
        if(field == null) return null;
        return field.getOwner();
    }

    @Override
    public String resolveMethodName(String owner, String name, String desc, boolean stat) {
        if(stat) return name;
        GMethod method = getMethod(owner,name,desc,stat);
        return method != null ? method.getName() : null;
    }

    @Override
    public boolean hasCodec(String owner, String name, String desc, boolean stat) {
        GField field = getField(owner,name,desc,stat);
        if(field == null) throw new Error("Field not found: " + owner + "." + name + desc);
        return field.hasCodec();
    }

    @Override
    public Number getEncoder(String owner, String name, String desc, boolean stat) {
        GField field = getField(owner,name,desc,stat);
        if(field == null) throw new Error("Field not found: " + owner + "." + name + desc);
        return field.getEncoder();
    }

    @Override
    public Number getDecoder(String owner, String name, String desc, boolean stat) {
        GField field = getField(owner,name,desc,stat);
        if(field == null) throw new Error("Field not found: " + owner + "." + name + desc);
        return field.getDecoder();
    }


    @Override
    public boolean hasPredicate(String owner, String name, String desc, boolean stat) {
        GMethod method = getMethod(owner,name,desc,stat);
        if(method == null) throw new Error("Method not found: " + owner + "." + name + desc);
        return method.hasPredicate();
    }

    @Override
    public Number getPredicate(String owner, String name, String desc, boolean stat) {
        GMethod method = getMethod(owner,name,desc,stat);
        if(method == null) throw new Error("Method not found: " + owner + "." + name + desc);
        return method.getPredicate();
    }

    @Override
    public Type getPredicateType(String owner, String name, String desc, boolean stat) {
        GMethod method = getMethod(owner,name,desc,stat);
        if(method == null) throw new Error("Method not found: " + owner + "." + name + desc);
        return method.getPredicateType();
    }


}
