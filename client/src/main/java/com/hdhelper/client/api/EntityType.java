package com.hdhelper.client.api;

public enum EntityType {

    PLAYER(UID.TYPE_PLAYER),
    NPC(UID.TYPE_NPC),
    OBJECT(UID.TYPE_OBJECT),
    GROUND_ITEM(UID.TYPE_GROUND_ITEM);

    public final int id;

    EntityType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static EntityType forId(int id) {
        switch (id) {
            case UID.TYPE_PLAYER:
                return EntityType.PLAYER;
            case UID.TYPE_NPC:
                return EntityType.NPC;
            case UID.TYPE_OBJECT:
                return EntityType.OBJECT;
            case UID.TYPE_GROUND_ITEM:
                return EntityType.GROUND_ITEM;
        }
        return null;
    }

}
