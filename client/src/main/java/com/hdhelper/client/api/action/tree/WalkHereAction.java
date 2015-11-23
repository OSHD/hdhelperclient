package com.hdhelper.client.api.action.tree;

import com.hdhelper.client.api.action.ActionTypes;

public class WalkHereAction extends NotifyingAction {

    public WalkHereAction() {
        super(ActionTypes.WALK_HERE);
    }

    public int getScreenX() { return arg1; }
    public int getScreenY() { return arg2; }

    public static boolean isInstance(int opcode) {
        return Action.pruneOpcode(opcode) == ActionTypes.WALK_HERE;
    }

    //TODO get the tile over that cord (Done within client in landscape.drawTile)
}
