package com.hdhelper.client.api;

import com.hdhelper.agent.services.RSVarpbit;
import com.hdhelper.client.Client;

public class Varpbit {

    private static int[] MASKS = new int[32];


    static {
        int var0 = 2;
        for(int var1 = 0; var1 < 32; ++var1) {
            MASKS[var1] = var0 - 1;
            var0 += var0;
        }
    }


    private final RSVarpbit src;
    private final int id;

    private Varpbit(RSVarpbit src, int id) {
        this.src = src;
        this.id  = id;
    }

    public int getId() {
        return id;
    }

    public static Varpbit get(int id) {
        RSVarpbit varpbit = Client.get().getVarpbit(id);
        if(varpbit == null) return null;
        return new Varpbit(varpbit,id);
    }



    public int getBitCount() {
        final int low  = getLowBit();
        final int high = getHighBit();
        return high - low;
    }

    public int getMask() {
        return MASKS[getBitCount()];
    }

    public int getValue(int v) { // Extracts the int value with the provided word within the bit range defined in this varbit
        final int low  = getLowBit();
        final int high = getHighBit();
        return getValue(v,low,high);
    }

    public boolean getBooleanValue(int v) {  // Extracts the boolean value with the provided word within the bit range defined in this varbit
        return getValue(v) == 1;
    }

    public int set(int var, int value) {
        return setValue(var, getLowBit(), getHighBit(), value);
    }


    public int getValue() {
        final int index = getVarp();
        final int v = Config.get(index); //TODO
        return getValue(v);
    }

    public boolean booleanValue() {
        return getValue() == 1;
    }

    public boolean isBooleanType() {
        return getBitCount() == 1;
    }


    public int getLowBit()  {
        return src.getLowBit();
    }

    public int getHighBit() {
        return src.getHighBit();
    }

    public int getVarp()   {
        return src.getVarp();
    }

    @Override
    public String toString() {
        return  "VarpBit(id=" + id + ")<" + getVarp() + "> ( " + getLowBit() + " -> " + getHighBit() + " | " + getMask() + " ) == " + getValue();
    }


    /////////////////////////////////////////////////////////////////////////////////

    public static int getValue(int v, int low, int high) {
        final int mask = MASKS[high-low];
        return v >> low & mask;
    }

    public static int setValue(int cur_value, int low, int high, int put_value) {
        int mask = MASKS[high - low];
        if (put_value < 0 || put_value > mask) {
            throw new Error("Value out of range(mask = " + mask + " | value = " + cur_value + ")");
        }
        mask <<= low;
        return ((cur_value & ~mask) | (put_value << low & mask));
    }

    public static boolean getBooleanValue(int v, int pos) {
        return getValue(v,pos,pos) == 1;
    }

    public static int setBooleanValue(int cur_value, int pos, boolean value) {
        return setValue(cur_value,pos,pos,value ? 1 : 0);
    }


}
