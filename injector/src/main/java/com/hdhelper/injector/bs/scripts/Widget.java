package com.hdhelper.injector.bs.scripts;

import com.bytescript.lang.BField;
import com.bytescript.lang.BMethod;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSImage;
import com.hdhelper.agent.services.RSWidget;
import com.hdhelper.injector.bs.scripts.collection.Node;
import com.hdhelper.injector.bs.scripts.graphics.Image;

@ByteScript(name = "Widget")
public class Widget extends Node implements RSWidget {

    @BField int id;
    @BField int type;
    @BField int contentType;
    @BField Widget[] children;
    @BField String text;
    @BField int fontId;
    @BField int spriteId;
    @BField int[] spriteIds;
    @BField int relativeX;
    @BField int relativeY;
    @BField int width;
    @BField int height;

    @BMethod(name = "getImage")
    public Image getImage0(int index, int pred) {
        return null;
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
    public int getContentType() {
        return contentType;
    }

    @Override
    public RSWidget[] getChildren() {
        return children;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int getFontId() {
        return fontId;
    }

    @Override
    public int getSpriteId() {
        return spriteId;
    }

    @Override
    public RSImage getImage(int index) {
        return getImage0(index,1);
    }

    public int[] getSpriteIds() {
        return spriteIds;
    }

    @Override
    public int getX() {
        return relativeX;
    }

    @Override
    public int getY() {
        return relativeY;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
