package com.hdhelper.injector.bs.scripts.util;

import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSItemTable;
import com.hdhelper.injector.bs.scripts.collection.Node;

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
