package com.hdhelper.client.api.db.iv.ge;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GEItemTrend {

    private static final Pattern CHANGE_PATTERN =
            Pattern.compile("^(\\+|\\-)([0-9]+[\\.]{1}[0-9]+)%$");

    String trend;
    String change;

    public Trend getTrend() {
        return Trend.parseTrend(trend);
    }

    public double getChange() {
        return parseChange(change);
    }

    public static double parseChange(CharSequence change) {
        Matcher matcher = CHANGE_PATTERN.matcher(change);
        if(!matcher.matches())
            throw new NumberFormatException(change.toString());
        char sign = matcher.group(1).charAt(0);
        String strValue = matcher.group(2);
        double value = Double.parseDouble(strValue);
        return ((sign == '+') ? +value : -value);
    }

}
