package com.hdhelper.agent.services;

public interface RSLandscape extends RSService {

    RSLandscapeTile[][][] getTiles();
    RSEntityMarker[] getTempEntities();
    boolean[][][][] getVisibilityMap();

    RSBoundary getBoundaryAt(int floor, int rx, int ry);
    RSBoundaryDecoration getBoundaryDecorationAt(int floor, int rx, int ry);
    RSTileDecoration getTileDecorationAt(int floor, int rx, int ry);
    RSEntityMarker getObjectAt(int floor, int rx, int ry);

    int getBoundaryUID(int floor, int rx, int ry);
    int getBoundaryDecorationUID(int floor, int rx, int ry);
    int getTileDecorationUID(int floor, int rx, int ry);
    int getObjectUID(int floor, int rx, int ry);

    int getConfigForUID(int floor, int rx, int ry, int UID);

}
