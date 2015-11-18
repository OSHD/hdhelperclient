package com.hdhelper.injector.bs.scripts.collection;

import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;

@ByteScript(name = "DualNode")
public class DualNode extends Node {

    @BField DualNode dualNext;
    @BField DualNode dualPrev;

}
