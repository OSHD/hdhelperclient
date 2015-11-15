package com.hdhelper.agent.peer;

import java.io.File;

public interface RSClient {

    RSPlayer getMyPlayer();

    RSPlayer[] getPlayers();
    RSNpc[] getNpcs();

    RSItemDefinition getItemDef(int id);
    RSObjectDefinition getObjectDef(int id);
    RSNpcDefinition getNpcDef(int id);
    RSImage getItemImage(int id, int quantity, int borderThickness, int shadowColor, int num, boolean noted);

    RSNodeTable getItemContainers();

    int getRegionBaseX();
    int getRegionBaseY();

    int[][][] getTileHeights();
    byte[][][] getRenderRules();

    int getPitch();
    int getYaw();
    int getCameraX();
    int getCameraY();
    int getCameraZ();

    int getViewportScale();
    int getViewportWidth();
    int getViewportHeight();

    int getFloor();

    RSDeque[][][] getGroundItems();

    int[] getChunkIds();
    int[][] getKeys();
    
    File getCacheDirectory();

    RSLandscape getLandscape();

    //GPI:
    int[] getPlayerIndices();
    int getPlayerCount();

    int getFps();

    int getEngineCycle();
    int getConnectionState();
}
