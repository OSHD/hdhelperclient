package com.hdhelper.client.api.db.iv.ge;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GEItemPrice {

    private static final Pattern PRICE_PATTERN =
            Pattern.compile("^([\\+|\\-]{0,1})([0-9]+[\\.]{0,1}[0-9]{0,1})([k,m]{0,1})$");

    String trend;
    String price;

    GEItemPrice() {
    }

    public Trend getTrend() {
        return Trend.parseTrend(trend);
    }

    public int getPrice() {
        return parsePrice(price.replace(",",""));
    }

    public static int parsePrice(CharSequence price) {

        Matcher matcher = PRICE_PATTERN.matcher(price);
        if(!matcher.matches())
            throw new NumberFormatException(price.toString());

        String signStr  = matcher.group(1);
        String valueStr = matcher.group(2);
        String unitStr  = matcher.group(3);

        boolean negative = (!signStr.isEmpty() && (signStr.charAt(0) == '-'));
        double value = Double.parseDouble(valueStr);
        int unit = getUnit(unitStr);

        if(negative)
            value = -value;
        value *= unit;

        return (int) value;
    }

    private static int getUnit(String str) {
        if(str.isEmpty()) return 1; //Default unit
        char unitChar = str.charAt(0);
        if(unitChar == 'k') return 1000;
        if(unitChar == 'm') return 1000000;
        throw new NumberFormatException("Unknown unit:" + str);
    }

}