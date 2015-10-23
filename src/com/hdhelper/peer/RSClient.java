package com.hdhelper.peer;

public interface RSClient {

    RSPlayer getMyPlayer();

    RSPlayer[] getPlayers();
    RSNpc[] getNpcs();

    RSItemDefinition getItemDef(int id);
    RSObjectDefintion getObjectDef(int id);

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

}
