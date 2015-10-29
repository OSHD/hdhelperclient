package com.hdhelper.api.ge;

import java.util.Random;

public abstract class RTFont extends RTGraphics {

    public RTIcon[] fieldS;
    Random fieldAs = new Random();
    String[] fieldAh = new String[100];
    int streakColor = -1;
    int underlineColor = -1;
    int defaultShadowColor = -1;
    int defaultColor = 0;
    int curColor = 0;
    public int alpha = 256;
    int fieldC = 0;
    int fieldAn = 0;
    int shadowColor = -1;
    public int baseLine = 0;

    //All package prvate
    int maxDescent; //N
    public int[] absWidth; //V
    public int[] widths; //I
    public byte[][] flags = new byte[256][]; //A
    public int[] insetY; //M  // from the top left corner
    public int[] heights; //B
    public byte[] fieldK; //K
    int maxAscent; //E
    public int[] insetX; //L


    RTFont(byte[] var1) {
        this.a(var1);
    }

    RTFont(byte[] var1, int[] drawOffsetX, int[] drawOffsetY, int[] widths, int[] heights, int[] colorMap, byte[][] bitmap) {

        this.insetX = drawOffsetX;
        this.insetY = drawOffsetY;
        this.widths = widths;
        this.heights = heights;

        this.a(var1);
        this.flags = bitmap;
        
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



    // Draws the bottom left of the text at the specified points
    public void drawString(String str, int x, int y, int color, int shadowColor) {
        if (str != null) {
            this.t(color, shadowColor);
            this.j(str, x, y);
        }
    }

    public void drawString(String str, int x, int y, int color) {
        drawString(str,x,y,color,-1);
    }



    public void drawCenterString(String str, int x, int y, int color, int shadowColor) {
        if (str != null) {
            this.t(color, shadowColor);
            this.j(str, x - this.getStringWidth(str) / 2, y);
        }
    }

    public void drawCenterString(String str, int x, int y, int color) {
        drawCenterString(str,x,y,color,-1);
    }




    //Draws the string on the left of the point, rather then the standard right.
    public void drawLeftString(String str, int x, int y, int color, int shadowColor) {
        if (str != null) {
            this.t(color, shadowColor);
            this.j(str, x - this.getStringWidth(str), y);
        }
    }

    public void drawLeftString(String str, int x, int y, int color) {
        drawLeftString(str,x,y,color,-1);
    }






    static int method31(byte[][] var0, byte[][] var1, int[] var2, int[] var3, int[] var4, int var5, int var6) {
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


    public static String embedTag(String var0) {
        int var1 = var0.length();
        int var2 = 0;

        for (int var3 = 0; var3 < var1; ++var3) {
            char var4 = var0.charAt(var3);
            if (var4 == 60 || var4 == 62) {
                var2 += 3;
            }
        }

        StringBuilder var6 = new StringBuilder(var1 + var2);

        for (int var7 = 0; var7 < var1; ++var7) {
            char var5 = var0.charAt(var7);
            if (var5 == 60) {
                var6.append("<lt>");
            } else if (var5 == 62) {
                var6.append("<gt>");
            } else {
                var6.append(var5);
            }
        }

        return var6.toString();
    }

    void method33(byte[] var0, int var1, int var2, int var3, int var4, int var5, int var6) {
        int var7 = var1 + var2 * rasterWidth;
        int var8 = rasterWidth - var3;
        int var9 = 0;
        int var10 = 0;
        int var11;
        if (var2 < viewportY) {
            var11 = viewportY - var2;
            var4 -= var11;
            var2 = viewportY;
            var10 += var11 * var3;
            var7 += var11 * rasterWidth;
        }

        if (var2 + var4 > viewportMaxY) {
            var4 -= var2 + var4 - viewportMaxY;
        }

        if (var1 < viewportX) {
            var11 = viewportX - var1;
            var3 -= var11;
            var1 = viewportX;
            var10 += var11;
            var7 += var11;
            var9 += var11;
            var8 += var11;
        }

        if (var1 + var3 > viewportMaxX) {
            var11 = var1 + var3 - viewportMaxX;
            var3 -= var11;
            var9 += var11;
            var8 += var11;
        }

        if (var3 > 0 && var4 > 0) {
            method35(raster, var0, var5, var10, var7, var3, var4, var8, var9, var6);
        }
    }

    static void method34(int[] var0, byte[] flags, int color, int var3, int var4, int var5, int var6, int var7, int var8) {
        int var9 = -(var5 >> 2);
        var5 = -(var5 & 3);

        for (int var10 = -var6; var10 < 0; ++var10) {
            int var11;
            for (var11 = var9; var11 < 0; ++var11) {
                if (flags[var3++] != 0) {
                    var0[var4++] = color;
                } else {
                    ++var4;
                }

                if (flags[var3++] != 0) {
                    var0[var4++] = color;
                } else {
                    ++var4;
                }

                if (flags[var3++] != 0) {
                    var0[var4++] = color;
                } else {
                    ++var4;
                }

                if (flags[var3++] != 0) {
                    var0[var4++] = color;
                } else {
                    ++var4;
                }
            }

            for (var11 = var5; var11 < 0; ++var11) {
                if (flags[var3++] != 0) {
                    var0[var4++] = color;
                } else {
                    ++var4;
                }
            }

            var4 += var7;
            var3 += var8;
        }

    }

    static void method35(int[] dest, byte[] flags, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
        var2 = ((var2 & 16711935) * var9 & -16711936) + ((var2 & '\uff00') * var9 & 16711680) >> 8;
        var9 = 256 - var9;

        for (int var10 = -var6; var10 < 0; ++var10) {
            for (int var11 = -var5; var11 < 0; ++var11) {
                if (flags[var3++] != 0) {
                    int var12 = dest[var4];
                    dest[var4++] = (((var12 & 16711935) * var9 & -16711936) + ((var12 & '\uff00') * var9 & 16711680) >> 8) + var2;
                } else {
                    ++var4;
                }
            }

            var4 += var7;
            var3 += var8;
        }

    }

    void method36(byte[] var0, int var1, int var2, int var3, int var4, int var5) {
        int var6 = var1 + var2 * rasterWidth;
        int var7 = rasterWidth - var3;
        int var8 = 0;
        int var9 = 0;
        int var10;
        if (var2 < viewportY) {
            var10 = viewportY - var2;
            var4 -= var10;
            var2 = viewportY;
            var9 += var10 * var3;
            var6 += var10 * rasterWidth;
        }

        if (var2 + var4 > viewportMaxY) {
            var4 -= var2 + var4 - viewportMaxY;
        }

        if (var1 < viewportX) {
            var10 = viewportX - var1;
            var3 -= var10;
            var1 = viewportX;
            var9 += var10;
            var6 += var10;
            var8 += var10;
            var7 += var10;
        }

        if (var1 + var3 > viewportMaxX) {
            var10 = var1 + var3 - viewportMaxX;
            var3 -= var10;
            var8 += var10;
            var7 += var10;
        }

        if (var3 > 0 && var4 > 0) {
            method34(raster, var0, var5, var9, var6, var3, var4, var7, var8);
        }
    }

    void a(byte[] buffer) {
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


    // getStringWidth
    public int getStringWidth(String var1) { //b
        if (var1 == null) {
            return 0;
        } else {
            int tag_start = -1;
            int var3 = -1;
            int var4 = 0;

            for (int index = 0; index < var1.length(); ++index) {
                char c = var1.charAt(index);
                if (c == '<' /*60*/) {
                    tag_start = index;
                } else {
                    if (c == '>' /*62*/ && tag_start != -1) {
                        String operation = var1.substring(tag_start + 1, index);
                        tag_start = -1;
                        if (operation.equals("lt")) {
                            c = 60;
                        } else {
                            if (!operation.equals("gt")) {
                                if (operation.startsWith("img=")) {
                                    try {
                                        int var8 = Util.method305(operation.substring(4), 1848156278);
                                        var4 += fieldS[var8].width;
                                        var3 = -1;
                                    } catch (Exception var9) {
                                        ;
                                    }
                                }
                                continue;
                            }

                            c = 62;
                        }
                    }

                    if (c == 160) { //Runescape Space Char
                        c = 32; // ' '
                    }

                    if (tag_start == -1) {
                        var4 += this.absWidth[(char) (Util.method264(c, (byte) -76) & 255)];
                        if (this.fieldK != null && var3 != -1) {
                            var4 += this.fieldK[(var3 << 8) + c];
                        }

                        var3 = c;
                    }
                }
            }

            return var4;
        }
    }

    int l(String var1, int[] var2, String[] var3) {
        if (var1 == null) {
            return 0;
        } else {
            int var4 = 0;
            int var5 = 0;
            StringBuilder var6 = new StringBuilder(100);
            int var7 = -1;
            int var8 = 0;
            byte var9 = 0;
            int var10 = -1;
            char var11 = 0;
            int var12 = 0;
            int var13 = var1.length();

            for (int var14 = 0; var14 < var13; ++var14) {
                char var15 = var1.charAt(var14);
                if (var15 == 60) {
                    var10 = var14;
                } else {
                    if (var15 == 62 && var10 != -1) {
                        String var16 = var1.substring(var10 + 1, var14);
                        var10 = -1;
                        var6.append('<');
                        var6.append(var16);
                        var6.append('>');
                        if (var16.equals("br")) {
                            var3[var12] = var6.toString().substring(var5, var6.length());
                            ++var12;
                            var5 = var6.length();
                            var4 = 0;
                            var7 = -1;
                            var11 = 0;
                        } else if (var16.equals("lt")) {
                            var4 += this.charWidth('<');
                            if (this.fieldK != null && var11 != -1) {
                                var4 += this.fieldK[(var11 << 8) + 60];
                            }

                            var11 = 60;
                        } else if (var16.equals("gt")) {
                            var4 += this.charWidth('>');
                            if (this.fieldK != null && var11 != -1) {
                                var4 += this.fieldK[(var11 << 8) + 62];
                            }

                            var11 = 62;
                        } else if (var16.startsWith("img=")) {
                            try {
                                int var17 = Util.method305(var16.substring(4), 182687781);
                                var4 += fieldS[var17].width;
                                var11 = 0;
                            } catch (Exception var18) {
                                ;
                            }
                        }

                        var15 = 0;
                    }

                    if (var10 == -1) {
                        if (var15 != 0) {
                            var6.append(var15);
                            var4 += this.charWidth(var15);
                            if (this.fieldK != null && var11 != -1) {
                                var4 += this.fieldK[(var11 << 8) + var15];
                            }

                            var11 = var15;
                        }

                        if (var15 == 32) {
                            var7 = var6.length();
                            var8 = var4;
                            var9 = 1;
                        }

                        if (var2 != null && var4 > var2[var12 < var2.length ? var12 : var2.length - 1] && var7 >= 0) {
                            var3[var12] = var6.toString().substring(var5, var7 - var9);
                            ++var12;
                            var5 = var7;
                            var7 = -1;
                            var4 -= var8;
                            var11 = 0;
                        }

                        if (var15 == 45) {
                            var7 = var6.length();
                            var8 = var4;
                            var9 = 0;
                        }
                    }
                }
            }

            String var19 = var6.toString();
            if (var19.length() > var5) {
                var3[var12++] = var19.substring(var5, var19.length());
            }

            return var12;
        }
    }

    public int w(String var1, int var2) {
        return this.l(var1, new int[]{var2}, fieldAh);
    }

    //k

    public int f(String var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
        if (var1 == null) {
            return 0;
        } else {
            this.t(var6, var7);
            if (var10 == 0) {
                var10 = this.baseLine;
            }

            int[] var11 = new int[]{var4};
            if (var5 < this.maxAscent + this.maxDescent + var10 && var5 < var10 + var10) {
                var11 = null;
            }

            int var12 = this.l(var1, var11, fieldAh);
            if (var9 == 3 && var12 == 1) {
                var9 = 1;
            }

            int var13;
            int var14;
            if (var9 == 0) {
                var13 = var3 + this.maxAscent;
            } else if (var9 == 1) {
                var13 = var3 + this.maxAscent + (var5 - this.maxAscent - this.maxDescent - (var12 - 1) * var10) / 2;
            } else if (var9 == 2) {
                var13 = var3 + var5 - this.maxDescent - (var12 - 1) * var10;
            } else {
                var14 = (var5 - this.maxAscent - this.maxDescent - (var12 - 1) * var10) / (var12 + 1);
                if (var14 < 0) {
                    var14 = 0;
                }

                var13 = var3 + this.maxAscent + var14;
                var10 += var14;
            }

            for (var14 = 0; var14 < var12; ++var14) {
                if (var8 == 0) {
                    this.j(fieldAh[var14], var2, var13);
                } else if (var8 == 1) {
                    this.j(fieldAh[var14], var2 + (var4 - this.getStringWidth(fieldAh[var14])) / 2, var13);
                } else if (var8 == 2) {
                    this.j(fieldAh[var14], var2 + var4 - this.getStringWidth(fieldAh[var14]), var13);
                } else if (var14 == var12 - 1) {
                    this.j(fieldAh[var14], var2, var13);
                } else {
                    this.u(fieldAh[var14], var4);
                    this.j(fieldAh[var14], var2, var13);
                    fieldC = 0;
                }

                var13 += var10;
            }

            return var12;
        }
    }

    public void d(String var1, int var2, int var3, int var4, int var5, int var6) {
        if (var1 != null) {
            this.t(var4, var5);
            int[] var7 = new int[var1.length()];

            for (int var8 = 0; var8 < var1.length(); ++var8) {
                var7[var8] = (int) (Math.sin((double) var8 / 2.0D + (double) var6 / 5.0D) * 5.0D);
            }

            this.g(var1, var2 - this.getStringWidth(var1) / 2, var3, (int[]) null, var7);
        }
    }

    public void x(String var1, int var2, int var3, int var4, int var5, int var6) {
        if (var1 != null) {
            this.t(var4, var5);
            int[] var7 = new int[var1.length()];
            int[] var8 = new int[var1.length()];

            for (int var9 = 0; var9 < var1.length(); ++var9) {
                var7[var9] = (int) (Math.sin((double) var9 / 5.0D + (double) var6 / 5.0D) * 5.0D);
                var8[var9] = (int) (Math.sin((double) var9 / 3.0D + (double) var6 / 5.0D) * 5.0D);
            }

            this.g(var1, var2 - this.getStringWidth(var1) / 2, var3, var7, var8);
        }
    }

    void t(int var1, int var2) {
        streakColor = -1;
        underlineColor = -1;
        defaultShadowColor = var2;
        shadowColor = var2;
        defaultColor = var1;
        curColor = var1;
        alpha = 256;
        fieldC = 0;
        fieldAn = 0;
    }

    void u(String var1, int var2) {
        int var3 = 0;
        boolean var4 = false;

        for (int var5 = 0; var5 < var1.length(); ++var5) {
            char var6 = var1.charAt(var5);
            if (var6 == 60) {
                var4 = true;
            } else if (var6 == 62) {
                var4 = false;
            } else if (!var4 && var6 == 32) {
                ++var3;
            }
        }

        if (var3 > 0) {
            fieldC = (var2 - this.getStringWidth(var1) << 8) / var3;
        }

    }


    void g(String var1, int var2, int var3, int[] var4, int[] var5) {
        var3 -= this.baseLine;
        int var6 = -1;
        int var7 = -1;
        int var8 = 0;

        for (int var9 = 0; var9 < var1.length(); ++var9) {
            if (var1.charAt(var9) != 0) {
                char var10 = (char) (Util.method264(var1.charAt(var9), (byte) 57) & 255);
                if (var10 == 60) {
                    var6 = var9;
                } else {
                    int var12;
                    int var13;
                    int var14;
                    if (var10 == 62 && var6 != -1) {
                        String var11 = var1.substring(var6 + 1, var9);
                        var6 = -1;
                        if (var11.equals("lt")) {
                            var10 = 60;
                        } else {
                            if (!var11.equals("gt")) {
                                if (var11.startsWith("img=")) {
                                    try {
                                        if (var4 != null) {
                                            var12 = var4[var8];
                                        } else {
                                            var12 = 0;
                                        }

                                        if (var5 != null) {
                                            var13 = var5[var8];
                                        } else {
                                            var13 = 0;
                                        }

                                        ++var8;
                                        var14 = Util.method305(var11.substring(4), 1058201672);
                                        RTIcon var18 = fieldS[var14];
                                        var18.draw(var2 + var12, var3 + this.baseLine - var18.height + var13);
                                        var2 += var18.width;
                                        var7 = -1;
                                    } catch (Exception var16) {
                                        ;
                                    }
                                } else {
                                    this.procConfig(var11);
                                }
                                continue;
                            }

                            var10 = 62;
                        }
                    }

                    if (var10 == 160) {
                        var10 = 32;
                    }

                    if (var6 == -1) {
                        if (this.fieldK != null && var7 != -1) {
                            var2 += this.fieldK[(var7 << 8) + var10];
                        }

                        int var17 = this.widths[var10];
                        var12 = this.heights[var10];
                        if (var4 != null) {
                            var13 = var4[var8];
                        } else {
                            var13 = 0;
                        }

                        if (var5 != null) {
                            var14 = var5[var8];
                        } else {
                            var14 = 0;
                        }


                        ++var8;
                        if (var10 != 32) {
                            if (alpha == 256) {
                                if (shadowColor != -1) {

                                    method36(this.flags[var10], var2 + this.insetX[var10] + 1 + var13, var3 + this.insetY[var10] + 1 + var14, var17, var12, shadowColor);
                                }

                                this.y(this.flags[var10], var2 + this.insetX[var10] + var13, var3 + this.insetY[var10] + var14, var17, var12, curColor);
                            } else {
                                if (shadowColor != -1) {
                                    method33(this.flags[var10], var2 + this.insetX[var10] + 1 + var13, var3 + this.insetY[var10] + 1 + var14, var17, var12, shadowColor, alpha);
                                }

                                this.p(this.flags[var10], var2 + this.insetX[var10] + var13, var3 + this.insetY[var10] + var14, var17, var12, curColor, alpha);
                            }
                        } else if (fieldC > 0) {
                            fieldAn += fieldC;
                            var2 += fieldAn >> 8;
                            fieldAn &= 255;
                        }

                        int var15 = this.absWidth[var10];
                        if (streakColor != -1) {
                            drawHorizontalLine(var2, var3 + (int) ((double) this.baseLine * 0.7D), var15, streakColor);
                        }

                        if (underlineColor != -1) {
                            drawHorizontalLine(var2, var3 + this.baseLine, var15, underlineColor);
                        }

                        var2 += var15;
                        var7 = var10;
                    }
                }
            }
        }

    }

    abstract void p(byte[] var1, int var2, int var3, int var4, int var5, int var6, int var7);

    public void o(String var1, int var2, int var3, int var4, int var5, int var6, int var7) {
        if (var1 != null) {
            this.t(var4, var5);
            double var8 = 7.0D - (double) var7 / 8.0D;
            if (var8 < 0.0D) {
                var8 = 0.0D;
            }

            int[] var10 = new int[var1.length()];

            for (int var11 = 0; var11 < var1.length(); ++var11) {
                var10[var11] = (int) (Math.sin((double) var11 / 1.5D + (double) var6 / 1.0D) * var8);
            }

            this.g(var1, var2 - this.getStringWidth(var1) / 2, var3, (int[]) null, var10);
        }
    }

    public void q(String var1, int var2, int var3, int var4, int var5, int var6) {
        if (var1 != null) {
            this.t(var4, var5);
            fieldAs.setSeed((long) var6);
            alpha = 192 + (fieldAs.nextInt() & 31);
            int[] var7 = new int[var1.length()];
            int var8 = 0;

            for (int var9 = 0; var9 < var1.length(); ++var9) {
                var7[var9] = var8;
                if ((fieldAs.nextInt() & 3) == 0) {
                    ++var8;
                }
            }

            this.g(var1, var2, var3, var7, (int[]) null);
        }
    }

    public int m(String var1, int var2) {
        int var3 = this.l(var1, new int[]{var2}, fieldAh);
        int var4 = 0;

        for (int var5 = 0; var5 < var3; ++var5) {
            int var6 = this.getStringWidth(fieldAh[var5]);
            if (var6 > var4) {
                var4 = var6;
            }
        }

        return var4;
    }

    int charWidth(char var1) {
        if (var1 == 160) {
            var1 = 32;
        }

        return this.absWidth[Util.method264(var1, (byte) -96) & 255];
    }

    void j(String var1, int x, int y) {

        y -= this.baseLine;
        int var4 = -1;
        int var5 = -1;

        for (int var6 = 0; var6 < var1.length(); ++var6) {
            if (var1.charAt(var6) != 0) {
                char curChar = (char) (Util.method264(var1.charAt(var6), (byte) 51) & 255);
                if (curChar == 60) {
                    var4 = var6;
                } else {
                    int var9;
                    if (curChar == 62 && var4 != -1) {
                        String var8 = var1.substring(var4 + 1, var6);
                        var4 = -1;
                        if (var8.equals("lt")) {
                            curChar = 60;
                        } else {
                            if (!var8.equals("gt")) {
                                if (var8.startsWith("img=")) {
                                    try {
                                        var9 = Util.method305(var8.substring(4), -1274458779);
                                        RTIcon var13 = fieldS[var9];
                                        var13.draw(x, y + this.baseLine - var13.height);
                                        x += var13.width;
                                        var5 = -1;
                                    } catch (Exception var11) {
                                        ;
                                    }
                                } else {
                                    this.procConfig(var8);
                                }
                                continue;
                            }

                            curChar = 62;
                        }
                    }

                    if (curChar == 160) {
                        curChar = 32;
                    }

                    if (var4 == -1) {

                        if (this.fieldK != null && var5 != -1) {
                            x += this.fieldK[(var5 << 8) + curChar];
                        }

                        int var12 = this.widths[curChar];
                        var9 = this.heights[curChar];

                        if (curChar != 32) {

                            if (alpha == 256) {

                                if (shadowColor != -1) {
                                    method36(this.flags[curChar], x + this.insetX[curChar] + 1, y + this.insetY[curChar] + 1, var12, var9, shadowColor);
                                }

                                this.y(this.flags[curChar], x + this.insetX[curChar], y + this.insetY[curChar], var12, var9, curColor);

                            } else {

                                if (shadowColor != -1) {
                                    method33(this.flags[curChar], x + this.insetX[curChar] + 1, y + this.insetY[curChar] + 1, var12, var9, shadowColor, alpha);
                                }

                                this.p(this.flags[curChar], x + this.insetX[curChar], y + this.insetY[curChar], var12, var9, curColor, alpha);
                            }

                        } else if (fieldC > 0) {

                            fieldAn += fieldC;
                            x += fieldAn >> 8;
                            fieldAn &= 255;

                        }

                        int var10 = this.absWidth[curChar];
                        if (streakColor != -1) {
                            drawHorizontalLine(x, y + (int) ((double) this.baseLine * 0.7D), var10, streakColor);
                        }



                        if (underlineColor != -1) {
                            drawHorizontalLine(x, y + this.baseLine + 1, var10, underlineColor);
                        }

                        x += var10;
                        var5 = curChar;
                    }
                }
            }
        }

    }

    abstract void y(byte[] var1, int var2, int var3, int var4, int var5, int var6); //y



    void procConfig(String var1) {
        try {
		//TagProcessor(...);
		// render.draw(... RTFont.IGNORE_TAGS);
            if (var1.startsWith("col=")) {
                curColor = Util.method69(var1.substring(4), 16);
            } else if (var1.equals("/col")) {
                curColor = defaultColor;
            } else if (var1.startsWith("str=")) { //streak = color
                streakColor = Util.method69(var1.substring(4), 16);
            } else if (var1.equals("str")) {
                streakColor = 8388608;
            } else if (var1.equals("/str")) {
                streakColor = -1;
            } else if (var1.startsWith("u=")) {
                underlineColor = Util.method69(var1.substring(2), 16);
            } else if (var1.equals("u")) {
                underlineColor = 0;
            } else if (var1.equals("/u")) {
                underlineColor = -1;
            } else if (var1.startsWith("shad=")) {
                shadowColor = Util.method69(var1.substring(5), 16);
            } else if (var1.equals("shad")) {
                shadowColor = 0;
            } else if (var1.equals("/shad")) {
                shadowColor = defaultShadowColor;
            } else if (var1.equals("br")) {
                this.t(defaultColor, defaultShadowColor);
            }
        } catch (Exception var3) {
            ;
        }

    }
}