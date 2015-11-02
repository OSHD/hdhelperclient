package com.hdhelper.agent.bs.impl.scripts.ls;

import com.hdhelper.agent.bs.impl.scripts.entity.Entity;
import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.peer.RSEntity;
import com.hdhelper.peer.RSTileDecoration;

@ByteScript(name = "TileDecorationStub")
public class TileDecoration implements RSTileDecoration {

    @BField Entity entity;

    @BField int strictX;
    @BField int strictY;
    @BField int height;

    @BField int config;
    @BField int uid;




    @Override
    public RSEntity getEntity() {
        return entity;
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
    public int getConfig() {
        return config;
    }

    @Override
    public int getUid() {
        return uid;
    }

}
