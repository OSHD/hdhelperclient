package com.hdhelper.client.api;

import com.hdhelper.agent.services.RSItemDefinition;
import com.hdhelper.client.Client;

public class Item {

    protected int id;
    protected int q;

    public Item() {
        this(-1,-1);
    }

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
        return Client.get().getItemDef(getId());
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
