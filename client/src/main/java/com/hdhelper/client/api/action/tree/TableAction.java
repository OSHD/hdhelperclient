package com.hdhelper.client.api.action.tree;

import com.hdhelper.client.api.action.ActionTypes;

public class TableAction extends AbstractTableAction {

    public TableAction(int opcode, int item_id, int item_index, int containerUID) {
        super(opcode, item_id, item_index, containerUID);
    }

    public static int getOpcodeForActIndex(int index) {
        if(index < 0 || index > 4) return -1;
        return ActionTypes.TABLE_ACTION_0 + index;
    }

    public int getActionIndex() {
        return opcode - ActionTypes.TABLE_ACTION_0;
    }

    public static boolean isInstance(int opcode) {
        opcode = Action.pruneOpcode(opcode);
        return opcode >= ActionTypes.TABLE_ACTION_0
            && opcode <= ActionTypes.TABLE_ACTION_4;
    }
}
