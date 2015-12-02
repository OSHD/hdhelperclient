package com.hdhelper.client.api;

import com.hdhelper.agent.services.RSWidget;

public class TableItem extends Item {

    protected final RSWidget table;
    protected final int slot;

    public TableItem(RSWidget table, int slot) {
        this.table = table;
        this.slot  = slot;
    }

    public RSWidget getTable() {
        return table;
    }

    public int getSlot() {
        return slot;
    }

}
