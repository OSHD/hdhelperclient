package com.hdhelper.client.api.db.iv.ge;

public final class GEItemDetail {

    String icon;
    String icon_large;
    int id;
    String type;
    String typeIcon;
    String name;
    String description;
    boolean members;

    GEItemPrice current;
    GEItemPrice today;

    GEItemTrend day30;
    GEItemTrend day90;
    GEItemTrend day180;

    public GEItemPrice getCurrentPrice() {
        return current;
    }


}
