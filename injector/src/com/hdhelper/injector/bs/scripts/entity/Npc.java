package com.hdhelper.injector.bs.scripts.entity;

import com.hdhelper.agent.bs.scripts.cache.NpcDefinition;
import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSNpc;
import com.hdhelper.agent.services.RSNpcDefinition;

@ByteScript(name = "Npc")
public class Npc extends com.hdhelper.agent.bs.scripts.entity.Character implements RSNpc {

    @BField
    NpcDefinition definition;



    @Override
    public RSNpcDefinition getDef() {
        return definition;
    }

}
