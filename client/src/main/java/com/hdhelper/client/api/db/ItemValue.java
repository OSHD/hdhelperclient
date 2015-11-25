package com.hdhelper.client.api.db;

public abstract class ItemValue {

    public static final int STATE_UPDATED  = 3;  // Our item is a 'updated' state. Which is deemed by the database
    public static final int STATE_OUTDATED = 2;  // We have a value, but its not up-to-date
    public static final int STATE_UNKNOWN  = 1;  // No value has not been figured yet
    public static final int STATE_ERROR    = 0;    // Error fetching acquiring any value for this item

    protected final int id;
    // Updated by the database thread
    protected int value;
    protected int state = STATE_UNKNOWN;

    protected ItemValue(int id) {
        this.id = id;
    }

    // We have the best value to work with
    public boolean isUpdated() {
        return getState() == STATE_UPDATED;
    }

    // We have a value, but it's stale
    public boolean isOutdated() {
        return getState() == STATE_OUTDATED;
    }

    // No value has been determined
    public boolean isUnknown() {
        return getState() == STATE_UNKNOWN;
    }

    // No value can or will ever be determined
    public boolean isError() {
        return getState() == STATE_ERROR;
    }

    //We have some value to work with...
    public boolean isReady() {
        return getState() >= 2;
    }

    protected int getState() {
        return state;
    }

    public int getValue() {
        return value;
    }

    public int getId() {
        return id;
    }

    public String info() {
        if(isError()) return "ERR";
        if(isUnknown()) return "...";
        return String.valueOf(getValue());
    }
}
