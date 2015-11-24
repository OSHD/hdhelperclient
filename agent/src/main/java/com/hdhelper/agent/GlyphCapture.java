package com.hdhelper.agent;

//Allows us to capture the font data of runescape fonts to be used within the api
public abstract class GlyphCapture {

    public final String name; //The descriptor of the font

    public GlyphCapture(String name) {
        this.name = name;
    }

    public abstract void capture(byte[] meta, int[] drawOffsetX, int[] drawOffsetY, int[] widths, int[] height, int[] colorMap, byte[][] bitmap);

}
