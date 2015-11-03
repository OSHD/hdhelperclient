package com.hdhelper.agent.bs.impl.scripts.entity;

import com.hdhelper.agent.bs.impl.scripts.util.PlayerConfig;
import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.peer.RSPlayer;
import com.hdhelper.peer.RSPlayerConfig;

@ByteScript(name = "Player")
public class Player extends com.hdhelper.agent.bs.impl.scripts.entity.Character implements RSPlayer {

    @BField String name;
    @BField int combatLevel;
    @BField int height;
    @BField PlayerConfig config;
    @BField int prayerIcon;
    @BField int skullIcon;




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
}
