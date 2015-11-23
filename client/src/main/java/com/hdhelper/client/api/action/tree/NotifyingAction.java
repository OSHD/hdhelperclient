package com.hdhelper.client.api.action.tree;

import com.hdhelper.client.api.action.ActionTypes;

//Any action that holds no information about what its interaction with,
// or in other words, it has no useful augments.
public abstract class NotifyingAction extends Action {

    public NotifyingAction(int opcode) {
        super(opcode,0,0,0);
    }

    @Override
    public final int getSignificantArgs() {
        return 0;
    }

    public static NotifyingAction valueOf(int opcode) {
        switch (Action.pruneOpcode(opcode)) {
            case ActionTypes.CANCEL:
                return new CancelAction();
            case ActionTypes.WALK_HERE:
                return new WalkHereAction();
        }
        return null;
    }

    @Override
    public final boolean accepts(int opcode, int arg0, int arg1, int arg2) {
        return this.opcode == opcode;
    }

}
