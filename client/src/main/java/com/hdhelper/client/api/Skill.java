package com.hdhelper.client.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Skill {

    ATTACK, // 197
    DEFENCE, // 199
    STRENGTH, // 198
    HITPOINTS,
    RANGED,
    PRAYER,
    MAGIC,
    COOKING,
    WOODCUTTING,
    FLETCHING,
    FISHING,
    FIREMAKING,
    CRAFTING,
    SMITHING,
    MINING,
    HERBLORE,
    AGILITY,
    THIEVING,
    SLAYER,
    FARMING,
    RUNECRAFTING,
    HUNTER,
    CONSTRUCTION;

    String name;

    private static Map<String,Skill> name2SkillMap;

    Skill() {
        String name0 = name();
        this.name = name0.charAt(0) + name0.toLowerCase().substring(1);
    }

    public static Skill forName(String name) {
        if(name2SkillMap == null) {
            //init map:
            synchronized (Skill.class) {
                Map<String,Skill> map = new HashMap<String, Skill>();
                for(Skill skill : Skill.values()) {
                    map.put(skill.name,skill);
                }
                name2SkillMap = Collections.unmodifiableMap(map);
            }
        }
        return name2SkillMap.get(name);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return ordinal();
    }

    @Override
    public String toString() {
        return getName();
    }

}
