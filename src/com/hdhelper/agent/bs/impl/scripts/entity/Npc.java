package com.hdhelper.agent.bs.impl.scripts.entity;

import com.hdhelper.agent.bs.impl.scripts.cache.NpcDefinition;
import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.peer.RSNpc;
import com.hdhelper.peer.RSNpcDefinition;

@ByteScript(name = "Npc")
public class Npc extends com.hdhelper.agent.bs.impl.scripts.entity.Character implements RSNpc {

    @BField
    NpcDefinition definition;



    @Override
    public RSNpcDefinition getDef() {
        return definition;
    }

}
