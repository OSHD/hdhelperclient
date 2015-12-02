package com.hdhelper.client.api.action.tree;

import com.hdhelper.client.api.action.ActionTypes;

// We can extend out respected action type since we are not a entity, and allow for more simplicity
public class SpellOnItemAction extends AbstractTableAction {

    public SpellOnItemAction(int item_id, int item_index, int table_id) {
        super(ActionTypes.SPELL_ON_ITEM, item_id, item_index, table_id);
    }

    public static boolean isInstance(int opcode) {
        return Action.pruneOpcode(opcode) == ActionTypes.SPELL_ON_ITEM;
    }
}
