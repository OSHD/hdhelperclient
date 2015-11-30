package com.hdhelper.client.api.db.iv.ge;

public enum Trend {

    NEUTRAL("neutral"),
    POSITIVE("positive"),
    NEGATIVE("negative");

    private final String name;

    Trend(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Trend parseTrend(String trend) {
        if(trend == null) return null;
        if (trend.equals("neutral")) {
            return Trend.NEUTRAL;
        } else if(trend.equals("positive")) {
            return Trend.POSITIVE;
        } else if(trend.equals("negative")) {
            return Trend.NEGATIVE;
        }
        return null;
    }

}
