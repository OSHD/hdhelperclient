package com.hdhelper.agent.mod;

import com.hdhelper.agent.ClientCanvas;
import com.hdhelper.agent.mod.mem.FieldMember;
import com.hdhelper.agent.mod.mem.MethodMember;
import com.hdhelper.agent.util.ASMUtil;
import com.hdhelper.peer.*;
import jdk.internal.org.objectweb.asm.tree.*;
import jdk.nashorn.internal.codegen.types.Type;

import java.awt.*;
import java.util.Map;

/**
 * Created by Jamie on 10/21/2015.
 */
public class ClientMod extends InjectionModule {

    public static final FieldMember LOCAL;
    public static final FieldMember PLAYERS;
    public static final FieldMember NPCS;

    public static final MethodMember GET_ITEM_DEF;
    public static final MethodMember GET_OBJECT_DEF;

    public static final FieldMember BASE_X;
    public static final FieldMember BASE_Y;
    public static final FieldMember FLOOR;

    public static final FieldMember PITCH;
    public static final FieldMember YAW;

    public static final FieldMember CAM_X;
    public static final FieldMember CAM_Y;
    public static final FieldMember CAM_Z;

    public static final FieldMember TILE_HEIGHTS;
    public static final FieldMember RENDER_RULES;

    public static final FieldMember VIEWPORT_SCALE;
    public static final FieldMember VIEWPORT_WIDTH;
    public static final FieldMember VIEWPORT_HEIGHT;

    static {


        LOCAL = new FieldMember("ao","hb",PlayerMod.PLAYER_DESC,true);
        PLAYERS = new FieldMember("client","gz","[" + PlayerMod.PLAYER_DESC,true);
        NPCS = new FieldMember("client","cy","[" + NpcMod.NPC_DESC,true);

        GET_ITEM_DEF = new MethodMember("v","d","(II)" + ItemDefintionMod.ITEM_DEF_DESC, -1230771441,true);
        GET_OBJECT_DEF = new MethodMember("dx","y","(II)" + ItemDefintionMod.ITEM_DEF_DESC, 159,true);

        BASE_X = new FieldMember("ar","cs","I",1997326677,true);
        BASE_Y = new FieldMember("d","dg","I",832137801,true);
        FLOOR = new FieldMember("d","gc", "I", 1970135321,true);

        PITCH = new FieldMember("az","fg","I",602192145,true);
        YAW   = new FieldMember("ah","fh","I",2059944801,true);

        CAM_X = new FieldMember("b","fe","I",2135970033,true);
        CAM_Y = new FieldMember("z","fi","I",1400779075,true);
        CAM_Z = new FieldMember("n","fj","I",2108432335,true);

        TILE_HEIGHTS = new FieldMember("c","y","[[[I",true);
        RENDER_RULES = new FieldMember("c","d","[[[B",true);

        VIEWPORT_SCALE = new FieldMember("client","oz","I",123737557,true);
        VIEWPORT_WIDTH = new FieldMember("ee","qr","I",-1382612291,true);
        VIEWPORT_HEIGHT = new FieldMember("fr","qg","I",-1541523505,true);

    }

    @Override
    public void inject(Map<String, ClassNode> classes) {

        ClassNode client = classes.get("client");
        client.interfaces.add(Type.getInternalName(RSClient.class));

        client.methods.add(ASMUtil.mkGetter("getRegionBaseX", BASE_X));
        client.methods.add(ASMUtil.mkGetter("getRegionBaseY", BASE_Y));
        client.methods.add(ASMUtil.mkGetter("getFloor", FLOOR));

        client.methods.add(ASMUtil.mkGetter("getMyPlayer", Type.getMethodDescriptor(RSPlayer.class), LOCAL));
        client.methods.add(ASMUtil.mkGetter("getPlayers", Type.getMethodDescriptor(RSPlayer[].class), PLAYERS));
        client.methods.add(ASMUtil.mkGetter("getNpcs", Type.getMethodDescriptor(RSNpc[].class), NPCS));

        client.methods.add(mkGetItemDef());
        client.methods.add(mkGetObjectDef());

        client.methods.add(ASMUtil.mkGetter("getPitch", PITCH));
        client.methods.add(ASMUtil.mkGetter("getYaw", YAW));

        client.methods.add(ASMUtil.mkGetter("getCameraX", CAM_X));
        client.methods.add(ASMUtil.mkGetter("getCameraY", CAM_Y));
        client.methods.add(ASMUtil.mkGetter("getCameraZ", CAM_Z));

        client.methods.add(ASMUtil.mkGetter("getTileHeights",TILE_HEIGHTS));
        client.methods.add(ASMUtil.mkGetter("getRenderRules",RENDER_RULES));


        client.methods.add(ASMUtil.mkGetter("getViewportScale", VIEWPORT_SCALE));
        client.methods.add(ASMUtil.mkGetter("getViewportWidth", VIEWPORT_WIDTH));
        client.methods.add(ASMUtil.mkGetter("getViewportHeight",VIEWPORT_HEIGHT));

        hackCanvas(classes);


    }

    static MethodNode mkGetItemDef() {
        MethodMember def = GET_ITEM_DEF;
        MethodNode getItemDef = new MethodNode(ACC_PUBLIC|ACC_FINAL, "getItemDef",
                Type.getMethodDescriptor(RSItemDefinition.class, int.class),null,null);
        InsnList stack = getItemDef.instructions;
        stack.add(new VarInsnNode(ILOAD,1));
        stack.add(new LdcInsnNode(def.getDummy()));
        stack.add(new MethodInsnNode(INVOKESTATIC,def.getOwner(),def.getName(),def.getDesc(), false));
        stack.add(new InsnNode(ARETURN));
        return getItemDef;
    }

    static MethodNode mkGetObjectDef() {
        MethodMember def = GET_OBJECT_DEF;
        MethodNode getItemDef = new MethodNode(ACC_PUBLIC|ACC_FINAL, "getObjectDef",
                Type.getMethodDescriptor(RSObjectDefintion.class, int.class),null,null);
        InsnList stack = getItemDef.instructions;
        stack.add(new VarInsnNode(ILOAD,1));
        stack.add(new LdcInsnNode(def.getDummy()));
        stack.add(new MethodInsnNode(INVOKESTATIC,def.getOwner(),def.getName(),def.getDesc(), false));
        stack.add(new InsnNode(ARETURN));
        return getItemDef;
    }


    private static void hackCanvas(Map<String, ClassNode> classes) {
        for (final ClassNode cn : classes.values()) {
            if (!cn.superName.equals(Type.getInternalName(Canvas.class))) continue;
            cn.superName = Type.getInternalName(ClientCanvas.class);
            for (final MethodNode mn : cn.methods) {
                if (!mn.name.equals("<init>")) continue;
                for (final AbstractInsnNode ain : mn.instructions.toArray()) {
                    if (ain.getOpcode() != INVOKESPECIAL) continue;

                    final MethodInsnNode min = (MethodInsnNode) ain;
                    if (min.owner.equals(Type.getInternalName(Canvas.class)) && mn.name.equals("<init>")) {
                        min.owner = Type.getInternalName(ClientCanvas.class);
                    }

                }
            }
        }

    }

}
