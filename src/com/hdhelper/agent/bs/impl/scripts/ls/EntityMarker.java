package com.hdhelper.agent.bs.impl.scripts.ls;

import com.hdhelper.agent.bs.impl.scripts.entity.Entity;
import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.peer.RSEntity;
import com.hdhelper.peer.RSEntityMarker;

@ByteScript(name = "EntityMarker")
public class EntityMarker implements RSEntityMarker {

    @BField Entity entity;

    @BField int regionX;
    @BField int regionY;
    @BField int floorLevel;

    @BField int maxX;
    @BField int maxY;

    @BField int strictX;
    @BField int strictY;
    @BField int height;

    @BField int config;
    @BField int uid;
    @BField int orientation;



    @Override
    public RSEntity getEntity() {
        return entity;
    }

    @Override
    public int getUid() {
        return uid;
    }

    @Override
    public int getConfig() {
        return config;
    }

    @Override
    public int getOrientation() {
        return orientation;
    }

    @Override
    public int getRootRegionX() {
        return regionX;
    }

    @Override
    public int getRootRegionY() {
        return regionY;
    }

    @Override
    public int getFloorLevel() {
        return floorLevel;
    }

    @Override
    public int getStrictX() {
        return strictX;
    }

    @Override
    public int getStrictY() {
        return strictY;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getMaxX() {
        return maxX;
    }

    @Override
    public int getMaxY() {
        return maxY;
    }
}
