package com.hdhelper.agent.bs.impl.scripts;

import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.BMethod;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.peer.RSObjectDefinition;

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
    public String name() {
        return name;
    }

}
