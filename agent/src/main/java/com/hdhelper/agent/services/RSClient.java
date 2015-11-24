package com.hdhelper.agent.services;

import com.hdhelper.agent.event.ActionListener;
import com.hdhelper.agent.event.MessageListener;
import com.hdhelper.agent.event.SkillListener;
import com.hdhelper.agent.event.VariableListener;

import java.io.File;

public interface RSClient {

    RSPlayer getMyPlayer();

    RSPlayer[] getPlayers();
    RSNpc[] getNpcs();

    RSItemDefinition getItemDef(int id);
    RSObjectDefinition getObjectDef(int id);
    RSNpcDefinition getNpcDef(int id);
    RSImage getItemImage(int id, int quantity, int borderThickness, int shadowColor, int num, boolean noted);
    RSRuneScript getRuneScript(int id);

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
    int getBootState();

    RSWidget[][] getWidgets();

    int getMinimapScale();
    int getMinimapRotation();
    int getViewRotation();

    void addMessageListener(MessageListener listener);
    void removeMessageListener(MessageListener listener);

    void addActionListener(ActionListener listener);
    void removeActionListener(ActionListener listener);

    void addVariableListener(VariableListener listener);
    void removeVariableListener(VariableListener listener);

    void addSkillListener(SkillListener listener);
    void removeSkillListener(SkillListener listener);

}
