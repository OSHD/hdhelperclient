package com.hdhelper.agent.bs.impl.scripts.cache;

import com.hdhelper.agent.bs.impl.scripts.collection.DualNode;
import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.BMethod;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.agent.peer.RSNpcDefinition;

@ByteScript(name = "NpcDefinition")
public class NpcDefinition extends DualNode implements RSNpcDefinition {

    @BField int varpIndex;
    @BField short[] modifiedColors;
    @BField String name;
    @BField int varp32Index;
    @BField int[] transformIds;
    @BField int id;
    @BField int combatLevel;
    @BField String[] actions;
    @BField short[] colors;





    @BMethod(name = "transform")
    public NpcDefinition transform0() {
        return null;
    }


    @Override
    public int getCombatLevel() {
        return combatLevel;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public RSNpcDefinition transform() {
        return transform0();
    }

}
