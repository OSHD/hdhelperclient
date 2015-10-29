package com.hdhelper.agent.mod;

import com.hdhelper.agent.mod.mem.FieldMember;
import com.hdhelper.agent.util.ASMUtil;
import com.hdhelper.peer.RSPlayer;
import com.hdhelper.peer.RSPlayerConfig;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * Created by Jamie on 10/21/2015.
 */
public class PlayerMod extends InjectionModule {

    public static final String PLAYER = "f";
    public static final String PLAYER_DESC = "L" + PLAYER + ";";

    public static final FieldMember CB_LEVEL;
    public static final FieldMember NAME;
    public static final FieldMember CONFIG;
    public static final FieldMember HEIGHT;

    static {

        CB_LEVEL = new FieldMember(PLAYER,"a","I",868548497);
        NAME = new FieldMember(PLAYER,"i", Type.getDescriptor(String.class));
        CONFIG = new FieldMember(PLAYER,"v", PlayerConfigMod.PLAYER_CONFIG_DESC);
        HEIGHT = new FieldMember(PLAYER,"p","I",604542887); //TODO

    }

    @Override
    public void inject(Map<String, ClassNode> classes) {
        ClassNode cn = classes.get(PLAYER);
        cn.interfaces.add(Type.getInternalName(RSPlayer.class));
        cn.methods.add(ASMUtil.mkGetter("getCombatLevel", CB_LEVEL));
        cn.methods.add(ASMUtil.mkGetter("getName",NAME));
        cn.methods.add(ASMUtil.mkGetter("getZ",HEIGHT));
        cn.methods.add(ASMUtil.mkGetter("getConfig", jdk.nashorn.internal.codegen.types.Type.getMethodDescriptor(RSPlayerConfig.class),CONFIG));
    }
}
