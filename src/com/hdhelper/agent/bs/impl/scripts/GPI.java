package com.hdhelper.agent.bs.impl.scripts;

import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;

@ByteScript(name = "GPI")
public class GPI {

    @BField
    public static int[] playerIndices;
    @BField
    public static int playerCount;

}
