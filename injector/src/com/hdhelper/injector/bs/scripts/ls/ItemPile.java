package com.hdhelper.injector.bs.scripts.ls;

import com.hdhelper.agent.bs.scripts.entity.Entity;
import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSEntity;
import com.hdhelper.agent.services.RSItemPile;

@ByteScript(name = "ItemPile")
public class ItemPile implements RSItemPile {

    @BField int counterHeight;
    @BField int height;

    @BField int strictX;
    @BField int strictY;

    @BField int uid;

    @BField Entity top;
    @BField Entity middle;
    @BField Entity bottom;




    @Override
    public int getUid() {
        return uid;
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
    public int getCounterHeight() {
        return counterHeight;
    }

    @Override
    public RSEntity getTop() {
        return top;
    }

    @Override
    public RSEntity getMiddle() {
        return middle;
    }

    @Override
    public RSEntity getBottom() {
        return bottom;
    }
}
