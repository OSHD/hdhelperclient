package com.hdhelper.client.api.action.tree;

public abstract class SpellOnCharacter extends SpellOnEntityAction {

    public SpellOnCharacter(int opcode, int entity_id) {
        super(opcode, entity_id,0,0);
    }

    public final int getSignificantArgs() {
        return ARG0;
    }


    public boolean accepts(int opcode, int arg0, int arg1, int arg2) {
        return this.opcode == opcode && this.arg0 == arg0;
    }

}
