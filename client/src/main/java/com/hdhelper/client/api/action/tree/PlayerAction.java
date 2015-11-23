package com.hdhelper.client.api.action.tree;

import com.hdhelper.agent.services.RSPlayer;
import com.hdhelper.client.api.action.ActionTypes;

public class PlayerAction extends CharacterAction {

    public PlayerAction(int opcode, int player_index) {
        super(opcode, player_index);
    }

    public int getPlayerIndex() {
        return arg0;
    }

    public int getActionIndex() {
        return opcode - ActionTypes.PLAYER_ACTION_0;
    }

    public static boolean isInstance(int opcode) {
        opcode = Action.pruneOpcode(opcode);
        return opcode >= ActionTypes.PLAYER_ACTION_0
            && opcode <= ActionTypes.PLAYER_ACTION_7;
    }

    public RSPlayer getPlayer0() {
        int index = getPlayerIndex();
        if(index < 0 || index > 2047) return null;
        return client.getPlayers()[index];
    }

    public String toString() {
        return "Player Action[" + getPlayerIndex() + "](" + "@" + getActionIndex() + ") on " + getPlayer0();
    }
}
