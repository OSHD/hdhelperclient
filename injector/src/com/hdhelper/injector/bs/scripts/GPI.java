package com.hdhelper.injector.bs.scripts;

import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;

@ByteScript(name = "GPI")
public class GPI {

    @BField
    public static int[] playerIndices;
    @BField
    public static int playerCount;

}
