package com.hdhelper.agent.peer;

public interface RSPlayer extends RSCharacter {
    String getName();
    int getCombatLevel();
    RSPlayerConfig getConfig();
    int getZ();
    int getPrayerIcon();
    int getSkullIcon();
}
