package com.hdhelper.agent.bs.impl.patch;

import java.util.Map;

public class GClass {

    String name;

    Map<String,GField> fields;
    Map<String,GMethod> methods;

    public String getName() {
        return name;
    }

    public GField getField(String pubName, String pubDesc, GPatch p) {
        if(fields == null) return null;
        GField field = fields.get(pubName);
        if(field == null) return null;
        if(!field.getPublicDesc(p).equals(pubDesc)) return null;
        return field;
    }

    public GMethod getMethod(String pubName, String pubDesc, GPatch p) {
        if(methods == null) return null;
        GMethod method = methods.get(pubName);
        if(method == null) return null;
        if(!method.getPubDescNoPred(p).equals(pubDesc)) return null;
        return method;
    }

}
