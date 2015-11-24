package com.hdhelper.injector.patch;


import org.objectweb.asm.Type;

public class GMethod {

    String owner;
    String name;
    String desc;
    Number predicate;

    int access;

    public GMethod() {}

    public GMethod(String owner, String name, String desc, Number pred) {
        this.owner = owner;
        this.name = name;
        this.desc = desc;
    }
    private String pubDesc = null;
    public String getPublicDesc(GPatch p) {
        if(pubDesc != null) return pubDesc;
        return pubDesc = p.mapMethodDesc(desc);
    }

    private String pubDescNoPred = null;
    public String getPubDescNoPred(GPatch p) {
        if(pubDescNoPred != null) return pubDescNoPred;
        if(predicate == null) {
            return pubDescNoPred = getPublicDesc(p);
        }
        return pubDescNoPred = removePred(getPublicDesc(p));
    }

    public boolean isValid() {
        return owner != null && name != null && desc != null;
    }

    public String getName() {
        return name;
    }

    public boolean hasPredicate() {
        return predicate != null;
    }

    public Number getPredicate() {
        return predicate;
    }

    private String no_pred_desc;
    public String getNoPredDesc() {
        if(!hasPredicate()) return desc;
        if(no_pred_desc != null) return no_pred_desc;
        return no_pred_desc = removePred(desc);
    }

    // Remove the final argument from the descriptor.
    // The predicate/final arg should be either a byte,short,or int.
    private static String removePred(String desc) {
        Type[] args = Type.getArgumentTypes(desc);
        if(args.length == 0)
            throw new Error("no predicate argument");
        Type pred = args[args.length-1];
        int sort = pred.getSort();
        if(sort != Type.BYTE && sort != Type.SHORT && sort != Type.INT)
            throw new Error("invalid pred type: " + sort + "," + desc);
        Type[] types_no_pred = new Type[args.length-1];
        System.arraycopy(args,0,types_no_pred,0,args.length-1);
        return Type.getMethodDescriptor(Type.getReturnType(desc),types_no_pred);
    }

    public static String addPredicate(String desc, Type predType) {
        Type[] args = Type.getArgumentTypes(desc);
        Type[] types_pred = new Type[args.length+1];
        System.arraycopy(args,0,types_pred,0,args.length);
        types_pred[types_pred.length-1] = predType;
        return Type.getMethodDescriptor(Type.getReturnType(desc),types_pred);
    }

    public String getOwner() {
        return owner;
    }

    public String getDesc() {
        return desc;
    }

    Type predType;
    public Type getPredicateType() {
        if(!hasPredicate()) return null;
        if(predType != null) return predType;
        Type[] args = Type.getArgumentTypes(desc);
        return predType = args[args.length-1];
    }
}
