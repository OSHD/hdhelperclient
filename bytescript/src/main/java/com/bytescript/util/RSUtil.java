package com.bytescript.util;

import org.objectweb.asm.Type;

import java.text.NumberFormat;

public class RSUtil {

    public static String addPredicate(String desc, Type predType) {
        Type[] args = Type.getArgumentTypes(desc);
        Type[] types_pred = new Type[args.length+1];
        System.arraycopy(args,0,types_pred,0,args.length);
        types_pred[types_pred.length-1] = predType;
        return Type.getMethodDescriptor(Type.getReturnType(desc),types_pred);
    }

    //TODO super lazy
    // GSon wraps numbers into their own
    public static Number toJavaNumber(Number n) {
        Number num = null;
        try {
            num = NumberFormat.getInstance().parse(n.toString());
        } catch (Exception e) {
            throw new Error(e);
        }
        return num;
    }


}
