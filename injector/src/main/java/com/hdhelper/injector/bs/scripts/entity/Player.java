package com.hdhelper.injector.bs.scripts.entity;

import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSPlayer;
import com.hdhelper.agent.services.RSPlayerConfig;
import com.hdhelper.injector.bs.scripts.util.PlayerConfig;

@ByteScript(name = "Player")
public class Player extends Character implements RSPlayer {

    @BField String name;
    @BField int combatLevel;
    @BField int height;
    @BField PlayerConfig config;
    @BField int prayerIcon;
    @BField int skullIcon;
    @BField int team;






    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCombatLevel() {
        return combatLevel;
    }

    @Override
    public RSPlayerConfig getConfig() {
        return config;
    }

    @Override
    public int getZ() {
        return height;
    }

    @Override
    public int getPrayerIcon() {
        return prayerIcon;
    }

    @Override
    public int getSkullIcon() {
        return skullIcon;
    }

    @Override
    public int getTeam() {
        return team;
    }
}
