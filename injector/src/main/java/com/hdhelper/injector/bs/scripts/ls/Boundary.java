package com.hdhelper.injector.bs.scripts.ls;

import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSBoundary;
import com.hdhelper.agent.services.RSEntity;
import com.hdhelper.injector.bs.scripts.entity.Entity;

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
