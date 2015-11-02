package com.hdhelper.agent.bs.impl.scripts;

import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.peer.RSNpc;
import com.hdhelper.peer.RSNpcDefintion;

@ByteScript(name = "Npc")
public class Npc extends Character implements RSNpc {

    @BField NpcDefinition definition;



    @Override
    public RSNpcDefintion getDef() {
        return definition;
    }

}
