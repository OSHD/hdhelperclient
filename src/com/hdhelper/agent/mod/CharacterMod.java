package com.hdhelper.agent.mod;

import com.hdhelper.agent.mod.mem.FieldMember;
import com.hdhelper.agent.util.ASMUtil;
import com.hdhelper.peer.RSCharacter;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.util.Map;

public class CharacterMod extends InjectionModule {

    public static final String CHARACTER = "aq";
    public static final String CHARACTER_DESC = "L" + CHARACTER + ";";
    

    public static final FieldMember STRICT_X;
    public static final FieldMember STRICT_Y;
    public static final FieldMember ANIMATION;
    public static final FieldMember TARGET;
    public static final FieldMember ORIENTATION;

    static {
        STRICT_X = new FieldMember(CHARACTER,"i","I",-1453400901);
        STRICT_Y = new FieldMember(CHARACTER,"ah","I",-1718522929);
        ANIMATION = new FieldMember(CHARACTER,"bh","I",-464776311);
        TARGET = new FieldMember(CHARACTER,"at","I",-143123477);
        ORIENTATION = new FieldMember(CHARACTER,"cy","I",1844861447); //TODO WRONG
    }

    @Override
    public void inject(Map<String, ClassNode> classes) {
        ClassNode cn = classes.get(CHARACTER);
        cn.interfaces.add(Type.getInternalName(RSCharacter.class));
        cn.methods.add(ASMUtil.mkGetter("getStrictX",STRICT_X));
        cn.methods.add(ASMUtil.mkGetter("getStrictY",STRICT_Y));
        cn.methods.add(ASMUtil.mkGetter("getAnimation", ANIMATION));
        cn.methods.add(ASMUtil.mkGetter("getTargetIndex",TARGET));
        cn.methods.add(ASMUtil.mkGetter("getOrientation",ORIENTATION));
    }

}
