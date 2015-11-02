package com.hdhelper.agent.bs.compiler;

import com.hdhelper.agent.bs.lang.*;

public interface BSProfiler {

    Rename       getRename          (String name);
    ByteScript   getScriptDef       (String name);
    BField       getFieldDef        (String owner, String name, String desc);
    BMethod      getMethodDef       (String owner, String name, String desc);
    BConstructor getConstructorDef  (String owner, String name, String desc);

    boolean isStaticField  (String owner, String name, String desc);
    boolean isStaticMethod (String owner, String name, String desc);

}
