package com.hdhelper;

import java.io.File;

public class Environment {

    public static final File ROOT = new File(System.getProperty("user.home"),"hdhelper");

    // ROOT/BIN/...
    public static final File BIN     = new File(ROOT,"bin");
    public static final File CLIENT  = new File(BIN,"client.jar");
    public static final File INJECTED  = new File(BIN,"injected.jar");
    public static final File VERSION = new File(BIN,"version");

    public static final int WORLD = 2;


}
