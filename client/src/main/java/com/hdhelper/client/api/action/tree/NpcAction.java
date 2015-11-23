package com.hdhelper.client.api.action.tree;


import com.hdhelper.agent.services.RSNpc;
import com.hdhelper.agent.services.RSNpcDefinition;
import com.hdhelper.client.api.action.ActionTypes;

public class NpcAction extends CharacterAction {

    public NpcAction(int opcode, int npcIndex) {
        super(opcode, npcIndex);
    }

    public int getActionIndex() {
        return opcode - ActionTypes.NPC_ACTION_0;
    }

    public static boolean isInstance(int opcode) {
        opcode = Action.pruneOpcode(opcode);
        return opcode >= ActionTypes.NPC_ACTION_0
            && opcode <= ActionTypes.NPC_ACTION_4;
    }

    public int getNpcIndex() {
        return getEntityId();
    }

    RSNpc getNpc0() {
        final int index = getNpcIndex();
        if(index < 0 || index > Short.MAX_VALUE) return null;
        return client.getNpcs()[index];
    }

    RSNpcDefinition getDef0() {
        RSNpc npc = getNpc0();
        if(npc == null) return null;
        return npc.getDef();
    }


    public String getName() {
        RSNpcDefinition def = getDef0();
        if(def == null) return "null";
        return def.getName();
    }

    public String getActionName() {
        RSNpcDefinition def = getDef0();
        if(def == null) return null;
       // return def.getActions()[getActionIndex()];
        return null;
    }

    @Override
    public String toString() {
        return "Npc Interaction [action-name(index=" + getActionIndex() + ")=" + getActionName() + "] on " + getNpc0();
    }

}
