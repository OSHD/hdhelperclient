package com.hdhelper.agent.mod;

import com.hdhelper.agent.mod.mem.FieldMember;
import com.hdhelper.agent.util.ASMUtil;
import com.hdhelper.peer.RSItemTable;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.nashorn.internal.codegen.types.Type;

import java.util.Map;

/**
 * Created by Jamie on 10/21/2015.
 */
public class ItemTableMod extends InjectionModule {

    public static final String ITEM_CONTAINER = "g";
    public static final String ITEM_CONTAINER_DESC = "L" + ITEM_CONTAINER + ";";

    public static final FieldMember IDS;
    public static final FieldMember QUANTITIES;

    static  {

        IDS = new FieldMember(ITEM_CONTAINER,"v","[I");
        QUANTITIES = new FieldMember(ITEM_CONTAINER,"f","[I");

    }


    @Override
    public void inject(Map<String, ClassNode> classes) {
        ClassNode con = classes.get(ITEM_CONTAINER);
        con.interfaces.add(Type.getInternalName(RSItemTable.class));

        con.methods.add( ASMUtil.mkGetter("getIds", IDS) );
        con.methods.add( ASMUtil.mkGetter("getQuantities",QUANTITIES) );
    }
}
