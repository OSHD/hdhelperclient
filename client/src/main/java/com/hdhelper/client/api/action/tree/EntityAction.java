package com.hdhelper.client.api.action.tree;


import com.hdhelper.client.api.EntityType;

import static com.hdhelper.client.api.action.ActionTypes.*;

// For all entity types
public abstract class EntityAction extends Action {

    protected final EntityType type;

    public EntityAction(int opcode,
                        int entity_id, int local_x, int local_y) {
        super(opcode,entity_id,local_x,local_y);
        this.type = op2EntityType(opcode); // Must match with the respected opcode
    }

    public int getSignificantArgs() {
        return SIG_ALL;
    }

    // The type of entity this action is targeting
    public final EntityType getEntityType() {
        return type;
    }

    // Not all -general- types have multiple actions, like spellOnEntity, or ItemOnEntity
    public int getActionIndex() {
        return -1;
    }

    public int getEntityId() {
        return arg0;
    }

    // The regional position of the entity when this action was created, can not be guaranteed to be real time
    public int getRegionX() {
        return arg1;
    }

    public int getRegionY() {
        return arg2;
    }

    public int getWorldX() {
        return client.getRegionBaseX() + getRegionX();
    }

    public int getWorldY() {
        return client.getRegionBaseY() + getRegionY();
    }

    // Determines the entity type the action targets or is directly references by its opcode
    public static EntityType op2EntityType(int opcode) {
        switch (Action.pruneOpcode(opcode)) {

            case OBJECT_ACTION_0:
            case OBJECT_ACTION_1:
            case OBJECT_ACTION_2:
            case OBJECT_ACTION_3:
            case OBJECT_ACTION_4:
            case EXAMINE_OBJECT:
            case ITEM_ON_OBJECT:
            case SPELL_ON_OBJECT:
                return EntityType.OBJECT;

            case GROUND_ITEM_ACTION_0:
            case GROUND_ITEM_ACTION_1:
            case GROUND_ITEM_ACTION_2:
            case GROUND_ITEM_ACTION_3:
            case GROUND_ITEM_ACTION_4:
            case EXAMINE_GROUND_ITEM:
            case ITEM_ON_GROUND_ITEM:
            case SPELL_ON_GROUND_ITEM:
                return EntityType.GROUND_ITEM;

            case NPC_ACTION_0:
            case NPC_ACTION_1:
            case NPC_ACTION_2:
            case NPC_ACTION_3:
            case NPC_ACTION_4:
            case EXAMINE_NPC:
            case ITEM_ON_NPC:
            case SPELL_ON_NPC:
                return EntityType.NPC;

            case PLAYER_ACTION_0:
            case PLAYER_ACTION_1:
            case PLAYER_ACTION_2:
            case PLAYER_ACTION_3:
            case PLAYER_ACTION_4:
            case PLAYER_ACTION_5:
            case PLAYER_ACTION_6:
            case PLAYER_ACTION_7:
            case ITEM_ON_PLAYER:
            case SPELL_ON_PLAYER:
                return EntityType.PLAYER;

        }

        return null;

    }


    public boolean isValid() {
        if(!super.isValid()) return false;
        final int rx = getRegionX();
        if(rx < 0 || rx > 104) return false;
        final int ry = getRegionY();
        if(ry < 0 || ry > 104) return false;
        return true; //TODO validate entity id range, need maximum objects for this client
    }

    // Validate arguments
    public boolean accepts(int opcode, int arg0, int arg1, int arg2) {
        // All arguments are significant
        return this.opcode == opcode
                && this.arg0 == arg0
                && this.arg1 == arg1
                && this.arg2 == arg2;
    }

}
