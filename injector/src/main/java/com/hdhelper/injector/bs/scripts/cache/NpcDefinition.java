package com.hdhelper.injector.bs.scripts.cache;

import com.bytescript.lang.BField;
import com.bytescript.lang.BMethod;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSNpcDefinition;
import com.hdhelper.injector.bs.scripts.collection.DualNode;

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
