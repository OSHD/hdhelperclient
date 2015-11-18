package com.hdhelper.injector.bs.scripts.cache;

import com.hdhelper.agent.bs.scripts.collection.DualNode;
import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSItemDefinition;

@ByteScript(name = "ItemDefinition")
public class ItemDefinition extends DualNode implements RSItemDefinition {

    @BField public String name;

    @Override
    public String getName() {
        return name;
    }

}
