package com.hdhelper.client.api.action.tree;

import com.hdhelper.client.api.action.ActionTypes;

public class VarSetButtonAction extends ButtonAction {
    public VarSetButtonAction(int WUID) {
        super(ActionTypes.BUTTON_VARSET,WUID);
    }

    public static boolean isInstance(int opcode) {
        return Action.pruneOpcode(opcode) == ActionTypes.BUTTON_VARSET;
    }
}
