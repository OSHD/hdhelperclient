package com.hdhelper.injector.bs.scripts.ls;

import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSEntity;
import com.hdhelper.agent.services.RSTileDecoration;
import com.hdhelper.injector.bs.scripts.entity.Entity;

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
