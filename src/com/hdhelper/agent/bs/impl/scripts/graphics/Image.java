package com.hdhelper.agent.bs.impl.scripts.graphics;

import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.peer.RSImage;

@ByteScript(name = "Sprite")
public class Image extends Graphics implements RSImage {

    @BField public int[] pixels;
    @BField public int width;
    @BField public int height;
    @BField public int insetX;
    @BField public int insetY;
    @BField public int maxX;
    @BField public int maxY;



    @Override
    public int[] getPixels() {
        return pixels;
    }

    @Override
    public int getWidth() {
        return width;
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
    public int getMaxX() {
        return maxX;
    }

    @Override
    public int getMaxY() {
        return maxY;
    }

}
