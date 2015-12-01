package com.hdhelper.injector.bs.scripts;

import com.bytescript.lang.BField;
import com.bytescript.lang.BMethod;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.*;
import com.hdhelper.agent.bus.ActionBus;
import com.hdhelper.agent.bus.MessageBus;
import com.hdhelper.agent.bus.SkillBus;
import com.hdhelper.agent.bus.VariableBus;
import com.hdhelper.agent.bus.access.ActionBusAccess;
import com.hdhelper.agent.bus.access.MessageBusAccess;
import com.hdhelper.agent.bus.access.SkillBusAccess;
import com.hdhelper.agent.bus.access.VariableBusAccess;
import com.hdhelper.agent.event.ActionListener;
import com.hdhelper.agent.event.MessageListener;
import com.hdhelper.agent.event.SkillListener;
import com.hdhelper.agent.event.VariableListener;
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
    @BField(name = "interfaces")
    public static Widget[][] widgets;
    @BField
    public static int bootState;
    @BField
    public static int minimapScale;
    @BField
    public static int minimapRotation;
    @BField
    public static int viewRotation;
    @BField
    public static int[] currentLevels;
    @BField
    public static int[] levels;
    @BField
    public static boolean menuOpen;
    @BField
    public static int menuItemCount;
    @BField
    public static int[] menuOpcodes;
    @BField
    public static int[] menuArg0;
    @BField
    public static int[] menuArg1;
    @BField
    public static int[] menuArg2;
    @BField
    public static String[] menuNouns;
    @BField
    public static String[] menuActions;
    @BField
    public static int[] tempVars;
    @BField
    public static NodeTable varpbitTable;



    // Methods

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

    @BMethod(name = "runScript")
    public static void runScript0(ScriptEvent e) {
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    @Override
    public RSWidget[][] getWidgets() {
        return widgets;
    }

    @Override
    public int getBootState() {
        return bootState;
    }

    @Override
    public int getMinimapScale() {
        return minimapScale;
    }

    @Override
    public int getMinimapRotation() {
        return minimapRotation;
    }

    @Override
    public int getViewRotation() {
        return viewRotation;
    }

    @Override
    public int[] getCurrentLevels() {
        return currentLevels;
    }
    @Override
    public int[] getLevels() {
        return levels;
    }

    @Override
    public boolean isMenuOpen() {
        return menuOpen;
    }

    @Override
    public int getMenuCount() {
        return menuItemCount;
    }

    @Override
    public int[] getMenuOpcodes() {
        return menuOpcodes;
    }

    @Override
    public int[] getMenuArg0s() {
        return menuArg0;
    }

    @Override
    public int[] getMenuArg1s() {
        return menuArg1;
    }

    @Override
    public int[] getMenuArg2s() {
        return menuArg2;
    }

    @Override
    public String[] getMenuOptions() {
        return menuNouns;
    }

    @Override
    public String[] getMenuActions() {
        return menuActions;
    }

    @Override
    public int[] getConfig() {
        return tempVars;
    }

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

    @Piston
    @Override
    public RSVarpbit getVarpbit(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void runScript(Object[] args) {
        ScriptEvent e = new ScriptEvent();
        e.args = args;
        runScript0(e);
    }



    // Listener Functions:

    @Override
    public void addMessageListener(MessageListener l) {
        if(msgBus == null)
            msgBus = msgBusAccess.mkMessageBus(this);
        msgBus.addMessageListener(l);
    }

    @Override
    public void removeMessageListener(MessageListener l) {
        if(msgBus == null) return; // No listeners exist
        msgBus.removeMessageListener(l);
    }

    //---------------------------------------------------------

    @Override
    public void addActionListener(ActionListener l) {
        if(actBus == null)
            actBus = actBusAccess.mkActionBus(this);
        actBus.addActionListener(l);
    }

    @Override
    public void removeActionListener(ActionListener l) {
        if(actBus == null) return;
        actBus.removeMessageListener(l);
    }

    //---------------------------------------------------------

    @Override
    public void addVariableListener(VariableListener l) {
        if(varBus == null)
            varBus = varBusAccess.mkVariableBus(this);
        varBus.addListener(l);
    }

    @Override
    public void removeVariableListener(VariableListener l) {
        if(varBus == null) return;
        varBus.removeListener(l);
    }

    //---------------------------------------------------------

    @Override
    public void addSkillListener(SkillListener l) {
        if(skillBus == null)
            skillBus = skillBusAccess.mkSkillBus(this);
        skillBus.addListener(l);
    }

    @Override
    public void removeSkillListener(SkillListener l) {
        if(skillBus == null) return;
        skillBus.removeListener(l);
    }

    //---------------------------------------------------------


    ////////////////////////////////////////////////////////////////////////////////////////////


    public static ClientCanvas getCanvas() {
        return (ClientCanvas) canvas;
    }



    @Override
    public void start() {
        if(!booted)
            throw new RuntimeException("CNI has not been initialized");
        super.start();
    }



    ////////////////////////////////////////////////////////////////////////////////////////////

    public static String curFont;
    public static void captureGlyphVector(byte[] meta,
                                          int[] xOffsets,int[] yOffsets,
                                          int[] widths,int[] heights,
                                          int[] colorMap,byte[][] bitmap) {
        // 1. Load the font  -> Grab the name
        // 2. Init the font  -> Grab the args
        if(curFont == null) return; //Unknown capture
        GlyphCapture capture = glyphCaptureFactory.getCapture(curFont);
        if(capture == null)
            throw new RuntimeException(curFont + " capture == null");
        capture.capture(meta, xOffsets, yOffsets, widths, heights, colorMap, bitmap);
        curFont = null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    // Injection helper functions:

    // Proxies the IASTORE operation (for the settings array) to this method
    /** @see com.hdhelper.injector.mod.VarChangeMod **/
    public static void setVar(int[] settings, int index, int value) {
        varChanged(index, settings[index], value);
        settings[index] = value; // Actually set it now
    }

    public static void setRealSkillLvl(int[] skills, int skill, int value) {
        realSkillLvlChanged(skill, skills[skill], value);
        skills[skill] = value;
    }

    public static void setTempSkillLvl(int[] skills, int skill, int value) {
        tempSkillLevelChanged(skill, skills[skill], value);
        skills[skill] = value;
    }

    public static void setExp(int[] exps, int skill, int value) {
        expChanged(skill, exps[skill], value);
        exps[skill] = value;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    // Bus/Event functions: Called throughout the client:

    /** @see com.hdhelper.injector.mod.MessageMod **/
    public static void messageReceived(Message msg) {
        if(msgBus == null) return; // We're not interested
        msgBusAccess.dispatchMessageEvent(msgBus, msg);
    }

    /** @see com.hdhelper.injector.mod.ActionMod **/
    public static void actionPerformed(BasicAction ba) {
        if(actBus == null) return;
        actBusAccess.dispatchActionEvent(actBus, ba);
    }

    /** @see #setVar(int[], int, int) **/
    public static void varChanged(int var, int old, int now) {
        if(varBus == null) return;
        if(old == now) return; // Guaranteed change
        varBusAccess.dispatchVarEvent(varBus, var, old, now);
    }

    /** @see #setRealSkillLvl(int[], int, int) **/
    public static void realSkillLvlChanged(int skill, int old, int now) {
        if(skillBus == null) return;
        if(old == now) return;
        skillBusAccess.dispatchRealLevelChangeEvent(skillBus, skill, old, now);
    }

    /** @see #setTempSkillLvl(int[], int, int) **/
    public static void tempSkillLevelChanged(int skill, int old, int now) {
        if(skillBus == null) return;
        if(old == now) return;
        skillBusAccess.dispatchTempLevelChangeEvent(skillBus, skill, old, now);
    }

    /** @see #setExp(int[], int, int) **/
    public static void expChanged(int skill, int old, int now) {
        if(skillBus == null) return;
        if(old == now) return;
        skillBusAccess.dispatchExpChangeEvent(skillBus, skill, old, now);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    //CNI Bridge
    private static boolean booted = false; // True if the CNI interfaces were successfully established
    public static CNI cni;
    public static RenderSwitch render_switch;
    public static CanvasFactory canvas_factory;
    public static GlyphCaptureFactory glyphCaptureFactory;
    //-----------------------------------------------
    //Static/Global Buses
    private static MessageBus msgBus;
    private static ActionBus actBus;
    private static VariableBus varBus;
    private static SkillBus skillBus;

    //Bus Accessors
    private static final MessageBusAccess msgBusAccess
            = SharedAgentSecrets.getMessageBusAccess();
    private static final ActionBusAccess actBusAccess
            = SharedAgentSecrets.getActionBusAccess();
    private static final VariableBusAccess varBusAccess
            = SharedAgentSecrets.getVariableBusAccess();
    private static final SkillBusAccess skillBusAccess
            = SharedAgentSecrets.getSkillBusAccess();





    /** @see CNI#initCNI(Class, CNI, CNIRuntimeArgs) **/
    public static void initCNI(CNI cni_, CNIRuntimeArgs args) {
        cni = cni_;
        render_switch = args.ren_switch;
        canvas_factory = args.canvasFactory;
        glyphCaptureFactory = args.glyphCaptureFactory;
        //--------------------------------------
        booted = verify();
    }

    // Verify CNI OK to initialize
    private static boolean verify() {
        requireNonNull(cni,"cni must be non-null");
        requireNonNull(render_switch,"render switch must be non-null");
        requireNonNull(canvas_factory,"canvas factory must be non-null");
        requireNonNull(glyphCaptureFactory,"glyph capture factory must be non-null");
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
    private static final Object engine_start_lock = new Object();
    /** Notifies us when the GameEngine Thread has been started.
     *  Called as the first very frst statement executed within
     *  the engines thread; before any other code is called.
     * @see com.hdhelper.injector.mod.EngineMod;
     */
    public static void engineStarted() {
        engine_thread = Thread.currentThread(); //Capture the engines thread
        engine_thread.setName("GameEngine");
        synchronized (engine_start_lock) {
            engine_start_lock.notifyAll();
        }
    }
    // Allows us to wait for the engine to start.
    public static void awaitEngineStart(long ms) throws InterruptedException {
        synchronized (engine_start_lock) {
            engine_start_lock.wait(ms);
        }
    }

}
