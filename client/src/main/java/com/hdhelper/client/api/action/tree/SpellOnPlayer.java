package com.hdhelper.client.api.action.tree;


import com.hdhelper.agent.services.RSPlayer;

public class SpellOnPlayer extends CharacterAction {

    public SpellOnPlayer(int opcode, int entity_id) {
        super(opcode, entity_id);
    }

    public RSPlayer getPlayer0() {
        return client.getPlayers()[getEntityId()];
    }

}
