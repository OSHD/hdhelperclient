package com.hdhelper.agent.bs.compiler;

import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.BMethod;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.agent.bs.lang.Rename;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.Remapper;

public class BSRemaper extends Remapper {

    final BSProfiler profiler;
    final BSResolver resolver;

    public BSRemaper(BSProfiler profiler, BSResolver resolver) {
        this.profiler = profiler;
        this.resolver = resolver;
    }

    @Override
    public final String mapMethodName(String owner, String name, String desc) {
        if(name.equals("<init>") || name.equals("<clinit>")) return name;
        BMethod method = profiler.getMethodDef(owner, name, desc);
        if(method == null) {
            return name;
        } else {
            boolean stat = profiler.isStaticMethod(owner,name,desc);
            String resolve_method_name = method.name();
            if(resolve_method_name.equals("this")) {
                resolve_method_name = name;
            }
            String resolve_owner = map0(owner);
            String resolve_desc  = mapMethodDesc0(desc);
            String resolved_name = resolver.resolveMethodName(resolve_owner, resolve_method_name, resolve_desc, stat);
            if(resolved_name == null || resolved_name.isEmpty()) {
                throw new IllegalStateException("Unresolved Method: (" + owner + "." + name + desc + ") | (" + resolve_owner + "." + resolve_method_name + resolve_desc + ")");
            }
            return resolved_name;
        }
    }

    @Override
    public final String mapFieldName(String owner, String name, String desc) {
        BField field = profiler.getFieldDef(owner, name, desc);
        if(field == null) {
            return name;
        } else {
            boolean stat = profiler.isStaticField(owner, name, desc);
            String resolve_field_name = field.name();
            if(resolve_field_name.equals("this")) {
                resolve_field_name = name;
            }
            String resolve_owner = map0(owner);
            String resolve_desc  = mapDesc0(desc);
            String resolved_name = resolver.resolveFieldName(resolve_owner, resolve_field_name, resolve_desc, stat);
            if(resolved_name == null || resolved_name.isEmpty()) {
                throw new IllegalStateException("Unresolved Field: (" + owner + "." + name + desc + ") | (" + resolve_owner + "." + resolve_field_name + resolve_desc + ")");
            }
            return resolved_name;
        }
    }


    public String mapMethodDesc0(String var1) {
        if("()V".equals(var1)) {
            return var1;
        } else {
            Type[] var2 = Type.getArgumentTypes(var1);
            StringBuilder var3 = new StringBuilder("(");

            for(int var4 = 0; var4 < var2.length; ++var4) {
                var3.append(this.mapDesc0(var2[var4].getDescriptor()));
            }

            Type var5 = Type.getReturnType(var1);
            if(var5 == Type.VOID_TYPE) {
                var3.append(")V");
                return var3.toString();
            } else {
                var3.append(')').append(this.mapDesc0(var5.getDescriptor()));
                return var3.toString();
            }
        }
    }


    public String mapDesc0(String var1) {
        Type var2 = Type.getType(var1);
        switch(var2.getSort()) {
            case 9:
                String var3 = this.mapDesc0(var2.getElementType().getDescriptor());

                for(int var5 = 0; var5 < var2.getDimensions(); ++var5) {
                    var3 = '[' + var3;
                }

                return var3;
            case 10:
                String var4 = this.map0(var2.getInternalName());
                if(var4 != null) {
                    return 'L' + var4 + ';';
                }
            default:
                return var1;
        }
    }


    public String map0(String typeName) {
        ByteScript script = profiler.getScriptDef(typeName);
        Rename rename = profiler.getRename(typeName);
        if(script != null && rename != null)
            throw new RuntimeException("Can not have both");
        if(script == null && rename == null)
            return typeName;
        if(script != null) {
            String resolve_name = script.name();
            assert resolve_name != null;
            if (resolve_name.equals("this")) {
                resolve_name = typeName;
            }
            return resolve_name;
        } else { // Rename != null
            String name = rename.name();
            assert name != null;
            if (name.isEmpty()) {
                throw new Error(typeName + " has an empty name");
            }
            return name;
        }
    }

    @Override
    public final String map(String typeName) {
        ByteScript script = profiler.getScriptDef(typeName);
        Rename rename = profiler.getRename(typeName);
        if(script != null && rename != null)
            throw new RuntimeException("Can not have both");
        if(script == null && rename == null)
            return typeName;
        if(script != null) {
            String resolve_name = script.name();
            assert resolve_name != null;
            if(resolve_name.equals("this")) {
                resolve_name = typeName;
            }
            String name = resolver.resolveName(resolve_name);
            if(name == null || name.isEmpty()) {
                throw new Error("Unresolved Type: " + typeName + " (" + resolve_name + ")");
            }
            return name;
        } else { // Rename != null
            String name = rename.name();
            assert name != null;
            if(name.isEmpty()) {
                throw new Error(typeName + " has an empty name");
            }
            return name;
        }
    }

}
