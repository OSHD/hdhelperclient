package com.hdhelper.client.api.action.tree;

import com.hdhelper.client.api.action.ActionTypes;

public class DialogButtonAction extends ButtonAction {
    public DialogButtonAction(int WUID) {
        super(ActionTypes.BUTTON_DIALOG,WUID);
    }

    public static boolean isInstance(int opcode) {
        return Action.pruneOpcode(opcode) == ActionTypes.BUTTON_DIALOG;
    }
}
