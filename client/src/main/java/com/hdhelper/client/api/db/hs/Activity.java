package com.hdhelper.client.api.db.hs;

public enum Activity {

    CASTLE_WARS,
    BARBARIAN_ASSAULT,
    DUEL_TOURNAMENT,
    FIST_OF_GUTHIX;

    public int getId() {
        return ordinal();
    }

}
