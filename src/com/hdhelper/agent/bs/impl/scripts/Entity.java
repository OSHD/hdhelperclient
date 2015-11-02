package com.hdhelper.agent.bs.impl.scripts;

import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.peer.RSEntity;

@ByteScript(name = "Entity")
public abstract class Entity extends DualNode implements RSEntity {

    @BField int modelHeight;


    @Override
    public int getHeight() {
        return modelHeight;
    }

}
