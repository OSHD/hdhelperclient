package com.hdhelper.agent.bs.impl.scripts.entity;

import com.hdhelper.agent.bs.impl.scripts.collection.DualNode;
import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.agent.peer.RSEntity;

@ByteScript(name = "Entity")
public abstract class Entity extends DualNode implements RSEntity {

    @BField int modelHeight;


    @Override
    public int getHeight() {
        return modelHeight;
    }

}
