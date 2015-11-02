package com.hdhelper.agent.bs.impl.scripts.ls;

import com.hdhelper.agent.bs.impl.scripts.entity.Entity;
import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.peer.RSBoundary;
import com.hdhelper.peer.RSEntity;

@ByteScript(name = "BoundaryStub")
public class Boundary implements RSBoundary {

    @BField Entity entityA;
    @BField int orientationA;

    @BField Entity entityB;
    @BField int orientationB;

    @BField int strictX;
    @BField int strictY;
    @BField int height;

    @BField int config;
    @BField int uid;




    @Override
    public RSEntity getEntityA() {
        return entityA;
    }

    @Override
    public RSEntity getEntityB() {
        return entityB;
    }

    @Override
    public int getOrientationA() {
        return orientationA;
    }

    @Override
    public int getOrientationB() {
        return orientationB;
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
