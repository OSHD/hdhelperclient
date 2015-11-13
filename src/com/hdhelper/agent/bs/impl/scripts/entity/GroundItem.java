package com.hdhelper.agent.bs.impl.scripts.entity;

import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.agent.peer.RSGroundItem;

@ByteScript(name = "GroundItem")
public class GroundItem extends Entity implements RSGroundItem {

    @BField int id;
    @BField int quantity;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }
}
