package com.hdhelper.api;

import com.hdhelper.Main;
import com.hdhelper.agent.peer.RSItemDefinition;

public class Item {
    private int id;
    private int q;

    public Item(int id, int q) {
        this.id = id;
        this.q = q;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return q;
    }

    public RSItemDefinition getDef() {
        return Main.client.getItemDef(getId());
    }

    public String name() {
        RSItemDefinition def = getDef();
        if(def == null) return "null";
        return def.getName();
    }

    @Override
    public String toString() {
        return name() + "(" + getId() + ") x " + getQuantity();
    }

}
