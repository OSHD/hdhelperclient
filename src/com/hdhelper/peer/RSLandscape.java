package com.hdhelper.peer;

public interface RSLandscape {
    RSLandscapeTile[][][] getTiles();
    RSEntityMarker[] getTempEntities();
    boolean[][][][] getVisibilityMap();
}
