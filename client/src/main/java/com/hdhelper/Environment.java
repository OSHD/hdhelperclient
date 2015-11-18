package com.hdhelper;

import java.io.File;

public class Environment {

    public static final File ROOT = new File(System.getProperty("user.home"),"hdhelper");

    // ROOT/BIN/...
    public static final File BIN     = new File(ROOT,"bin");
    public static final File CLIENT  = new File(BIN,"client.jar");
    public static final File INJECTED  = new File(BIN,"injected.jar");
    public static final File VERSION = new File(BIN,"version");

    // ROOT/DATA
    public static final File DATA  = new File(ROOT,"data");
    public static final File XTEAS = new File(DATA,"xtea");

    public static final int WORLD = 2;


    public static int CLIENT_REVISION = 98;

    public static boolean RENDER_LANDSCAPE = true;

    public static boolean RENDER_NPC_DEBUG;
    public static boolean RENDER_PLAYER_DEBUG;
    public static boolean RENDER_GROUND_ITEM_DEBUG;
    public static boolean RENDER_BOUNDARY_DEBUG;
    public static boolean RENDER_BOUNDARY_DECO_DEBUG;
    public static boolean RENDER_TILE_DECO_DEBUG;
    public static boolean RENDER_OBJECT_DEBUG;
    public static boolean RENDER_MISC_DEBUG;

}
