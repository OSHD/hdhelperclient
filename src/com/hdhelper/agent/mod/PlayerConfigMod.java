package com.hdhelper.agent.mod;

import com.hdhelper.agent.mod.mem.FieldMember;
import com.hdhelper.agent.util.ASMUtil;
import com.hdhelper.peer.RSPlayerConfig;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.util.Map;
public class PlayerConfigMod extends InjectionModule {

    public static final String PLAYER_CONFIG = "fv";
    public static final String PLAYER_CONFIG_DESC = "L" + PLAYER_CONFIG + ";";

    public static final FieldMember EQUIPMENT;
    public static final FieldMember EQUIPMENT_COLORS;
    public static final FieldMember FEMALE;

    static {

        EQUIPMENT = new FieldMember(PLAYER_CONFIG,"i","[I");
        EQUIPMENT_COLORS = new FieldMember(PLAYER_CONFIG,"v","[I"); //TODO not to sure
        FEMALE = new FieldMember(PLAYER_CONFIG,"r","Z");

    }


    @Override
    public void inject(Map<String, ClassNode> classes) {

        ClassNode cn = classes.get(PLAYER_CONFIG);
        cn.interfaces.add(Type.getInternalName(RSPlayerConfig.class));

        cn.methods.add(ASMUtil.mkGetter("getEquipment",EQUIPMENT));
        cn.methods.add(ASMUtil.mkGetter("getEquipmentColors",EQUIPMENT_COLORS));
        cn.methods.add(ASMUtil.mkGetter("isFemale",FEMALE));

    }


}
