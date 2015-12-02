package com.hdhelper.client.api.action.tree;

// Though would be more explicit if it extends its
// type entity (eg. SpellOnNpc extends NpcAction extends ItemOnEntity),
// we can not extend multiple classes


import com.hdhelper.client.api.EntityType;
import com.hdhelper.client.api.action.ActionTypes;

public class ItemOnEntityAction extends EntityAction {

    public ItemOnEntityAction(int opcode, int entity_id, int local_x, int local_y) {
        super(opcode, entity_id, local_x, local_y);
    }

    public static boolean isInstance(int opcode) {
        switch (Action.pruneOpcode(opcode)) {
            case ActionTypes.ITEM_ON_OBJECT:
            case ActionTypes.ITEM_ON_NPC:
            case ActionTypes.ITEM_ON_PLAYER:
            case ActionTypes.ITEM_ON_GROUND_ITEM:
                return true;
        }
        return false;
    }

    //Keeps within the scope of itemOn opcodes, when asserting an instance of itemOn
    public static EntityType itemOp2EntityType(int opcode) {
        switch (Action.pruneOpcode(opcode)) {
            case ActionTypes.ITEM_ON_OBJECT:
                return EntityType.OBJECT;
            case ActionTypes.ITEM_ON_NPC:
                return EntityType.NPC;
            case ActionTypes.ITEM_ON_PLAYER:
                return EntityType.PLAYER;
            case ActionTypes.ITEM_ON_GROUND_ITEM:
                return EntityType.GROUND_ITEM;
        }
        return null;
    }

}
