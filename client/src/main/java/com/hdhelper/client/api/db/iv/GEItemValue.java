package com.hdhelper.client.api.db.iv;

public final class GEItemValue extends ItemValue {

    GEItemValue(int id) {
        super(id);
    }

    void setValue(int value) {
        this.value = value;
    }

    void setState(int state) {
        this.state = state;
    }

}
