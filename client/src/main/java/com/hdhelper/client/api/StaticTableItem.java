package com.hdhelper.client.api;

import com.hdhelper.agent.services.RSWidget;

public final class StaticTableItem extends TableItem {

    public StaticTableItem(RSWidget table, int slot) {
        this(table,slot,
                table.getItemIds()[slot],
                table.getItemQuantities()[slot]
        );
    }

    public StaticTableItem(RSWidget table, int slot, int id, int q) {
        super(table, slot);
        //Take a snapshot now
        super.id = id;
        super.q = q;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getQuantity() {
        return q;
    }

}