package com.hdhelper.client.api.action.tree;

import com.hdhelper.client.api.action.ActionTypes;

public class InputButtonAction extends ButtonAction {
    public InputButtonAction(int WUID) {
        super(ActionTypes.BUTTON_INPUT,WUID);
    }

    public static boolean isInstance(int opcode) {
        return Action.pruneOpcode(opcode) == ActionTypes.BUTTON_INPUT;
    }
}
