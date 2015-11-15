package com.hdhelper.agent.bs.impl.scripts;

import com.hdhelper.agent.bs.impl.scripts.graphics.Image;
import com.hdhelper.agent.bs.impl.scripts.cache.ItemDefinition;
import com.hdhelper.agent.bs.impl.scripts.cache.NpcDefinition;
import com.hdhelper.agent.bs.impl.scripts.cache.ObjectDefinition;
import com.hdhelper.agent.bs.impl.scripts.collection.Deque;
import com.hdhelper.agent.bs.impl.scripts.collection.NodeTable;
import com.hdhelper.agent.bs.impl.scripts.entity.Npc;
import com.hdhelper.agent.bs.impl.scripts.entity.Player;
import com.hdhelper.agent.bs.impl.scripts.ls.Landscape;
import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.BMethod;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.agent.Piston;
import com.hdhelper.agent.peer.*;

import java.io.File;

@ByteScript(name = "Client")
public class Client extends GameEngine implements RSClient {

    @BField
    public static int regionBaseX;
    @BField
    public static int regionBaseY;
    @BField
    public static Player myPlayer;
    @BField(name = "playerArray")
    public static Player[] players;
    @BField(name = "npcArray")
    public static Npc[] npcs;
    @BField
    public static int[][][] tileHeights;
    @BField
    public static byte[][][] renderRules;
    @BField
    public static int cameraPitch;
    @BField
    public static int cameraYaw;
    @BField
    public static int cameraX;
    @BField
    public static int cameraY;
    @BField
    public static int cameraZ;
    @BField
    public static int screenScale;
    @BField
    public static int screenWidth;
    @BField
    public static int screenHeight;
    @BField
    public static int floorLevel;
    @BField
    public static Deque[][][] groundItemDeque;
    @BField
    public static File cacheDirectory;
    @BField
    public static NodeTable itemTables;
    @BField
    public static int[] chunkIds;
    @BField
    public static int[][] XTEAKeys;
    @BField
    public static Landscape landscape;
    @BField
    public static int fps;
    @BField
    public static int engineCycle;
    @BField
    public static int connectionState;




    @BMethod(name = "getObjectDefinition")
    public static ObjectDefinition getObjectDefinition0(int id) {
        return null;
    }

    @BMethod(name = "getNpcDefinition")
    public static NpcDefinition getNpcDefinition0(int id) {
        return null;
    }

    @BMethod(name = "getItemDefinition")
    public static ItemDefinition getItemDefinition0(int id) {
        return null;
    }

    @BMethod(name = "getItemSprite")
    public static Image getItemImage0(int id, int quantity, int borderThickness, int backgroundColor, int num, boolean noted) {
        return null;
    }








    @Override
    public RSPlayer getMyPlayer() {
        return myPlayer;
    }

    @Override
    public RSPlayer[] getPlayers() {
        return players;
    }

    @Override
    public RSNpc[] getNpcs() {
        return npcs;
    }

    @Override
    public RSNodeTable getItemContainers() {
        return itemTables;
    }

    public int getRegionBaseX() {
        return regionBaseX;
    }

    @Override
    public int getRegionBaseY() {
        return regionBaseY;
    }

    @Override
    public int[][][] getTileHeights() {
        return tileHeights;
    }

    @Override
    public byte[][][] getRenderRules() {
        return renderRules;
    }

    @Override
    public int getPitch() {
        return cameraPitch;
    }

    @Override
    public int getYaw() {
        return cameraYaw;
    }

    @Override
    public int getCameraX() {
        return cameraX;
    }

    @Override
    public int getCameraY() {
        return cameraY;
    }

    @Override
    public int getCameraZ() {
        return cameraZ;
    }

    @Override
    public int getViewportScale() {
        return screenScale;
    }

    @Override
    public int getViewportWidth() {
        return screenWidth;
    }

    @Override
    public int getViewportHeight() {
        return screenHeight;
    }

    @Override
    public int getFloor() {
        return floorLevel;
    }

    @Override
    public RSDeque[][][] getGroundItems() {
        return groundItemDeque;
    }

    @Override
    public int[] getChunkIds() {
        return chunkIds;
    }

    @Override
    public int[][] getKeys() {
        return XTEAKeys;
    }


    @Override
    public File getCacheDirectory() {
        return cacheDirectory;
    }

    @Override
    public RSLandscape getLandscape() {
        return landscape;
    }


    @Override
    public int[] getPlayerIndices() {
        return GPI.playerIndices;
    }

    @Override
    public int getPlayerCount() {
        return GPI.playerCount;
    }

    @Override
    public int getFps() {
        return fps;
    }

    @Override
    public int getEngineCycle() {
        return engineCycle;
    }

    @Override
    public int getConnectionState() { return connectionState; }


    // Methods //////////////////////////////////////////////////////////////////////


    @Piston
    @Override
    public RSItemDefinition getItemDef(int id) {
        if(id < 0 || id >= Short.MAX_VALUE) return null; // Cache the total items within the cache
        return getItemDefinition0(id);
    }

    @Piston
    @Override
    public RSObjectDefinition getObjectDef(int id) {
        if(id < 0 || id >= Short.MAX_VALUE) return null;
        return getObjectDefinition0(id);
    }

    @Piston
    @Override
    public RSNpcDefinition getNpcDef(int id) {
        if(id < 0 || id >= Short.MAX_VALUE) return null;
        return getNpcDefinition0(id);
    }

    @Piston
    @Override
    public RSImage getItemImage(int id, int quantity, int borderThickness, int shadowColor, int num, boolean noted) {
        return getItemImage0(id, quantity, borderThickness, shadowColor, num, noted);
    }




    // For methods that must be synchronized with the game engine. Since establishing
    // multi-thread support would be overly complex and/or degrade performance considerably.
    public static void verifyCaller() {
        if(!inEngineThread()) {
            StackTraceElement e = new Exception().getStackTrace()[1];
            String caller_method = e.getMethodName();
            throw new Error(caller_method + "() should be called within the engine thread.");
        }
    }

    public static boolean inEngineThread() {
        return Thread.currentThread() == engine_thread;
    }

    public static Thread engine_thread;
    public static void onBoot() {
        engine_thread = Thread.currentThread();
    }

}
