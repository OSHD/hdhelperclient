package com.hdhelper.injector.bs.scripts.cache;

import com.bytescript.lang.BField;
import com.bytescript.lang.BMethod;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSObjectDefinition;
import com.hdhelper.injector.bs.scripts.collection.DualNode;

@ByteScript(name = "ObjectDefinition")
public class ObjectDefinition extends DualNode implements RSObjectDefinition {

    @BField String name;


    @BMethod(name = "transform")
    public ObjectDefinition transform0() {
        return null;
    }





    @Override
    public RSObjectDefinition transform() {
        return transform0();
    }

    @Override
    public String getName() {
        return name;
    }

}
