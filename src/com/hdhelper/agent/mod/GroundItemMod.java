package com.hdhelper.agent.mod;

import com.hdhelper.agent.mod.mem.FieldMember;
import com.hdhelper.agent.util.ASMUtil;
import com.hdhelper.peer.RSGroundItem;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * Created by Jamie on 10/22/2015.
 */
public class GroundItemMod extends InjectionModule {

    public static final String GROUND_ITEM = "ah";
    public static final String GROUND_ITEM_DESC = "L" + GROUND_ITEM + ";";

    public static final FieldMember ID;
    public static final FieldMember NUM;

    static {
        ID  = new FieldMember(GROUND_ITEM,"i","I",1898835819);
        NUM = new FieldMember(GROUND_ITEM,"v","I",-824129891);
    }

    @Override
    public void inject(Map<String, ClassNode> classes) {
        ClassNode cn = classes.get(GROUND_ITEM);
        cn.interfaces.add(Type.getInternalName(RSGroundItem.class));

        cn.methods.add(ASMUtil.mkGetter("getId",ID));
        cn.methods.add(ASMUtil.mkGetter("getQuantity",NUM));

    }
}
