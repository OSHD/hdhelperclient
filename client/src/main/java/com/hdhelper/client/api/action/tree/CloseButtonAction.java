package com.hdhelper.client.api.action.tree;

import com.hdhelper.client.api.action.ActionTypes;

public class CloseButtonAction extends ButtonAction {
    public CloseButtonAction(int WUID) {
        super(ActionTypes.BUTTON_CLOSE,WUID);
    }

    public static boolean isInstance(int opcode) {
        return Action.pruneOpcode(opcode) == ActionTypes.BUTTON_CLOSE;
    }
}
