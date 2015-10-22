package com.hdhelper.agent.mod;

import com.hdhelper.agent.mod.mem.FieldMember;
import com.hdhelper.agent.util.ASMUtil;
import com.hdhelper.peer.RSNpc;
import com.hdhelper.peer.RSNpcDefintion;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * Created by Jamie on 10/21/2015.
 */
public class NpcMod extends InjectionModule {

    public static final String NPC = "ay";
    public static final String NPC_DESC = "L" + NPC + ";";

    public static final FieldMember DEF;

    static {

        DEF = new FieldMember(NPC,"y",NpcDefintionMod.NPC_DEFINTION_DESC);

    }

    @Override
    public void inject(Map<String, ClassNode> classes) {
        ClassNode cn = classes.get(NPC);
        cn.interfaces.add(Type.getInternalName(RSNpc.class));
        cn.methods.add(ASMUtil.mkGetter("getDef", jdk.nashorn.internal.codegen.types.Type.getMethodDescriptor(RSNpcDefintion.class),DEF));

    }
}
