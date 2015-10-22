package com.hdhelper.agent.mod;

import com.hdhelper.agent.mod.mem.FieldMember;
import com.hdhelper.agent.util.ASMUtil;
import com.hdhelper.peer.RSItemDefinition;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.nashorn.internal.codegen.types.Type;

import java.util.Map;

/**
 * Created by Jamie on 10/21/2015.
 */
public class ItemDefintionMod extends InjectionModule {

    public static final String ITEM_DEF = "at";
    public static final String ITEM_DEF_DESC = "L" + ITEM_DEF + ";";

    public static final FieldMember NAME;

    static {

        NAME = new FieldMember(ITEM_DEF,"s", ASMUtil.getDescriptor(String.class));

    }
    @Override
    public void inject(Map<String, ClassNode> classes) {

        ClassNode itmDef = classes.get(ITEM_DEF);
        itmDef.interfaces.add( Type.getInternalName(RSItemDefinition.class));

        itmDef.methods.add( ASMUtil.mkGetter("getName", NAME) );

    }
}
