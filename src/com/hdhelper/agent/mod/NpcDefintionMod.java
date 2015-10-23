package com.hdhelper.agent.mod;

import com.hdhelper.agent.mod.mem.FieldMember;
import com.hdhelper.agent.util.ASMUtil;
import com.hdhelper.peer.RSNpcDefintion;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * Created by Jamie on 10/21/2015.
 */
public class NpcDefintionMod extends InjectionModule {

    public static final String NPC_DEFINTION = "ay";
    public static final String NPC_DEFINTION_DESC = "L" + NPC_DEFINTION + ";";

    public static final FieldMember ID;
    public static final FieldMember NAME;

    static {

        ID = new FieldMember(NPC_DEFINTION,"x","I",-764559607);
        NAME = new FieldMember(NPC_DEFINTION,"q", Type.getDescriptor(String.class));

    }

    @Override
    public void inject(Map<String, ClassNode> classes) {

        ClassNode cn = classes.get(NPC_DEFINTION);
        cn.interfaces.add(Type.getInternalName(RSNpcDefintion.class));

        cn.methods.add(ASMUtil.mkGetter("getId", ID));
        cn.methods.add(ASMUtil.mkGetter("getName",NAME));

    }
}
