package com.hdhelper.peer;

public interface RSLandscapeTile {

    RSBoundaryDecoration getBoundaryDecoration();
    RSBoundary getBoundary();
    RSEntityMarker[] getMarkers();
    RSTileDecoration getTileDecoration();
    RSItemPile getItemPile();

    int getRegionX();
    int getRegionY();
    int getFloor();

}
