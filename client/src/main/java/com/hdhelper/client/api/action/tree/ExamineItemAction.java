package com.hdhelper.client.api.action.tree;

import com.hdhelper.client.api.action.ActionTypes;

public class ExamineItemAction extends AbstractTableAction {

    public ExamineItemAction(int item_id, int item_index, int table_id) {
        super(ActionTypes.EXAMINE_ITEM, item_id, item_index, table_id );
    }

    public static boolean isInstance(int opcode) {
        return Action.pruneOpcode(opcode) == ActionTypes.EXAMINE_ITEM;
    }

}
