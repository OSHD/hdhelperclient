package com.hdhelper.injector.bs.scripts.util;

import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSPlayerConfig;

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

