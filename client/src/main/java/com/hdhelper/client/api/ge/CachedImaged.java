package com.hdhelper.client.api.ge;

import com.hdhelper.agent.services.RSImage;
import com.hdhelper.client.Main;
import com.hdhelper.client.api.Skill;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CachedImaged {

    public static final int GRAND_EXCHANGE_OFFER = 1112;
    public static final int HIGH_ALCH = 41;

    public static final int[] SKILL2IMAGE;

    static {
        SKILL2IMAGE = new int[Skill.values().length];
        Arrays.fill(SKILL2IMAGE, 204); //Defaults. Empty Image
        SKILL2IMAGE[Skill.ATTACK.getId()] = 197;
        SKILL2IMAGE[Skill.STRENGTH.getId()] = 198;
        SKILL2IMAGE[Skill.DEFENCE.getId()] = 199;
        SKILL2IMAGE[Skill.RANGED.getId()] = 200;
        SKILL2IMAGE[Skill.PRAYER.getId()] = 201;
        SKILL2IMAGE[Skill.MAGIC.getId()] = 202;
        SKILL2IMAGE[Skill.HITPOINTS.getId()] = 203;
        SKILL2IMAGE[Skill.AGILITY.getId()] = 204;
        SKILL2IMAGE[Skill.HERBLORE.getId()] = 205;
        SKILL2IMAGE[Skill.THIEVING.getId()] = 206;
        SKILL2IMAGE[Skill.CRAFTING.getId()] = 207;
        SKILL2IMAGE[Skill.RANGED.getId()] = 208;
        SKILL2IMAGE[Skill.MINING.getId()] = 209;
        SKILL2IMAGE[Skill.SMITHING.getId()] = 210;
        SKILL2IMAGE[Skill.FISHING.getId()] = 210;
        SKILL2IMAGE[Skill.COOKING.getId()] = 212;
        SKILL2IMAGE[Skill.FIREMAKING.getId()] = 213;
        SKILL2IMAGE[Skill.WOODCUTTING.getId()] = 214;
        SKILL2IMAGE[Skill.RUNECRAFTING.getId()] = 215;
        SKILL2IMAGE[Skill.SLAYER.getId()] = 216;
        SKILL2IMAGE[Skill.FARMING.getId()] = 217;
    }

    private static Map<Integer,RSImage> cache
            = new HashMap<Integer, RSImage>();

    public static RSImage getImage(int id) {
        RSImage img = cache.get(id);
        if(img != null) return img;
        try {
            ClassLoader loader = Main.client.getClass().getClassLoader();
            Field f = loader.loadClass("bj").getDeclaredField("u");
            Method m = loader.loadClass("ar").getDeclaredMethod("j", loader.loadClass("fa"), int.class, int.class, int.class);
            f.setAccessible(true);
            m.setAccessible(true);
            Object o = f.get(null);
            Object oo = m.invoke(null, o, id, 0, 2097572161);
            RSImage hit = (RSImage) oo;
            cache.put(id,hit);
            return hit;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

}
