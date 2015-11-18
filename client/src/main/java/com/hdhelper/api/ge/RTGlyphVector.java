package com.hdhelper.api.ge;

public class RTGlyphVector {

    int[] absWidth;
    int[] insetX;
    int[] insetY;
    int[] widths;
    int[] heights;
    byte[][] glyphs = new byte[256][];
    byte[] fieldK;
    int maxAscent;
    int maxDescent;
    int baseLine = 0;

    private RTGlyphVector() {
    }

    RTGlyphVector(byte[] meta) {
        this.unpackMeta(meta);
    }

    RTGlyphVector(int[] absWidth, int baseLine, int[] drawOffsetX, int[] drawOffsetY, int[] widths, int[] heights, int[] colorMap, byte[][] bitmap) {
        this.insetX = drawOffsetX;
        this.insetY = drawOffsetY;
        this.widths = widths;
        this.heights = heights;

        this.absWidth = absWidth;
        this.baseLine = baseLine;

        this.glyphs = bitmap;

        computeRanges();
    }

    RTGlyphVector(byte[] meta, int[] drawOffsetX, int[] drawOffsetY, int[] widths, int[] heights, int[] colorMap, byte[][] bitmap) {

        this.insetX = drawOffsetX;
        this.insetY = drawOffsetY;
        this.widths = widths;
        this.heights = heights;

        unpackMeta(meta); // Unpack absWidths, baseline

        this.glyphs = bitmap;

        computeRanges();
    }

    public RTGlyphVector copy() {

        RTGlyphVector dest = new RTGlyphVector();

        dest.glyphs   = this.glyphs.clone();
        dest.insetX   = this.insetX.clone();
        dest.insetY   = this.insetY.clone();
        dest.widths   = this.widths.clone();
        dest.heights  = this.heights.clone();
        dest.absWidth = this.absWidth.clone();

        dest.baseLine   = this.baseLine;
        dest.maxAscent  = this.maxAscent;
        dest.maxDescent = this.maxDescent;

        if(this.fieldK != null) {
            dest.fieldK = this.fieldK.clone();
        }

        return dest;

    }

    private void computeRanges() {

        int var8 = Integer.MAX_VALUE;
        int var9 = Integer.MIN_VALUE;

        for (int var10 = 0; var10 < 256; ++var10) {

            if (this.insetY[var10] < var8 && this.heights[var10] != 0) {
                var8 = this.insetY[var10];
            }

            if (this.insetY[var10] + this.heights[var10] > var9) {
                var9 = this.insetY[var10] + this.heights[var10];
            }

        }

        this.maxAscent = this.baseLine - var8;
        this.maxDescent = var9 - this.baseLine;

    }


    private void unpackMeta(byte[] buffer) {
        this.absWidth = new int[256];
        int caret;
        if (buffer.length == 257) {
            for (caret = 0; caret < this.absWidth.length; ++caret) {
                this.absWidth[caret] = buffer[caret] & 255;
            }

            this.baseLine = buffer[256] & 255;

        } else {
            caret = 0;

            for (int var3 = 0; var3 < 256; ++var3) {
                this.absWidth[var3] = buffer[caret++] & 255;
            }

            int[] glypSize = new int[256];
            int[] var4 = new int[256];

            int var5;
            for (var5 = 0; var5 < 256; ++var5) {
                glypSize[var5] = buffer[caret++] & 255;
            }

            for (var5 = 0; var5 < 256; ++var5) {
                var4[var5] = buffer[caret++] & 255;
            }

            byte[][] var11 = new byte[256][];

            int charB;
            for (int var6 = 0; var6 < 256; ++var6) {
                var11[var6] = new byte[glypSize[var6]];
                byte var7 = 0;

                for (charB = 0; charB < var11[var6].length; ++charB) {
                    var7 += buffer[caret++];
                    var11[var6][charB] = var7;
                }
            }

            byte[][] var12 = new byte[256][];

            int charA;
            for (charA = 0; charA < 256; ++charA) {
                var12[charA] = new byte[glypSize[charA]];
                byte var14 = 0;

                for (int var9 = 0; var9 < var12[charA].length; ++var9) {
                    var14 += buffer[caret++];
                    var12[charA][var9] = var14;
                }
            }

            this.fieldK = new byte[65536];

            for (charA = 0; charA < 256; ++charA) {
                if (charA != 32 && charA != 160) {
                    for (charB = 0; charB < 256; ++charB) {
                        if (charB != 32 && charB != 160) {
                            this.fieldK[(charA << 8) + charB] = (byte) method31(var11, var12, var4, this.absWidth, glypSize, charA, charB);
                        }
                    }
                }
            }

            this.baseLine = var4[32] + glypSize[32];


        }

    }

    private static int method31(byte[][] var0, byte[][] var1, int[] var2, int[] var3, int[] var4, int var5, int var6) {
        int var7 = var2[var5];
        int var8 = var7 + var4[var5];
        int var9 = var2[var6];
        int var10 = var9 + var4[var6];
        int var11 = var7;
        if (var9 > var7) {
            var11 = var9;
        }

        int var12 = var8;
        if (var10 < var8) {
            var12 = var10;
        }

        int var13 = var3[var5];
        if (var3[var6] < var13) {
            var13 = var3[var6];
        }

        byte[] var14 = var1[var5];
        byte[] var15 = var0[var6];
        int var16 = var11 - var7;
        int var17 = var11 - var9;

        for (int var18 = var11; var18 < var12; ++var18) {
            int var19 = var14[var16++] + var15[var17++];
            if (var19 < var13) {
                var13 = var19;
            }
        }

        return -var13;
    }


}
