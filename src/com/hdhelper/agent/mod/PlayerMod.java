package com.hdhelper.agent.mod;

import com.hdhelper.agent.mod.mem.FieldMember;
import com.hdhelper.agent.util.ASMUtil;
import com.hdhelper.peer.RSPlayer;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * Created by Jamie on 10/21/2015.
 */
public class PlayerMod extends InjectionModule {

    public static final String PLAYER = "g";
    public static final String PLAYER_DESC = "L" + PLAYER + ";";

    public static final FieldMember CB_LEVEL;
    public static final FieldMember NAME;

    static {

        CB_LEVEL = new FieldMember(PLAYER,"e","I",-603916511);
        NAME = new FieldMember(PLAYER,"y", Type.getDescriptor(String.class));

    }

    @Override
    public void inject(Map<String, ClassNode> classes) {
        ClassNode cn = classes.get(PLAYER);
        cn.interfaces.add(Type.getInternalName(RSPlayer.class));
        cn.methods.add(ASMUtil.mkGetter("getCombatLevel",CB_LEVEL));
        cn.methods.add(ASMUtil.mkGetter("getName",NAME));

    }
}
