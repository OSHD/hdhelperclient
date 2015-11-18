package com.hdhelper.agent.services;

public interface RSImage {
    int[] getPixels();
    int getWidth();
    int getHeight();
    int getInsetX();
    int getInsetY();
    int getMaxX();
    int getMaxY();
}
