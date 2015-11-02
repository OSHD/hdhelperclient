package com.hdhelper.agent.bs.impl.scripts;

import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.BMethod;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.peer.RSNpcDefintion;

@ByteScript(name = "NpcDefinition")
public class NpcDefinition extends DualNode implements RSNpcDefintion {

    @BField int varpIndex;
    @BField short[] modifiedColors;
    @BField String name;
    @BField int varp32Index;
    @BField int[] transformIds;
    @BField int id;
    @BField int combatLevel;
    @BField String[] actions;
    @BField short[] colors;


    @Override
    public String getName() {
        return null;
    }

    @BMethod
    public NpcDefinition transform() {
        return null;
    }

    @Override
    public int getId() {
        return 0;
    }


}
