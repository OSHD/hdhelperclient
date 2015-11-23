package com.hdhelper.client.api.action.tree;

import com.hdhelper.client.api.action.ActionTypes;

public class SpellOnWidgetAction extends Action {

    public SpellOnWidgetAction(int widget_index, int widget_id) {
        super(ActionTypes.SPELL_ON_WIDGET, 0, widget_index, widget_id);
    }

    public static boolean isInstance(int opcode) {
        return Action.pruneOpcode(opcode) == ActionTypes.SPELL_ON_WIDGET;
    }

    public int getSignificantArgs() {
        return ARG1 | ARG2;
    }

    @Override
    public boolean accepts(int opcode, int arg0, int arg1, int arg2) {
        return this.opcode == opcode
                && this.arg1 == arg1
                && this.arg2 == arg2;
    }

}
