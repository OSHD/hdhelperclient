package com.hdhelper.client.api.runeswing;

public class Widget {

    /** All possible widget component types **/
    public static final int TYPE_PANEL    = 0;
    public static final int TYPE_TODO     = 1;
    public static final int TYPE_TABLE    = 2;
    public static final int TYPE_BOX      = 3;
    public static final int TYPE_LABEL    = 4;
    public static final int TYPE_SPRITE   = 5;
    public static final int TYPE_MODEL    = 6;
    public static final int TYPE_MEDIA    = 7;
    public static final int TYPE_TOOLTIP  = 8;
    public static final int TYPE_DIVIDER  = 9;

    /** Spell flags **/ // Tells what a spell, when selected, can target
    public static final int SPELL_TARGET_GROUND_ITEM  = 0x1;  // 'Selected Spell' -> Ground Item
    public static final int SPELL_TARGET_NPC          = 0x2;  // 'Selected Spell' -> NPC
    public static final int SPELL_TARGET_OBJECT       = 0x4;  // 'Selected Spell' -> Object
    public static final int SPELL_TARGET_PLAYER       = 0x8;  // 'Selected Spell' -> Player
    public static final int SPELL_TARGET_TABLE_ITEM   = 0x10; // 'Selected Spell' -> Table Item
    public static final int SPELL_TARGET_WIDGET       = 0x20; // 'Selected Spell' -> Widget
    public static final int IS_SPELL                  = 0x40; // Tells if the interface is a 'spell'

    /** Action/Button Types */
    // NOTE: Buttons 1,4,5 have the same packet structure (same packet id) just different client-side results.
    // NOTE: Input buttons are currently used only for appearance modifications (skin color) //TODO dig more
    // NOTE: Buttons 4.5 only apply if  5 == Interface.opcodes[0][0] (getVar)
    public static final int BUTTON_INPUT   = 1; // Standard button w/ possible input required //TODO dig more
    public static final int BUTTON_SPELL   = 2; // Select Spell
    public static final int BUTTON_CLOSE   = 3; // Closes all open interfaces (0||3)
    public static final int BUTTON_VARFLIP = 4; // Sets a engine variable 'v' to (1 - v) //TODO what logic is this? bitwise?
    public static final int BUTTON_VARSET  = 5; // Sets a engine variable to static value
    public static final int BUTTON_DIALOG  = 6; // A dialog button (all options, not including 'continue')
    // NOTE: Continue is flagged under its config

    //TODO these tell what the interface is (for special cases), like equipment model, minimap, viewport, ect
    public static final int CONTENT_205 = 205;
    public static final int CONTENT_206 = 324;
    public static final int CONTENT_324 = 324;
    public static final int CONTENT_325 = 325;
    public static final int CONTENT_327 = 327;
    public static final int CONTENT_328 = 328;
    public static final int CONTENT_VIEWPORT = 1337; //main-screen
    public static final int CONTENT_MINIMAP = 1338;



    public static int getParentIndex(int WUID) {
        return WUID >> 16;
    }

    public static int getChildIndex(int WUID) {
        return WUID & 0xffff;
    }


}
