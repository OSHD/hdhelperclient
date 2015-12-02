package com.hdhelper.client.api.action.tree;

import com.hdhelper.client.api.action.ActionTypes;

// Selects a spell
public class SpellButtonAction extends ButtonAction {
    public SpellButtonAction(int WUID) {
        super(ActionTypes.BUTTON_SPELL,WUID);
    }

    public static boolean isInstance(int opcode) {
        return Action.pruneOpcode(opcode) == ActionTypes.BUTTON_SPELL;
    }
}
