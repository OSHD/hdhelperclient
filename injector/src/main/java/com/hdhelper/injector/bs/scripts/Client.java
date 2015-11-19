package com.hdhelper.injector.bs.scripts;

import com.bytescript.lang.BField;
import com.bytescript.lang.BMethod;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.CNIRuntimeArgs;
import com.hdhelper.agent.CNIVerifyException;
import com.hdhelper.agent.CanvasFactory;
import com.hdhelper.agent.ClientCanvas;
import com.hdhelper.agent.bridge.RenderSwitch;
import com.hdhelper.agent.services.*;
import com.hdhelper.injector.Piston;
import com.hdhelper.injector.bs.scripts.cache.ItemDefinition;
import com.hdhelper.injector.bs.scripts.cache.NpcDefinition;
import com.hdhelper.injector.bs.scripts.cache.ObjectDefinition;
import com.hdhelper.injector.bs.scripts.collection.Deque;
import com.hdhelper.injector.bs.scripts.collection.NodeTable;
import com.hdhelper.injector.bs.scripts.entity.Npc;
import com.hdhelper.injector.bs.scripts.entity.Player;
import com.hdhelper.injector.bs.scripts.graphics.Image;
import com.hdhelper.injector.bs.scripts.ls.Landscape;

import java.awt.*;
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
    @BField
    public static Canvas canvas;




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

    @BMethod(name = "getRuneScript")
    public static RuneScript getRuneScript0(int id) {
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

    @Piston
    @Override
    public RSRuneScript getRuneScript(int id) {
        return getRuneScript0(id);
    }


    public static ClientCanvas getCanvas() {
        return (ClientCanvas) canvas;
    }



    @Override
    public void start() {
        if(!booted)
            throw new RuntimeException("CNI has not been initialized");
        super.start();
    }


    //CNI Bridge
    private static boolean booted = false; // True if the CNI interfaces were successfully established
    public static RenderSwitch render_switch;
    public static CanvasFactory canvas_factory;
    //-----------------------------------------------

    public static void initCNI(CNIRuntimeArgs args) {
        render_switch = args.ren_switch;
        canvas_factory = args.canvasFactory;
        //--------------------------------------
        booted = verify();
    }

    // Verify CNI OK to initialize
    private static boolean verify() {
        requireNonNull(render_switch,"render switch must be non-null");
        requireNonNull(canvas_factory,"canvas factory must be non-null");
        return true;
    }

    private static void requireNonNull(Object o, String msg) {
        if(o == null)
            throw new CNIVerifyException(msg);
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
