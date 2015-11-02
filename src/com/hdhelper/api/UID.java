package com.hdhelper.api;

public class UID { // 32 bit value

    // The only -intractable- entity types (Note no projectiles/graphics)
    public static final int TYPE_PLAYER       =  0;
    public static final int TYPE_NPC          =  1;
    public static final int TYPE_OBJECT       =  2;
    public static final int TYPE_GROUND_ITEM  =  3;

    public final int uid;

    public UID(int uid) {
        this.uid = uid;
    }

    public int getRegionX() {
        return getRegionX(uid);
    }

    public int getRegionY() {
        return getRegionY(uid);
    }

    public int getEntityId() {
        return getEntityID(uid);
    }

    public int getEntityTypeId() {
        return getEntityType(uid);
    }

    public boolean isIntractable() { return isIntractable(uid); }

    //////////////////////////////////////////////////////////////////////////////////////////////

    public static int compile(int regionX, int regionY, int entityId, int entityType, boolean intractable) {
        regionX    &= 104;    // Maximum value of 104, BitMax of 127
        regionY    &= 104;    // Maximum value of 104, BitMax of 127
        entityId   &= 32767;  // Maximum value of 32767
        entityType &= 3;      // Maximum value of 3
        int uid = entityType << 29 + entityId << 14 + regionY << 7 + regionX;
        if(!intractable) uid -= Integer.MIN_VALUE; //Set the sign bit to 1
        return uid;
    }

    public static int getRegionX(final int UID) {
        return UID & 0x7f;
    }

    public static int getRegionY(final int UID) {
        return UID >> 7 & 0x7f;
    }

    public static int getEntityID(final int UID) {
        return UID >> 14 & 0x7fff;
    }

    public static int getEntityType(final int UID) {
        return UID >> 29 & 0x3;
    }
    //Checks the sign bit, checking if it's positive or negative is a faster/clever alternative
    public static boolean isIntractable(final int UID) { return UID > 0; }

}