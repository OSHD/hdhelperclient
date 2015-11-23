package com.hdhelper.client.api.action.tree;

import com.hdhelper.client.api.action.ActionTypes;

public class VarFlipButtonAction extends ButtonAction {

    public VarFlipButtonAction(int WUID) {
        super(ActionTypes.BUTTON_VARFLIP,WUID);
    }

    public static boolean isInstance(int opcode) {
        return Action.pruneOpcode(opcode) == ActionTypes.BUTTON_VARFLIP;
    }
}
