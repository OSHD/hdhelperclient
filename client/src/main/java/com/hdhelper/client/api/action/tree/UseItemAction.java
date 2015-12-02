package com.hdhelper.client.api.action.tree;


import com.hdhelper.client.api.action.ActionTypes;

public class UseItemAction extends AbstractTableAction {

    public UseItemAction(int itemId, int tableIndex, int tableId) {
        super(ActionTypes.USE_ITEM,itemId, tableIndex, tableId);
    }

    public static boolean isInstance(int opcode) {
        return Action.pruneOpcode(opcode) == ActionTypes.USE_ITEM;
    }

}
