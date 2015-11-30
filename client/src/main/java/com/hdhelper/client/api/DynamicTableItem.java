package com.hdhelper.client.api;

import com.hdhelper.agent.services.RSWidget;

public final class DynamicTableItem extends TableItem {

    public DynamicTableItem(RSWidget table, int slot) {
        super(table, slot);
    }

    @Override
    public int getId() {
        return table.getItemIds()[slot];
    }

    @Override
    public int getQuantity() {
        return table.getItemQuantities()[slot];
    }

}