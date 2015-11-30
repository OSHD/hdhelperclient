package com.hdhelper.client.api.action.tree;


import com.hdhelper.client.api.action.ActionTypes;

// Item actions only occur on tables
public class TableItemAction extends AbstractTableAction {

    public TableItemAction(int opcode,
                           int item_id,
                           int item_index,
                           int containerUID) {
        super(opcode, item_id, item_index, containerUID);
    }

    public static int getOpcodeForActIndex(int index) {
        if(index < 0 || index > 4) return -1;
        return ActionTypes.ITEM_ACTION_0 + index;
    }

    public static boolean isInstance(int op) {
        return op >= ActionTypes.ITEM_ACTION_0
            && op <= ActionTypes.ITEM_ACTION_4;
    }

    @Override
    public int getActionIndex() {
        return opcode - ActionTypes.ITEM_ACTION_0;
    }

    @Override
    public String toString() {
        final int parent = getParent();
        final int child = getChild();
        final int index = getItemIndex();
        final int address = getTableUID();
        return "ItemAction:[TableAddress(" + address + "," + index + ")=<" + parent + "#" + child + "#" + index + "> | ItemId=" + getItemID() + " | ItemIndex=" + getItemIndex() + " | ActionIndex=" + getActionIndex() + "]";
    }
}
