package com.bytescript.compiler;

import org.objectweb.asm.Type;

public interface BSResolver {

    //Standard:
    String resolveName(String bs);
    String resolveMethodName(String owner, String name, String desc, boolean stat);
    String resolveFieldName(String owner, String name, String desc, boolean stat);

    String resolveStaticMethodOwner(String owner, String name, String desc);
    String resolveStaticFieldOwner(String owner, String name, String desc);

    //Fields:
    boolean hasCodec(String owner, String name, String desc, boolean stat);
    Number getEncoder(String owner, String name, String desc, boolean stat);
    Number getDecoder(String owner, String name, String desc, boolean stat);

    //Methods: The desc is referenced with no preds
    boolean hasPredicate(String owner, String name, String desc, boolean stat);
    Number getPredicate(String owner, String desc, String name, boolean stat);
    Type getPredicateType(String owner, String name, String desc, boolean stat);
}
