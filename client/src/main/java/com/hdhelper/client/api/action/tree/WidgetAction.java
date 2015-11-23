package com.hdhelper.client.api.action.tree;

import com.hdhelper.client.api.action.ActionTypes;
import com.hdhelper.client.api.runeswing.Widget;

public class WidgetAction extends Action {

    public WidgetAction(int opcode,
                        int action_index,
                        int widget_index,
                        int widget_id) {
        super(opcode, action_index, widget_index, widget_id);
    }

    public WidgetAction(boolean type2,
                        int action_index,
                        int widget_index,
                        int widget_id) {
        this(type2 ? ActionTypes.WIDGET_ACTION_2 : ActionTypes.WIDGET_ACTION,
                action_index, widget_index, widget_id);
    }

    public static boolean isInstance(int opcode) {
        opcode = Action.pruneOpcode(opcode);
        return opcode == ActionTypes.WIDGET_ACTION
            || opcode == ActionTypes.WIDGET_ACTION_2;
    }

    public int getSignificantArgs() {
        return SIG_ALL;
    }

    //Arg0 is automatically lowered by one when it's derived from the client; when its a widgetAction
    public int getActionIndex() {
        return arg0;
    }

    public int getWidgetIndex() {
        return arg1;
    }

    public int getWidgetUID() {
        return arg2;
    }


    public int getParent() {
        return Widget.getParentIndex(getWidgetUID());
    }

    public int getChild()  {
        return Widget.getChildIndex(getWidgetUID());
    }

    public boolean isType2() {
        return opcode == ActionTypes.WIDGET_ACTION_2;
    }

   /* public Widget get() {
        final int UID = getParentUID();
        final int parent0 = Interface.getParentIndex(UID);
        final int child0  = Interface.getChildIndex(UID);
        final int index0  = getWidgetIndex();
        RSClient client = Game.getClient();
        RSInterface parent = client.getInterfaces()[parent0][child0];
        if(parent == null) return null;
        RSInterface[] children = parent.getChildren();
        if(children != null && index0 > 0 && index0 < children.length)
            return children[index0];
        return parent;
    }*/


    @Override
    public boolean accepts(int opcode, int arg0, int arg1, int arg2) {
        return this.opcode == opcode
                && this.arg0 == arg0
                && this.arg1 == arg1
                && this.arg2 == arg2;
    }

    @Override
    public String toString() {
        final int UID = getWidgetUID();
        final int parent = Widget.getParentIndex(UID);
        final int child  = Widget.getChildIndex(UID);
        final int index  = getWidgetIndex();
        final int action = getActionIndex();
        final int type = isType2() ? 2 : 1;
        return "WidgetAction:[Address=<" + parent + "#" + child + "#" + index + "> | ActonIndex=" + action + " | ActionType=" + type + "]" /*+ get()*/;
    }

}
