package com.hdhelper.injector.bs.scripts.cache;

import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSItemDefinition;
import com.hdhelper.injector.bs.scripts.collection.DualNode;

@ByteScript(name = "ItemDefinition")
public class ItemDefinition extends DualNode implements RSItemDefinition {

    @BField public String name;

    @Override
    public String getName() {
        return name;
    }

}
