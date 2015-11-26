package com.hdhelper.agent.services;

public interface RSWidget {
    int getId();
    int getType();
    int getContentType();
    RSWidget[] getChildren();
    String getText();
    int getFontId();
    int getSpriteId();

    int getX();
    int getY();
    int getWidth();
    int getHeight();

}
