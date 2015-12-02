package com.hdhelper.client.api.action.tree;

// Though would be more explicit if it extends its
// type entity (eg. SpellOnNpc extends NpcAction extends SpellAction),
// we can not extend multiple classes

// ^ It's more important that its marker as a entity action,
// with the entity type known through EntityAction.getEntityType

import com.hdhelper.client.api.EntityType;
import com.hdhelper.client.api.action.ActionTypes;

public class SpellOnEntityAction extends EntityAction {

    public SpellOnEntityAction(int opcode, int entity_id, int local_x, int local_y) {
        super(opcode, entity_id, local_x, local_y);
    }

    public static EntityType spellOp2EntityType(int opcode) {
        switch (Action.pruneOpcode(opcode)) {
            case ActionTypes.SPELL_ON_OBJECT:
                return EntityType.OBJECT;
            case ActionTypes.SPELL_ON_NPC:
                return EntityType.NPC;
            case ActionTypes.SPELL_ON_PLAYER:
                return EntityType.PLAYER;
            case ActionTypes.SPELL_ON_GROUND_ITEM:
                return EntityType.GROUND_ITEM;
        }
        return null;
    }

    public boolean isValid() {
        return super.isValid() && type != null;
    }

}
