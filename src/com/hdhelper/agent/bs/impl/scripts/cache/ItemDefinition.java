package com.hdhelper.agent.bs.impl.scripts.cache;

import com.hdhelper.agent.bs.impl.scripts.collection.DualNode;
import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.peer.RSItemDefinition;

@ByteScript(name = "ItemDefinition")
public class ItemDefinition extends DualNode implements RSItemDefinition {

    @BField public String name;

    @Override
    public String getName() {
        return name;
    }

}
