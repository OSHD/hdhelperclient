package com.hdhelper.client.api.action.tree;


import com.hdhelper.agent.services.RSNpc;

public class SpellOnNpc extends CharacterAction {

    public SpellOnNpc(int opcode, int npcIndex) {
        super(opcode, npcIndex);
    }

    public RSNpc getNpc0() {
        return client.getNpcs()[getEntityId()];
    }

}
