package com.hdhelper.client.api;

public class Equipment {

    public static Item getItemAt(Slot slot) {
        return ItemTable.getItemAt(ItemTable.EQUIPMENT,slot.index);
    }

    public enum Slot {

        HEAD(0),
        CAPE(1),
        NECK(2),
        WEAPON(3),
        BODY(4),
        SHIELD(5),
        LEGS(7),
        HANDS(9),
        FEET(10),
        RING(12),
        QUIVER(13);

        private final int index;

        Slot(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public Item get() {
            return Equipment.getItemAt(this);
        }

        public static Slot forIndex(final int index) {
            for (final Slot slot : Slot.values()) {
                if (slot.index == index)
                    return slot;
            }
            return null;
        }

    }





}
