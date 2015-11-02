package com.hdhelper.agent.bs.impl.scripts.util;

import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.peer.RSPlayerConfig;

@ByteScript(name = "PlayerConfig")
public class PlayerConfig implements RSPlayerConfig {

    @BField int[] appearance;
    @BField int[] appearanceColors;
    @BField boolean female;




    @Override
    public int[] getEquipment() {
        return appearance;
    }

    @Override
    public int[] getEquipmentColors() {
        return appearanceColors;
    }

    @Override
    public boolean isFemale() {
        return female;
    }

}

