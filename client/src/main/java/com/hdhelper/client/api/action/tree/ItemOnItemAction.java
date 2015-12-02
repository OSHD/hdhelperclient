package com.hdhelper.client.api.action.tree;

import com.hdhelper.client.api.action.ActionTypes;

public class ItemOnItemAction extends AbstractTableAction {

    public ItemOnItemAction(int item_id, int item_index, int table_id) {
        super(ActionTypes.ITEM_ON_ITEM, item_id, item_index, table_id);
    }

    public static boolean isInstance(int opcode) {
        return Action.pruneOpcode(opcode) == ActionTypes.ITEM_ON_ITEM;
    }

}
