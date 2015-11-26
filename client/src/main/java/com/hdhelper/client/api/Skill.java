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

    public int getId() {
        return ordinal();
    }

}
