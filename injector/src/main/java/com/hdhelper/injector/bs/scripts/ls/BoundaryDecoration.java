package com.hdhelper.injector.bs.scripts.ls;

import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSBoundaryDecoration;
import com.hdhelper.agent.services.RSEntity;
import com.hdhelper.injector.bs.scripts.entity.Entity;

@ByteScript(name = "BoundaryDecorationStub")
public class BoundaryDecoration implements RSBoundaryDecoration {

    @BField Entity entityA;
    @BField int orientationA;

    @BField Entity entityB;
    @BField int orientationB;

    @BField int strictX;
    @BField int strictY;
    @BField int height;

    @BField int insetX; //Strict Units
    @BField int insetY; //Strict Units

    @BField int config;
    @BField int uid;





    @Override
    public RSEntity getEntityA() {
        return entityA;
    }

    @Override
    public int getOrientationA() {
        return orientationA;
    }

    @Override
    public RSEntity getEntityB() {
        return entityB;
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
    public int getInsetX() {
        return insetX;
    }

    @Override
    public int getInsetY() {
        return insetY;
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
