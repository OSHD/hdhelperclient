package com.hdhelper.client.api.db.iv;

public class ItemValueDatabases {

    private static final GEValueDatabase GE_VALUE_DATABASE =
            new GEValueDatabase();

    public static GEValueDatabase getGeValueDatabase() {
        return GE_VALUE_DATABASE;
    }

}
