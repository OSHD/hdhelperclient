package com.hdhelper.agent.mod;

import com.hdhelper.agent.mod.mem.FieldMember;
import com.hdhelper.agent.util.ASMUtil;
import com.hdhelper.agent.peer.RSNpc;
import com.hdhelper.agent.peer.RSNpcDefinition;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * Created by Jamie on 10/21/2015.
 */
public class NpcMod extends InjectionModule {

    public static final String NPC = "aq";
    public static final String NPC_DESC = "L" + NPC + ";";

    public static final FieldMember DEF;

    static {

        DEF = new FieldMember(NPC,"i",NpcDefintionMod.NPC_DEFINTION_DESC);

    }

    @Override
    public void inject(Map<String, ClassNode> classes) {
        ClassNode cn = classes.get(NPC);
        cn.interfaces.add(Type.getInternalName(RSNpc.class));
        cn.methods.add(ASMUtil.mkGetter("getDef", ASMUtil.getMethodDescriptor(RSNpcDefinition.class),DEF));

    }
}
