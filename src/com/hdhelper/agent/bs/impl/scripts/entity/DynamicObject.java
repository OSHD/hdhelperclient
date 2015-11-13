package com.hdhelper.agent.bs.impl.scripts.entity;

import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.agent.peer.RSDynamicObject;

@ByteScript(name = "DynamicObject")
public class DynamicObject extends Entity implements RSDynamicObject {

    @BField int id;
    @BField int floorLevel;
    @BField int rotation;
    @BField int regionX;
    @BField int regionY;
    @BField int type;


    @Override
    public int getRegionX() {
        return regionX;
    }

    @Override
    public int getRegionY() {
        return regionY;
    }

    @Override
    public int getFloor() {
        return floorLevel;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public int getRotation() {
        return rotation;
    }

}
