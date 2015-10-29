package com.hdhelper.agent.mod;

import com.hdhelper.agent.mod.mem.FieldMember;
import com.hdhelper.agent.util.ASMUtil;
import com.hdhelper.peer.RSEntity;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * Created by Jamie on 10/22/2015.
 */
public class EntityMod extends InjectionModule {

    public static final String RENDERABLE = "ck";
    public static final String RENDERABLE_DESC = "L" + RENDERABLE + ";";

    public static final FieldMember HEIGHT;

    static {

        HEIGHT = new FieldMember(RENDERABLE,"cb","I",1513659429);

    }

    @Override
    public void inject(Map<String, ClassNode> classes) {
        ClassNode cn = classes.get(RENDERABLE);
        cn.interfaces.add(Type.getInternalName(RSEntity.class));

        cn.methods.add(ASMUtil.mkGetter("getHeight",HEIGHT));
    }
}
