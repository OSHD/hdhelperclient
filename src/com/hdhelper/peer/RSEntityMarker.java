package com.hdhelper.peer;

public interface RSEntityMarker {

    RSEntity getEntity();

    int getUid();
    int getConfig();
    int getOrientation();

    int getRootRegionX();
    int getRootRegionY();
    int getFloorLevel();

    int getStrictX();
    int getStrictY();
    int getHeight();

    int getMaxX();
    int getMaxY();

}
