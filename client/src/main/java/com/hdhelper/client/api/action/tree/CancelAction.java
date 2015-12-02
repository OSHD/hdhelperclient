package com.hdhelper.client.api.action.tree;


import com.hdhelper.client.api.action.ActionTypes;

public class CancelAction extends NotifyingAction {

    public CancelAction() {
        super(ActionTypes.CANCEL);
    }

    public static boolean isInstance(int opcode) {
        return opcode == ActionTypes.CANCEL;
    }

}
