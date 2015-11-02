package com.hdhelper.agent.bs.impl.scripts.util;

import com.hdhelper.agent.bs.impl.scripts.collection.Node;
import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.peer.RSItemTable;

@ByteScript(name = "ItemTable")
public class ItemTable extends Node implements RSItemTable {

    @BField int[] ids;
    @BField int[] quantities;

    @Override
    public int[] getIds() {
        return ids;
    }

    @Override
    public int[] getQuantities() {
        return quantities;
    }

}
