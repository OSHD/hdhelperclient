package com.hdhelper.client.api.action.tree;

import com.hdhelper.client.api.action.ActionTypes;

public class GroundItemAction extends EntityAction {

    public GroundItemAction(int opcode, int entity_id, int local_x, int local_y) {
        super(opcode, entity_id, local_x, local_y);
    }

    public int getItemId() {
        return getEntityId();
    }

    public int getActionIndex() {

        return opcode - ActionTypes.GROUND_ITEM_ACTION_0;
    }

    public static boolean isInstance(int opcode) {
        opcode = Action.pruneOpcode(opcode);
        return opcode >= ActionTypes.GROUND_ITEM_ACTION_0
            && opcode <= ActionTypes.GROUND_ITEM_ACTION_4;
    }

}
