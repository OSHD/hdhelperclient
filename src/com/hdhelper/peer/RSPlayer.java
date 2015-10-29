package com.hdhelper.peer;

public interface RSPlayer extends RSCharacter {
    String getName();
    int getCombatLevel();
    RSPlayerConfig getConfig();
    int getZ();
}
