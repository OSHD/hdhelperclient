package com.hdhelper.injector.patch;

import java.math.BigInteger;

public class GField {

    String owner;
    String name;
    String desc;

    Number encoder;
    Number decoder;

    int access;

    public GField() {
    }

    public GField(String owner, String name, String desc, Number decoder) {
        this.owner = owner;
        this.name  = name;
        this.desc  = desc;
        this.decoder = decoder;
    }

    private String pubDesc = null;
    public String getPublicDesc(GPatch p) {
        if(pubDesc != null) return pubDesc;
        return pubDesc = p.mapDesc(desc);
    }

    public String getDesc() {
        return desc;
    }

    public boolean isValid() {
        return owner != null && name != null && desc != null;
    }

    public String getName() {
        return name;
    }

    public boolean hasCodec() {
        return encoder != null || decoder != null;
    }

    public Number getEncoder() {
        if(encoder == null && decoder != null) {
            long v = decoder.longValue();
            if(v >= Integer.MIN_VALUE && v <= Integer.MAX_VALUE) {
                encoder = getInverse(decoder.intValue());
            } else {
                encoder = getInverse(decoder.longValue());
            }
        }
        return encoder;
    }

    private boolean fixedDecoder;
    public Number getDecoder() {
        return decoder;
    }


    public String getOwner() {
        return owner;
    }

    public static int getInverse( int coder ) {
        try {
            final BigInteger num = BigInteger.valueOf(coder);
            return num.modInverse(new BigInteger(String.valueOf(1L << 32))).intValue();
        } catch (final Exception e) {
            return 0;
        }
    }

    public static long getInverse(long coder) {
        try {
            final BigInteger num = BigInteger.valueOf(coder);
            return num.modInverse(new BigInteger(String.valueOf(1L << 63))).intValue();
        } catch (final Exception e) {
            return 0;
        }
    }

}
