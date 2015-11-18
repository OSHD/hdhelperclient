package com.hdhelper.injector.bs.scripts.entity;

import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSNpc;
import com.hdhelper.agent.services.RSNpcDefinition;
import com.hdhelper.injector.bs.scripts.cache.NpcDefinition;

@ByteScript(name = "Npc")
public class Npc extends Character implements RSNpc {

    @BField
    NpcDefinition definition;



    @Override
    public RSNpcDefinition getDef() {
        return definition;
    }

}
