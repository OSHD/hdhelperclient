package com.hdhelper.agent.bs.impl.scripts.collection;

import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;

@ByteScript(name = "DualNode")
public class DualNode extends Node {

    @BField DualNode dualNext;
    @BField DualNode dualPrev;

}
