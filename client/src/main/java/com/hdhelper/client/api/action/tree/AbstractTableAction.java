package com.hdhelper.client.api.action.tree;


import com.hdhelper.client.api.runeswing.Widget;

// Named abstract since TableAction can not be named
// The rest follow for simplicity through commonality
public abstract class AbstractTableAction extends Action {

    public AbstractTableAction(int opcode,
                               int item_id, int item_index, int table_uid) {
        super(opcode, item_id, item_index, table_uid);
    }

    @Override
    public final int getSignificantArgs() {
        return SIG_ALL;
    }

    public int getItemID() {
        return arg0;
    }

    public int getItemIndex() {
        return arg1;
    }

    public int getTableUID() {
        return arg2;
    }

    public int getParent() {
        return Widget.getParentIndex(getTableUID());
    }

    public int getChild()  {
        return Widget.getChildIndex(getTableUID());
    }

    public int getActionIndex() {
        return -1;
    }

   /* public Widget getTable() {
        final int parent = getParent();
        final int child  = getChild();
        return Interfaces.get(parent, child);
    }
*/
   /* public Item getItem() {
        Widget container = getTable();
        if(container == null) return null;
        return new WidgetItem(container,getItemIndex());
    }
*/

    @Override
    public final boolean accepts(int opcode, int arg0, int arg1, int arg2) {
        return this.opcode == opcode
                && this.arg0 == arg0
                && this.arg1 == arg1
                && this.arg2 == arg2;
    }

    public String toString() {
        return "TableAction:[Address(uid=" + getTableUID() + ")=<" + getParent() + "#" + getChild() + "> ] Item(index=" + getItemIndex() + ")=" /*+ getItem()*/;
    }
}
