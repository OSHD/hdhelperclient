package com.hdhelper.client.api;

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

    Skill() {
        String name0 = name();
        this.name = name0.charAt(0) + name0.toLowerCase().substring(1);
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
