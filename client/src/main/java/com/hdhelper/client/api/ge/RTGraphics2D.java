package com.hdhelper.client.api.ge;

public class RTGraphics2D extends RTGraphics {

    public  boolean fieldB = true;
    public  int[] fieldAn = new int[65536];
    public  TextureManager fieldAs;

     int fieldG;
     int fieldY;
     boolean fieldI = false;
     int[] fieldC = new int[1024];
     int alpha = 0;
     int screenCenterX;
     int screenCenterY;
     int fieldJ;
     boolean fieldV = false;

     int fieldP;
     int fieldR;
     int fieldZ;

     boolean offscreen = false;

    public static final int[] fieldAh   = new int[512];
    public static final int[] fieldAr   = new int[2048];
    public static final int[] SIN_TABLE = new int[2048];
    public static final int[] COS_TABLE = new int[2048];

    static {
        int var0;
        for (var0 = 1; var0 < 512; ++var0) {
            fieldAh[var0] = '\u8000' / var0;
        }

        for (var0 = 1; var0 < 2048; ++var0) {
            fieldAr[var0] = 65536 / var0;
        }

        for (var0 = 0; var0 < 2048; ++var0) {
            SIN_TABLE[var0] = (int) (65536.0D * Math.sin((double) var0 * 0.0030679615D));
            COS_TABLE[var0] = (int) (65536.0D * Math.cos((double) var0 * 0.0030679615D));
        }
    }

    public RTGraphics2D(){
    }

    public  final void method308(double var0) {
        method325(var0, 0, 512);
    }

    public  final void method309() {
        method310(viewportX, viewportY, viewportMaxX, viewportMaxY);
    }

     final void method310(int var0, int var1, int var2, int var3) {
        fieldJ = var2 - var0;
        fieldG = var3 - var1;
        method324();
        int var4;
        int var5;
        if (fieldC.length < fieldG) {
            var5 = fieldG;
            --var5;
            var5 |= var5 >>> 1;
            var5 |= var5 >>> 2;
            var5 |= var5 >>> 4;
            var5 |= var5 >>> 8;
            var5 |= var5 >>> 16;
            var4 = var5 + 1;
            fieldC = new int[var4];
        }

        var4 = var1 * rasterWidth + var0;

        for (var5 = 0; var5 < fieldG; ++var5) {
            fieldC[var5] = var4;
            var4 += rasterWidth;
        }

    }

     final void method311(int[] var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14) {
        if (offscreen) {
            if (var6 > fieldJ) {
                var6 = fieldJ;
            }

            if (var5 < 0) {
                var5 = 0;
            }
        }

        if (var5 < var6) {
            var4 += var5;
            var7 += var8 * var5;
            int var17 = var6 - var5;
            int var15;
            int var16;
            int var18;
            int var19;
            int var20;
            int var21;
            int var22;
            int var23;
            if (fieldI) {
                var23 = var5 - screenCenterX;
                var9 += var12 * var23;
                var10 += var13 * var23;
                var11 += var14 * var23;
                var22 = var11 >> 12;
                if (var22 != 0) {
                    var18 = var9 / var22;
                    var19 = var10 / var22;
                } else {
                    var18 = 0;
                    var19 = 0;
                }

                var9 += var12 * var17;
                var10 += var13 * var17;
                var11 += var14 * var17;
                var22 = var11 >> 12;
                if (var22 != 0) {
                    var20 = var9 / var22;
                    var21 = var10 / var22;
                } else {
                    var20 = 0;
                    var21 = 0;
                }

                var2 = (var18 << 20) + var19;
                var16 = ((var20 - var18) / var17 << 20) + (var21 - var19) / var17;
                var17 >>= 3;
                var8 <<= 3;
                var15 = var7 >> 8;
                if (fieldV) {
                    if (var17 > 0) {
                        do {
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var7 += var8;
                            var15 = var7 >> 8;
                            --var17;
                        } while (var17 > 0);
                    }

                    var17 = var6 - var5 & 7;
                    if (var17 > 0) {
                        do {
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            --var17;
                        } while (var17 > 0);
                    }
                } else {
                    if (var17 > 0) {
                        do {
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            var7 += var8;
                            var15 = var7 >> 8;
                            --var17;
                        } while (var17 > 0);
                    }

                    var17 = var6 - var5 & 7;
                    if (var17 > 0) {
                        do {
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            --var17;
                        } while (var17 > 0);
                    }
                }
            } else {
                var23 = var5 - screenCenterX;
                var9 += var12 * var23;
                var10 += var13 * var23;
                var11 += var14 * var23;
                var22 = var11 >> 14;
                if (var22 != 0) {
                    var18 = var9 / var22;
                    var19 = var10 / var22;
                } else {
                    var18 = 0;
                    var19 = 0;
                }

                var9 += var12 * var17;
                var10 += var13 * var17;
                var11 += var14 * var17;
                var22 = var11 >> 14;
                if (var22 != 0) {
                    var20 = var9 / var22;
                    var21 = var10 / var22;
                } else {
                    var20 = 0;
                    var21 = 0;
                }

                var2 = (var18 << 18) + var19;
                var16 = ((var20 - var18) / var17 << 18) + (var21 - var19) / var17;
                var17 >>= 3;
                var8 <<= 3;
                var15 = var7 >> 8;
                if (fieldV) {
                    if (var17 > 0) {
                        do {
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var7 += var8;
                            var15 = var7 >> 8;
                            --var17;
                        } while (var17 > 0);
                    }

                    var17 = var6 - var5 & 7;
                    if (var17 > 0) {
                        do {
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            --var17;
                        } while (var17 > 0);
                    }
                } else {
                    if (var17 > 0) {
                        do {
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            var7 += var8;
                            var15 = var7 >> 8;
                            --var17;
                        } while (var17 > 0);
                    }

                    var17 = var6 - var5 & 7;
                    if (var17 > 0) {
                        do {
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            --var17;
                        } while (var17 > 0);
                    }
                }
            }

        }
    }

     final void method312(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18) {
        int[] var19 = fieldAs.a(var18, (byte) -25);
        int var20;
        if (var19 == null) {
            var20 = fieldAs.v(var18, (byte) 76);
            method316(var0, var1, var2, var3, var4, var5, method320(var20, var6), method320(var20, var7), method320(var20, var8));
        } else {
            fieldI = fieldAs.b(var18, 1013279589);
            fieldV = fieldAs.i(var18, 1801166279);
            var20 = var4 - var3;
            int var21 = var1 - var0;
            int var22 = var5 - var3;
            int var23 = var2 - var0;
            int var24 = var7 - var6;
            int var25 = var8 - var6;
            int var26 = 0;
            if (var1 != var0) {
                var26 = (var4 - var3 << 16) / (var1 - var0);
            }

            int var27 = 0;
            if (var2 != var1) {
                var27 = (var5 - var4 << 16) / (var2 - var1);
            }

            int var28 = 0;
            if (var2 != var0) {
                var28 = (var3 - var5 << 16) / (var0 - var2);
            }

            int var29 = var20 * var23 - var22 * var21;
            if (var29 != 0) {
                int var30 = (var24 * var23 - var25 * var21 << 9) / var29;
                int var31 = (var25 * var20 - var24 * var22 << 9) / var29;
                var10 = var9 - var10;
                var13 = var12 - var13;
                var16 = var15 - var16;
                var11 -= var9;
                var14 -= var12;
                var17 -= var15;
                int var32 = var11 * var12 - var14 * var9 << 14;
                int var33 = var14 * var15 - var17 * var12 << 8;
                int var34 = var17 * var9 - var11 * var15 << 5;
                int var35 = var10 * var12 - var13 * var9 << 14;
                int var36 = var13 * var15 - var16 * var12 << 8;
                int var37 = var16 * var9 - var10 * var15 << 5;
                int var38 = var13 * var11 - var10 * var14 << 14;
                int var39 = var16 * var14 - var13 * var17 << 8;
                int var40 = var10 * var17 - var16 * var11 << 5;
                int var41;
                if (var0 <= var1 && var0 <= var2) {
                    if (var0 < fieldG) {
                        if (var1 > fieldG) {
                            var1 = fieldG;
                        }

                        if (var2 > fieldG) {
                            var2 = fieldG;
                        }

                        var6 = (var6 << 9) - var30 * var3 + var30;
                        if (var1 < var2) {
                            var5 = var3 <<= 16;
                            if (var0 < 0) {
                                var5 -= var28 * var0;
                                var3 -= var26 * var0;
                                var6 -= var31 * var0;
                                var0 = 0;
                            }

                            var4 <<= 16;
                            if (var1 < 0) {
                                var4 -= var27 * var1;
                                var1 = 0;
                            }

                            var41 = var0 - screenCenterY;
                            var32 += var34 * var41;
                            var35 += var37 * var41;
                            var38 += var40 * var41;
                            if ((var0 == var1 || var28 >= var26) && (var0 != var1 || var28 <= var27)) {
                                var2 -= var1;
                                var1 -= var0;
                                var0 = fieldC[var0];

                                while (true) {
                                    --var1;
                                    if (var1 < 0) {
                                        while (true) {
                                            --var2;
                                            if (var2 < 0) {
                                                return;
                                            }

                                            method318(raster, var19, 0, 0, var0, var4 >> 16, var5 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                            var5 += var28;
                                            var4 += var27;
                                            var6 += var31;
                                            var0 += rasterWidth;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method318(raster, var19, 0, 0, var0, var3 >> 16, var5 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                    var5 += var28;
                                    var3 += var26;
                                    var6 += var31;
                                    var0 += rasterWidth;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            } else {
                                var2 -= var1;
                                var1 -= var0;
                                var0 = fieldC[var0];

                                while (true) {
                                    --var1;
                                    if (var1 < 0) {
                                        while (true) {
                                            --var2;
                                            if (var2 < 0) {
                                                return;
                                            }

                                            method318(raster, var19, 0, 0, var0, var5 >> 16, var4 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                            var5 += var28;
                                            var4 += var27;
                                            var6 += var31;
                                            var0 += rasterWidth;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method318(raster, var19, 0, 0, var0, var5 >> 16, var3 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                    var5 += var28;
                                    var3 += var26;
                                    var6 += var31;
                                    var0 += rasterWidth;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            }
                        } else {
                            var4 = var3 <<= 16;
                            if (var0 < 0) {
                                var4 -= var28 * var0;
                                var3 -= var26 * var0;
                                var6 -= var31 * var0;
                                var0 = 0;
                            }

                            var5 <<= 16;
                            if (var2 < 0) {
                                var5 -= var27 * var2;
                                var2 = 0;
                            }

                            var41 = var0 - screenCenterY;
                            var32 += var34 * var41;
                            var35 += var37 * var41;
                            var38 += var40 * var41;
                            if ((var0 == var2 || var28 >= var26) && (var0 != var2 || var27 <= var26)) {
                                var1 -= var2;
                                var2 -= var0;
                                var0 = fieldC[var0];

                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        while (true) {
                                            --var1;
                                            if (var1 < 0) {
                                                return;
                                            }

                                            method318(raster, var19, 0, 0, var0, var3 >> 16, var5 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                            var5 += var27;
                                            var3 += var26;
                                            var6 += var31;
                                            var0 += rasterWidth;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method318(raster, var19, 0, 0, var0, var3 >> 16, var4 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                    var4 += var28;
                                    var3 += var26;
                                    var6 += var31;
                                    var0 += rasterWidth;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            } else {
                                var1 -= var2;
                                var2 -= var0;
                                var0 = fieldC[var0];

                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        while (true) {
                                            --var1;
                                            if (var1 < 0) {
                                                return;
                                            }

                                            method318(raster, var19, 0, 0, var0, var5 >> 16, var3 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                            var5 += var27;
                                            var3 += var26;
                                            var6 += var31;
                                            var0 += rasterWidth;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method318(raster, var19, 0, 0, var0, var4 >> 16, var3 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                    var4 += var28;
                                    var3 += var26;
                                    var6 += var31;
                                    var0 += rasterWidth;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            }
                        }
                    }
                } else if (var1 <= var2) {
                    if (var1 < fieldG) {
                        if (var2 > fieldG) {
                            var2 = fieldG;
                        }

                        if (var0 > fieldG) {
                            var0 = fieldG;
                        }

                        var7 = (var7 << 9) - var30 * var4 + var30;
                        if (var2 < var0) {
                            var3 = var4 <<= 16;
                            if (var1 < 0) {
                                var3 -= var26 * var1;
                                var4 -= var27 * var1;
                                var7 -= var31 * var1;
                                var1 = 0;
                            }

                            var5 <<= 16;
                            if (var2 < 0) {
                                var5 -= var28 * var2;
                                var2 = 0;
                            }

                            var41 = var1 - screenCenterY;
                            var32 += var34 * var41;
                            var35 += var37 * var41;
                            var38 += var40 * var41;
                            if ((var1 == var2 || var26 >= var27) && (var1 != var2 || var26 <= var28)) {
                                var0 -= var2;
                                var2 -= var1;
                                var1 = fieldC[var1];

                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        while (true) {
                                            --var0;
                                            if (var0 < 0) {
                                                return;
                                            }

                                            method318(raster, var19, 0, 0, var1, var5 >> 16, var3 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                            var3 += var26;
                                            var5 += var28;
                                            var7 += var31;
                                            var1 += rasterWidth;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method318(raster, var19, 0, 0, var1, var4 >> 16, var3 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                    var3 += var26;
                                    var4 += var27;
                                    var7 += var31;
                                    var1 += rasterWidth;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            } else {
                                var0 -= var2;
                                var2 -= var1;
                                var1 = fieldC[var1];

                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        while (true) {
                                            --var0;
                                            if (var0 < 0) {
                                                return;
                                            }

                                            method318(raster, var19, 0, 0, var1, var3 >> 16, var5 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                            var3 += var26;
                                            var5 += var28;
                                            var7 += var31;
                                            var1 += rasterWidth;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method318(raster, var19, 0, 0, var1, var3 >> 16, var4 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                    var3 += var26;
                                    var4 += var27;
                                    var7 += var31;
                                    var1 += rasterWidth;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            }
                        } else {
                            var5 = var4 <<= 16;
                            if (var1 < 0) {
                                var5 -= var26 * var1;
                                var4 -= var27 * var1;
                                var7 -= var31 * var1;
                                var1 = 0;
                            }

                            var3 <<= 16;
                            if (var0 < 0) {
                                var3 -= var28 * var0;
                                var0 = 0;
                            }

                            var41 = var1 - screenCenterY;
                            var32 += var34 * var41;
                            var35 += var37 * var41;
                            var38 += var40 * var41;
                            if (var26 < var27) {
                                var2 -= var0;
                                var0 -= var1;
                                var1 = fieldC[var1];

                                while (true) {
                                    --var0;
                                    if (var0 < 0) {
                                        while (true) {
                                            --var2;
                                            if (var2 < 0) {
                                                return;
                                            }

                                            method318(raster, var19, 0, 0, var1, var3 >> 16, var4 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                            var3 += var28;
                                            var4 += var27;
                                            var7 += var31;
                                            var1 += rasterWidth;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method318(raster, var19, 0, 0, var1, var5 >> 16, var4 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                    var5 += var26;
                                    var4 += var27;
                                    var7 += var31;
                                    var1 += rasterWidth;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            } else {
                                var2 -= var0;
                                var0 -= var1;
                                var1 = fieldC[var1];

                                while (true) {
                                    --var0;
                                    if (var0 < 0) {
                                        while (true) {
                                            --var2;
                                            if (var2 < 0) {
                                                return;
                                            }

                                            method318(raster, var19, 0, 0, var1, var4 >> 16, var3 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                            var3 += var28;
                                            var4 += var27;
                                            var7 += var31;
                                            var1 += rasterWidth;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method318(raster, var19, 0, 0, var1, var4 >> 16, var5 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                    var5 += var26;
                                    var4 += var27;
                                    var7 += var31;
                                    var1 += rasterWidth;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            }
                        }
                    }
                } else if (var2 < fieldG) {
                    if (var0 > fieldG) {
                        var0 = fieldG;
                    }

                    if (var1 > fieldG) {
                        var1 = fieldG;
                    }

                    var8 = (var8 << 9) - var30 * var5 + var30;
                    if (var0 < var1) {
                        var4 = var5 <<= 16;
                        if (var2 < 0) {
                            var4 -= var27 * var2;
                            var5 -= var28 * var2;
                            var8 -= var31 * var2;
                            var2 = 0;
                        }

                        var3 <<= 16;
                        if (var0 < 0) {
                            var3 -= var26 * var0;
                            var0 = 0;
                        }

                        var41 = var2 - screenCenterY;
                        var32 += var34 * var41;
                        var35 += var37 * var41;
                        var38 += var40 * var41;
                        if (var27 < var28) {
                            var1 -= var0;
                            var0 -= var2;
                            var2 = fieldC[var2];

                            while (true) {
                                --var0;
                                if (var0 < 0) {
                                    while (true) {
                                        --var1;
                                        if (var1 < 0) {
                                            return;
                                        }

                                        method318(raster, var19, 0, 0, var2, var4 >> 16, var3 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                        var4 += var27;
                                        var3 += var26;
                                        var8 += var31;
                                        var2 += rasterWidth;
                                        var32 += var34;
                                        var35 += var37;
                                        var38 += var40;
                                    }
                                }

                                method318(raster, var19, 0, 0, var2, var4 >> 16, var5 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                var4 += var27;
                                var5 += var28;
                                var8 += var31;
                                var2 += rasterWidth;
                                var32 += var34;
                                var35 += var37;
                                var38 += var40;
                            }
                        } else {
                            var1 -= var0;
                            var0 -= var2;
                            var2 = fieldC[var2];

                            while (true) {
                                --var0;
                                if (var0 < 0) {
                                    while (true) {
                                        --var1;
                                        if (var1 < 0) {
                                            return;
                                        }

                                        method318(raster, var19, 0, 0, var2, var3 >> 16, var4 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                        var4 += var27;
                                        var3 += var26;
                                        var8 += var31;
                                        var2 += rasterWidth;
                                        var32 += var34;
                                        var35 += var37;
                                        var38 += var40;
                                    }
                                }

                                method318(raster, var19, 0, 0, var2, var5 >> 16, var4 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                var4 += var27;
                                var5 += var28;
                                var8 += var31;
                                var2 += rasterWidth;
                                var32 += var34;
                                var35 += var37;
                                var38 += var40;
                            }
                        }
                    } else {
                        var3 = var5 <<= 16;
                        if (var2 < 0) {
                            var3 -= var27 * var2;
                            var5 -= var28 * var2;
                            var8 -= var31 * var2;
                            var2 = 0;
                        }

                        var4 <<= 16;
                        if (var1 < 0) {
                            var4 -= var26 * var1;
                            var1 = 0;
                        }

                        var41 = var2 - screenCenterY;
                        var32 += var34 * var41;
                        var35 += var37 * var41;
                        var38 += var40 * var41;
                        if (var27 < var28) {
                            var0 -= var1;
                            var1 -= var2;
                            var2 = fieldC[var2];

                            while (true) {
                                --var1;
                                if (var1 < 0) {
                                    while (true) {
                                        --var0;
                                        if (var0 < 0) {
                                            return;
                                        }

                                        method318(raster, var19, 0, 0, var2, var4 >> 16, var5 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                        var4 += var26;
                                        var5 += var28;
                                        var8 += var31;
                                        var2 += rasterWidth;
                                        var32 += var34;
                                        var35 += var37;
                                        var38 += var40;
                                    }
                                }

                                method318(raster, var19, 0, 0, var2, var3 >> 16, var5 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                var3 += var27;
                                var5 += var28;
                                var8 += var31;
                                var2 += rasterWidth;
                                var32 += var34;
                                var35 += var37;
                                var38 += var40;
                            }
                        } else {
                            var0 -= var1;
                            var1 -= var2;
                            var2 = fieldC[var2];

                            while (true) {
                                --var1;
                                if (var1 < 0) {
                                    while (true) {
                                        --var0;
                                        if (var0 < 0) {
                                            return;
                                        }

                                        method318(raster, var19, 0, 0, var2, var5 >> 16, var4 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                        var4 += var26;
                                        var5 += var28;
                                        var8 += var31;
                                        var2 += rasterWidth;
                                        var32 += var34;
                                        var35 += var37;
                                        var38 += var40;
                                    }
                                }

                                method318(raster, var19, 0, 0, var2, var5 >> 16, var3 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                var3 += var27;
                                var5 += var28;
                                var8 += var31;
                                var2 += rasterWidth;
                                var32 += var34;
                                var35 += var37;
                                var38 += var40;
                            }
                        }
                    }
                }
            }
        }
    }

    public  final void method313(TextureManager var0) {
        fieldAs = var0;
    }

     int method314(int var0, double var1) {
        double var3 = (double) (var0 >> 16) / 256.0D;
        double var5 = (double) (var0 >> 8 & 255) / 256.0D;
        double var7 = (double) (var0 & 255) / 256.0D;
        var3 = Math.pow(var3, var1);
        var5 = Math.pow(var5, var1);
        var7 = Math.pow(var7, var1);
        int var9 = (int) (var3 * 256.0D);
        int var10 = (int) (var5 * 256.0D);
        int var11 = (int) (var7 * 256.0D);
        return (var9 << 16) + (var10 << 8) + var11;
    }

    public  void checkOnScreen(int var0, int var1, int var2) {
        offscreen = var0 < 0 || var0 > fieldJ || var1 < 0 || var1 > fieldJ || var2 < 0 || var2 > fieldJ;
    }

     final void method316(int y1, int y2, int y3, int x1, int x2, int x3, int var6, int var7, int var8) {
        int var9 = x2 - x1;
        int var10 = y2 - y1;
        int var11 = x3 - x1;
        int var12 = y3 - y1;

        int var13 = var7 - var6;
        int var14 = var8 - var6;
        int var15;
        if (y3 != y2) {
            var15 = (x3 - x2 << 16) / (y3 - y2);
        } else {
            var15 = 0;
        }

        int var16;
        if (y2 != y1) {
            var16 = (var9 << 16) / var10;
        } else {
            var16 = 0;
        }

        int var17;
        if (y3 != y1) {
            var17 = (var11 << 16) / var12;
        } else {
            var17 = 0;
        }

        int var18 = var9 * var12 - var11 * var10;
        if (var18 != 0) {
            int var19 = (var13 * var12 - var14 * var10 << 8) / var18;
            int var20 = (var14 * var9 - var13 * var11 << 8) / var18;
            if (y1 <= y2 && y1 <= y3) {
                if (y1 < fieldG) {
                    if (y2 > fieldG) {
                        y2 = fieldG;
                    }

                    if (y3 > fieldG) {
                        y3 = fieldG;
                    }

                    var6 = (var6 << 8) - var19 * x1 + var19;
                    if (y2 < y3) {
                        x3 = x1 <<= 16;
                        if (y1 < 0) {
                            x3 -= var17 * y1;
                            x1 -= var16 * y1;
                            var6 -= var20 * y1;
                            y1 = 0;
                        }

                        x2 <<= 16;
                        if (y2 < 0) {
                            x2 -= var15 * y2;
                            y2 = 0;
                        }

                        if ((y1 == y2 || var17 >= var16) && (y1 != y2 || var17 <= var15)) {
                            y3 -= y2;
                            y2 -= y1;
                            y1 = fieldC[y1];

                            while (true) {
                                --y2;
                                if (y2 < 0) {
                                    while (true) {
                                        --y3;
                                        if (y3 < 0) {
                                            return;
                                        }

                                        method321(raster, y1, 0, 0, x2 >> 16, x3 >> 16, var6, var19);
                                        x3 += var17;
                                        x2 += var15;
                                        var6 += var20;
                                        y1 += rasterWidth;
                                    }
                                }

                                method321(raster, y1, 0, 0, x1 >> 16, x3 >> 16, var6, var19);
                                x3 += var17;
                                x1 += var16;
                                var6 += var20;
                                y1 += rasterWidth;
                            }
                        } else {
                            y3 -= y2;
                            y2 -= y1;
                            y1 = fieldC[y1];

                            while (true) {
                                --y2;
                                if (y2 < 0) {
                                    while (true) {
                                        --y3;
                                        if (y3 < 0) {
                                            return;
                                        }

                                        method321(raster, y1, 0, 0, x3 >> 16, x2 >> 16, var6, var19);
                                        x3 += var17;
                                        x2 += var15;
                                        var6 += var20;
                                        y1 += rasterWidth;
                                    }
                                }

                                method321(raster, y1, 0, 0, x3 >> 16, x1 >> 16, var6, var19);
                                x3 += var17;
                                x1 += var16;
                                var6 += var20;
                                y1 += rasterWidth;
                            }
                        }
                    } else {
                        x2 = x1 <<= 16;
                        if (y1 < 0) {
                            x2 -= var17 * y1;
                            x1 -= var16 * y1;
                            var6 -= var20 * y1;
                            y1 = 0;
                        }

                        x3 <<= 16;
                        if (y3 < 0) {
                            x3 -= var15 * y3;
                            y3 = 0;
                        }

                        if ((y1 == y3 || var17 >= var16) && (y1 != y3 || var15 <= var16)) {
                            y2 -= y3;
                            y3 -= y1;
                            y1 = fieldC[y1];

                            while (true) {
                                --y3;
                                if (y3 < 0) {
                                    while (true) {
                                        --y2;
                                        if (y2 < 0) {
                                            return;
                                        }

                                        method321(raster, y1, 0, 0, x1 >> 16, x3 >> 16, var6, var19);
                                        x3 += var15;
                                        x1 += var16;
                                        var6 += var20;
                                        y1 += rasterWidth;
                                    }
                                }

                                method321(raster, y1, 0, 0, x1 >> 16, x2 >> 16, var6, var19);
                                x2 += var17;
                                x1 += var16;
                                var6 += var20;
                                y1 += rasterWidth;
                            }
                        } else {
                            y2 -= y3;
                            y3 -= y1;
                            y1 = fieldC[y1];

                            while (true) {
                                --y3;
                                if (y3 < 0) {
                                    while (true) {
                                        --y2;
                                        if (y2 < 0) {
                                            return;
                                        }

                                        method321(raster, y1, 0, 0, x3 >> 16, x1 >> 16, var6, var19);
                                        x3 += var15;
                                        x1 += var16;
                                        var6 += var20;
                                        y1 += rasterWidth;
                                    }
                                }

                                method321(raster, y1, 0, 0, x2 >> 16, x1 >> 16, var6, var19);
                                x2 += var17;
                                x1 += var16;
                                var6 += var20;
                                y1 += rasterWidth;
                            }
                        }
                    }
                }
            } else if (y2 <= y3) {
                if (y2 < fieldG) {
                    if (y3 > fieldG) {
                        y3 = fieldG;
                    }

                    if (y1 > fieldG) {
                        y1 = fieldG;
                    }

                    var7 = (var7 << 8) - var19 * x2 + var19;
                    if (y3 < y1) {
                        x1 = x2 <<= 16;
                        if (y2 < 0) {
                            x1 -= var16 * y2;
                            x2 -= var15 * y2;
                            var7 -= var20 * y2;
                            y2 = 0;
                        }

                        x3 <<= 16;
                        if (y3 < 0) {
                            x3 -= var17 * y3;
                            y3 = 0;
                        }

                        if ((y2 == y3 || var16 >= var15) && (y2 != y3 || var16 <= var17)) {
                            y1 -= y3;
                            y3 -= y2;
                            y2 = fieldC[y2];

                            while (true) {
                                --y3;
                                if (y3 < 0) {
                                    while (true) {
                                        --y1;
                                        if (y1 < 0) {
                                            return;
                                        }

                                        method321(raster, y2, 0, 0, x3 >> 16, x1 >> 16, var7, var19);
                                        x1 += var16;
                                        x3 += var17;
                                        var7 += var20;
                                        y2 += rasterWidth;
                                    }
                                }

                                method321(raster, y2, 0, 0, x2 >> 16, x1 >> 16, var7, var19);
                                x1 += var16;
                                x2 += var15;
                                var7 += var20;
                                y2 += rasterWidth;
                            }
                        } else {
                            y1 -= y3;
                            y3 -= y2;
                            y2 = fieldC[y2];

                            while (true) {
                                --y3;
                                if (y3 < 0) {
                                    while (true) {
                                        --y1;
                                        if (y1 < 0) {
                                            return;
                                        }

                                        method321(raster, y2, 0, 0, x1 >> 16, x3 >> 16, var7, var19);
                                        x1 += var16;
                                        x3 += var17;
                                        var7 += var20;
                                        y2 += rasterWidth;
                                    }
                                }

                                method321(raster, y2, 0, 0, x1 >> 16, x2 >> 16, var7, var19);
                                x1 += var16;
                                x2 += var15;
                                var7 += var20;
                                y2 += rasterWidth;
                            }
                        }
                    } else {
                        x3 = x2 <<= 16;
                        if (y2 < 0) {
                            x3 -= var16 * y2;
                            x2 -= var15 * y2;
                            var7 -= var20 * y2;
                            y2 = 0;
                        }

                        x1 <<= 16;
                        if (y1 < 0) {
                            x1 -= var17 * y1;
                            y1 = 0;
                        }

                        if (var16 < var15) {
                            y3 -= y1;
                            y1 -= y2;
                            y2 = fieldC[y2];

                            while (true) {
                                --y1;
                                if (y1 < 0) {
                                    while (true) {
                                        --y3;
                                        if (y3 < 0) {
                                            return;
                                        }

                                        method321(raster, y2, 0, 0, x1 >> 16, x2 >> 16, var7, var19);
                                        x1 += var17;
                                        x2 += var15;
                                        var7 += var20;
                                        y2 += rasterWidth;
                                    }
                                }

                                method321(raster, y2, 0, 0, x3 >> 16, x2 >> 16, var7, var19);
                                x3 += var16;
                                x2 += var15;
                                var7 += var20;
                                y2 += rasterWidth;
                            }
                        } else {
                            y3 -= y1;
                            y1 -= y2;
                            y2 = fieldC[y2];

                            while (true) {
                                --y1;
                                if (y1 < 0) {
                                    while (true) {
                                        --y3;
                                        if (y3 < 0) {
                                            return;
                                        }

                                        method321(raster, y2, 0, 0, x2 >> 16, x1 >> 16, var7, var19);
                                        x1 += var17;
                                        x2 += var15;
                                        var7 += var20;
                                        y2 += rasterWidth;
                                    }
                                }

                                method321(raster, y2, 0, 0, x2 >> 16, x3 >> 16, var7, var19);
                                x3 += var16;
                                x2 += var15;
                                var7 += var20;
                                y2 += rasterWidth;
                            }
                        }
                    }
                }
            } else if (y3 < fieldG) {
                if (y1 > fieldG) {
                    y1 = fieldG;
                }

                if (y2 > fieldG) {
                    y2 = fieldG;
                }

                var8 = (var8 << 8) - var19 * x3 + var19;
                if (y1 < y2) {
                    x2 = x3 <<= 16;
                    if (y3 < 0) {
                        x2 -= var15 * y3;
                        x3 -= var17 * y3;
                        var8 -= var20 * y3;
                        y3 = 0;
                    }

                    x1 <<= 16;
                    if (y1 < 0) {
                        x1 -= var16 * y1;
                        y1 = 0;
                    }

                    if (var15 < var17) {
                        y2 -= y1;
                        y1 -= y3;
                        y3 = fieldC[y3];

                        while (true) {
                            --y1;
                            if (y1 < 0) {
                                while (true) {
                                    --y2;
                                    if (y2 < 0) {
                                        return;
                                    }

                                    method321(raster, y3, 0, 0, x2 >> 16, x1 >> 16, var8, var19);
                                    x2 += var15;
                                    x1 += var16;
                                    var8 += var20;
                                    y3 += rasterWidth;
                                }
                            }

                            method321(raster, y3, 0, 0, x2 >> 16, x3 >> 16, var8, var19);
                            x2 += var15;
                            x3 += var17;
                            var8 += var20;
                            y3 += rasterWidth;
                        }
                    } else {
                        y2 -= y1;
                        y1 -= y3;
                        y3 = fieldC[y3];

                        while (true) {
                            --y1;
                            if (y1 < 0) {
                                while (true) {
                                    --y2;
                                    if (y2 < 0) {
                                        return;
                                    }

                                    method321(raster, y3, 0, 0, x1 >> 16, x2 >> 16, var8, var19);
                                    x2 += var15;
                                    x1 += var16;
                                    var8 += var20;
                                    y3 += rasterWidth;
                                }
                            }

                            method321(raster, y3, 0, 0, x3 >> 16, x2 >> 16, var8, var19);
                            x2 += var15;
                            x3 += var17;
                            var8 += var20;
                            y3 += rasterWidth;
                        }
                    }
                } else {
                    x1 = x3 <<= 16;
                    if (y3 < 0) {
                        x1 -= var15 * y3;
                        x3 -= var17 * y3;
                        var8 -= var20 * y3;
                        y3 = 0;
                    }

                    x2 <<= 16;
                    if (y2 < 0) {
                        x2 -= var16 * y2;
                        y2 = 0;
                    }

                    if (var15 < var17) {
                        y1 -= y2;
                        y2 -= y3;
                        y3 = fieldC[y3];

                        while (true) {
                            --y2;
                            if (y2 < 0) {
                                while (true) {
                                    --y1;
                                    if (y1 < 0) {
                                        return;
                                    }

                                    method321(raster, y3, 0, 0, x2 >> 16, x3 >> 16, var8, var19);
                                    x2 += var16;
                                    x3 += var17;
                                    var8 += var20;
                                    y3 += rasterWidth;
                                }
                            }

                            method321(raster, y3, 0, 0, x1 >> 16, x3 >> 16, var8, var19);
                            x1 += var15;
                            x3 += var17;
                            var8 += var20;
                            y3 += rasterWidth;
                        }
                    } else {
                        y1 -= y2;
                        y2 -= y3;
                        y3 = fieldC[y3];

                        while (true) {
                            --y2;
                            if (y2 < 0) {
                                while (true) {
                                    --y1;
                                    if (y1 < 0) {
                                        return;
                                    }

                                    method321(raster, y3, 0, 0, x3 >> 16, x2 >> 16, var8, var19);
                                    x2 += var16;
                                    x3 += var17;
                                    var8 += var20;
                                    y3 += rasterWidth;
                                }
                            }

                            method321(raster, y3, 0, 0, x3 >> 16, x1 >> 16, var8, var19);
                            x1 += var15;
                            x3 += var17;
                            var8 += var20;
                            y3 += rasterWidth;
                        }
                    }
                }
            }
        }
    }

     final void method317(int[] buffer, int pos, int var2, int var3, int var4, int var5) {
        if (offscreen) {
            if (var5 > fieldJ) {
                var5 = fieldJ;
            }

            if (var4 < 0) {
                var4 = 0;
            }
        }

        if (var4 < var5) {
            pos += var4;
            var3 = var5 - var4 >> 2;
            if (alpha == 0) {
                while (true) {
                    --var3;
                    if (var3 < 0) {
                        var3 = var5 - var4 & 3;

                        while (true) {
                            --var3;
                            if (var3 < 0) {
                                return;
                            }

                            buffer[pos++] = var2;
                        }
                    }

                    buffer[pos++] = var2;
                    buffer[pos++] = var2;
                    buffer[pos++] = var2;
                    buffer[pos++] = var2;
                }
            } else if (alpha == 254) {
                while (true) {
                    --var3;
                    if (var3 < 0) {
                        var3 = var5 - var4 & 3;

                        while (true) {
                            --var3;
                            if (var3 < 0) {
                                return;
                            }

                            buffer[pos++] = buffer[pos];
                        }
                    }

                    buffer[pos++] = buffer[pos];
                    buffer[pos++] = buffer[pos];
                    buffer[pos++] = buffer[pos];
                    buffer[pos++] = buffer[pos];
                }
            } else {
                int var6 = alpha;
                int var7 = 256 - alpha;
                var2 = ((var2 & 16711935) * var7 >> 8 & 16711935) + ((var2 & '\uff00') * var7 >> 8 & '\uff00');

                while (true) {
                    --var3;
                    int var8;
                    if (var3 < 0) {
                        var3 = var5 - var4 & 3;

                        while (true) {
                            --var3;
                            if (var3 < 0) {
                                return;
                            }

                            var8 = buffer[pos];
                            buffer[pos++] = var2 + ((var8 & 16711935) * var6 >> 8 & 16711935) + ((var8 & '\uff00') * var6 >> 8 & '\uff00');
                        }
                    }

                    var8 = buffer[pos];
                    buffer[pos++] = var2 + ((var8 & 16711935) * var6 >> 8 & 16711935) + ((var8 & '\uff00') * var6 >> 8 & '\uff00');
                    var8 = buffer[pos];
                    buffer[pos++] = var2 + ((var8 & 16711935) * var6 >> 8 & 16711935) + ((var8 & '\uff00') * var6 >> 8 & '\uff00');
                    var8 = buffer[pos];
                    buffer[pos++] = var2 + ((var8 & 16711935) * var6 >> 8 & 16711935) + ((var8 & '\uff00') * var6 >> 8 & '\uff00');
                    var8 = buffer[pos];
                    buffer[pos++] = var2 + ((var8 & 16711935) * var6 >> 8 & 16711935) + ((var8 & '\uff00') * var6 >> 8 & '\uff00');
                }
            }
        }
    }

     final void method318(int[] var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14) {
        if (offscreen) {
            if (var6 > fieldJ) {
                var6 = fieldJ;
            }

            if (var5 < 0) {
                var5 = 0;
            }
        }

        if (var5 < var6) {
            var4 += var5;
            var7 += var8 * var5;
            int var17 = var6 - var5;
            int var15;
            int var16;
            int var10000;
            int var18;
            int var19;
            int var20;
            int var21;
            int var22;
            int var23;
            if (fieldI) {
                var23 = var5 - screenCenterX;
                var9 += (var12 >> 3) * var23;
                var10 += (var13 >> 3) * var23;
                var11 += (var14 >> 3) * var23;
                var22 = var11 >> 12;
                if (var22 != 0) {
                    var18 = var9 / var22;
                    var19 = var10 / var22;
                    if (var18 < 0) {
                        var18 = 0;
                    } else if (var18 > 4032) {
                        var18 = 4032;
                    }
                } else {
                    var18 = 0;
                    var19 = 0;
                }

                var9 += var12;
                var10 += var13;
                var11 += var14;
                var22 = var11 >> 12;
                if (var22 != 0) {
                    var20 = var9 / var22;
                    var21 = var10 / var22;
                    if (var20 < 0) {
                        var20 = 0;
                    } else if (var20 > 4032) {
                        var20 = 4032;
                    }
                } else {
                    var20 = 0;
                    var21 = 0;
                }

                var2 = (var18 << 20) + var19;
                var16 = (var20 - var18 >> 3 << 20) + (var21 - var19 >> 3);
                var17 >>= 3;
                var8 <<= 3;
                var15 = var7 >> 8;
                if (fieldV) {
                    if (var17 > 0) {
                        do {
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var10000 = var2 + var16;
                            var18 = var20;
                            var19 = var21;
                            var9 += var12;
                            var10 += var13;
                            var11 += var14;
                            var22 = var11 >> 12;
                            if (var22 != 0) {
                                var20 = var9 / var22;
                                var21 = var10 / var22;
                                if (var20 < 0) {
                                    var20 = 0;
                                } else if (var20 > 4032) {
                                    var20 = 4032;
                                }
                            } else {
                                var20 = 0;
                                var21 = 0;
                            }

                            var2 = (var18 << 20) + var19;
                            var16 = (var20 - var18 >> 3 << 20) + (var21 - var19 >> 3);
                            var7 += var8;
                            var15 = var7 >> 8;
                            --var17;
                        } while (var17 > 0);
                    }

                    var17 = var6 - var5 & 7;
                    if (var17 > 0) {
                        do {
                            var3 = var1[(var2 & 4032) + (var2 >>> 26)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            --var17;
                        } while (var17 > 0);
                    }
                } else {
                    if (var17 > 0) {
                        do {
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var10000 = var2 + var16;
                            var18 = var20;
                            var19 = var21;
                            var9 += var12;
                            var10 += var13;
                            var11 += var14;
                            var22 = var11 >> 12;
                            if (var22 != 0) {
                                var20 = var9 / var22;
                                var21 = var10 / var22;
                                if (var20 < 0) {
                                    var20 = 0;
                                } else if (var20 > 4032) {
                                    var20 = 4032;
                                }
                            } else {
                                var20 = 0;
                                var21 = 0;
                            }

                            var2 = (var18 << 20) + var19;
                            var16 = (var20 - var18 >> 3 << 20) + (var21 - var19 >> 3);
                            var7 += var8;
                            var15 = var7 >> 8;
                            --var17;
                        } while (var17 > 0);
                    }

                    var17 = var6 - var5 & 7;
                    if (var17 > 0) {
                        do {
                            if ((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            --var17;
                        } while (var17 > 0);
                    }
                }
            } else {
                var23 = var5 - screenCenterX;
                var9 += (var12 >> 3) * var23;
                var10 += (var13 >> 3) * var23;
                var11 += (var14 >> 3) * var23;
                var22 = var11 >> 14;
                if (var22 != 0) {
                    var18 = var9 / var22;
                    var19 = var10 / var22;
                    if (var18 < 0) {
                        var18 = 0;
                    } else if (var18 > 16256) {
                        var18 = 16256;
                    }
                } else {
                    var18 = 0;
                    var19 = 0;
                }

                var9 += var12;
                var10 += var13;
                var11 += var14;
                var22 = var11 >> 14;
                if (var22 != 0) {
                    var20 = var9 / var22;
                    var21 = var10 / var22;
                    if (var20 < 0) {
                        var20 = 0;
                    } else if (var20 > 16256) {
                        var20 = 16256;
                    }
                } else {
                    var20 = 0;
                    var21 = 0;
                }

                var2 = (var18 << 18) + var19;
                var16 = (var20 - var18 >> 3 << 18) + (var21 - var19 >> 3);
                var17 >>= 3;
                var8 <<= 3;
                var15 = var7 >> 8;
                if (fieldV) {
                    if (var17 > 0) {
                        do {
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var10000 = var2 + var16;
                            var18 = var20;
                            var19 = var21;
                            var9 += var12;
                            var10 += var13;
                            var11 += var14;
                            var22 = var11 >> 14;
                            if (var22 != 0) {
                                var20 = var9 / var22;
                                var21 = var10 / var22;
                                if (var20 < 0) {
                                    var20 = 0;
                                } else if (var20 > 16256) {
                                    var20 = 16256;
                                }
                            } else {
                                var20 = 0;
                                var21 = 0;
                            }

                            var2 = (var18 << 18) + var19;
                            var16 = (var20 - var18 >> 3 << 18) + (var21 - var19 >> 3);
                            var7 += var8;
                            var15 = var7 >> 8;
                            --var17;
                        } while (var17 > 0);
                    }

                    var17 = var6 - var5 & 7;
                    if (var17 > 0) {
                        do {
                            var3 = var1[(var2 & 16256) + (var2 >>> 25)];
                            var0[var4++] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            var2 += var16;
                            --var17;
                        } while (var17 > 0);
                    }
                } else {
                    if (var17 > 0) {
                        do {
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var10000 = var2 + var16;
                            var18 = var20;
                            var19 = var21;
                            var9 += var12;
                            var10 += var13;
                            var11 += var14;
                            var22 = var11 >> 14;
                            if (var22 != 0) {
                                var20 = var9 / var22;
                                var21 = var10 / var22;
                                if (var20 < 0) {
                                    var20 = 0;
                                } else if (var20 > 16256) {
                                    var20 = 16256;
                                }
                            } else {
                                var20 = 0;
                                var21 = 0;
                            }

                            var2 = (var18 << 18) + var19;
                            var16 = (var20 - var18 >> 3 << 18) + (var21 - var19 >> 3);
                            var7 += var8;
                            var15 = var7 >> 8;
                            --var17;
                        } while (var17 > 0);
                    }

                    var17 = var6 - var5 & 7;
                    if (var17 > 0) {
                        do {
                            if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                                var0[var4] = ((var3 & 16711935) * var15 & -16711936) + ((var3 & '\uff00') * var15 & 16711680) >> 8;
                            }

                            ++var4;
                            var2 += var16;
                            --var17;
                        } while (var17 > 0);
                    }
                }
            }

        }
    }

     final void method319(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18) {
        int[] var19 = fieldAs.a(var18, (byte) -105);
        int var20;
        if (var19 == null) {
            var20 = fieldAs.v(var18, (byte) 110);
            method316(var0, var1, var2, var3, var4, var5, method320(var20, var6), method320(var20, var7), method320(var20, var8));
        } else {
            fieldI = fieldAs.b(var18, 993452920);
            fieldV = fieldAs.i(var18, 1953889613);
            var20 = var4 - var3;
            int var21 = var1 - var0;
            int var22 = var5 - var3;
            int var23 = var2 - var0;
            int var24 = var7 - var6;
            int var25 = var8 - var6;
            int var26 = 0;
            if (var1 != var0) {
                var26 = (var4 - var3 << 16) / (var1 - var0);
            }

            int var27 = 0;
            if (var2 != var1) {
                var27 = (var5 - var4 << 16) / (var2 - var1);
            }

            int var28 = 0;
            if (var2 != var0) {
                var28 = (var3 - var5 << 16) / (var0 - var2);
            }

            int var29 = var20 * var23 - var22 * var21;
            if (var29 != 0) {
                int var30 = (var24 * var23 - var25 * var21 << 9) / var29;
                int var31 = (var25 * var20 - var24 * var22 << 9) / var29;
                var10 = var9 - var10;
                var13 = var12 - var13;
                var16 = var15 - var16;
                var11 -= var9;
                var14 -= var12;
                var17 -= var15;
                int var32 = var11 * var12 - var14 * var9 << 14;
                int var33 = var14 * var15 - var17 * var12 << 5;
                int var34 = var17 * var9 - var11 * var15 << 5;
                int var35 = var10 * var12 - var13 * var9 << 14;
                int var36 = var13 * var15 - var16 * var12 << 5;
                int var37 = var16 * var9 - var10 * var15 << 5;
                int var38 = var13 * var11 - var10 * var14 << 14;
                int var39 = var16 * var14 - var13 * var17 << 5;
                int var40 = var10 * var17 - var16 * var11 << 5;
                int var41;
                if (var0 <= var1 && var0 <= var2) {
                    if (var0 < fieldG) {
                        if (var1 > fieldG) {
                            var1 = fieldG;
                        }

                        if (var2 > fieldG) {
                            var2 = fieldG;
                        }

                        var6 = (var6 << 9) - var30 * var3 + var30;
                        if (var1 < var2) {
                            var5 = var3 <<= 16;
                            if (var0 < 0) {
                                var5 -= var28 * var0;
                                var3 -= var26 * var0;
                                var6 -= var31 * var0;
                                var0 = 0;
                            }

                            var4 <<= 16;
                            if (var1 < 0) {
                                var4 -= var27 * var1;
                                var1 = 0;
                            }

                            var41 = var0 - screenCenterY;
                            var32 += var34 * var41;
                            var35 += var37 * var41;
                            var38 += var40 * var41;
                            if ((var0 == var1 || var28 >= var26) && (var0 != var1 || var28 <= var27)) {
                                var2 -= var1;
                                var1 -= var0;
                                var0 = fieldC[var0];

                                while (true) {
                                    --var1;
                                    if (var1 < 0) {
                                        while (true) {
                                            --var2;
                                            if (var2 < 0) {
                                                return;
                                            }

                                            method311(raster, var19, 0, 0, var0, var4 >> 16, var5 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                            var5 += var28;
                                            var4 += var27;
                                            var6 += var31;
                                            var0 += rasterWidth;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method311(raster, var19, 0, 0, var0, var3 >> 16, var5 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                    var5 += var28;
                                    var3 += var26;
                                    var6 += var31;
                                    var0 += rasterWidth;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            } else {
                                var2 -= var1;
                                var1 -= var0;
                                var0 = fieldC[var0];

                                while (true) {
                                    --var1;
                                    if (var1 < 0) {
                                        while (true) {
                                            --var2;
                                            if (var2 < 0) {
                                                return;
                                            }

                                            method311(raster, var19, 0, 0, var0, var5 >> 16, var4 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                            var5 += var28;
                                            var4 += var27;
                                            var6 += var31;
                                            var0 += rasterWidth;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method311(raster, var19, 0, 0, var0, var5 >> 16, var3 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                    var5 += var28;
                                    var3 += var26;
                                    var6 += var31;
                                    var0 += rasterWidth;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            }
                        } else {
                            var4 = var3 <<= 16;
                            if (var0 < 0) {
                                var4 -= var28 * var0;
                                var3 -= var26 * var0;
                                var6 -= var31 * var0;
                                var0 = 0;
                            }

                            var5 <<= 16;
                            if (var2 < 0) {
                                var5 -= var27 * var2;
                                var2 = 0;
                            }

                            var41 = var0 - screenCenterY;
                            var32 += var34 * var41;
                            var35 += var37 * var41;
                            var38 += var40 * var41;
                            if ((var0 == var2 || var28 >= var26) && (var0 != var2 || var27 <= var26)) {
                                var1 -= var2;
                                var2 -= var0;
                                var0 = fieldC[var0];

                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        while (true) {
                                            --var1;
                                            if (var1 < 0) {
                                                return;
                                            }

                                            method311(raster, var19, 0, 0, var0, var3 >> 16, var5 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                            var5 += var27;
                                            var3 += var26;
                                            var6 += var31;
                                            var0 += rasterWidth;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method311(raster, var19, 0, 0, var0, var3 >> 16, var4 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                    var4 += var28;
                                    var3 += var26;
                                    var6 += var31;
                                    var0 += rasterWidth;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            } else {
                                var1 -= var2;
                                var2 -= var0;
                                var0 = fieldC[var0];

                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        while (true) {
                                            --var1;
                                            if (var1 < 0) {
                                                return;
                                            }

                                            method311(raster, var19, 0, 0, var0, var5 >> 16, var3 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                            var5 += var27;
                                            var3 += var26;
                                            var6 += var31;
                                            var0 += rasterWidth;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method311(raster, var19, 0, 0, var0, var4 >> 16, var3 >> 16, var6, var30, var32, var35, var38, var33, var36, var39);
                                    var4 += var28;
                                    var3 += var26;
                                    var6 += var31;
                                    var0 += rasterWidth;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            }
                        }
                    }
                } else if (var1 <= var2) {
                    if (var1 < fieldG) {
                        if (var2 > fieldG) {
                            var2 = fieldG;
                        }

                        if (var0 > fieldG) {
                            var0 = fieldG;
                        }

                        var7 = (var7 << 9) - var30 * var4 + var30;
                        if (var2 < var0) {
                            var3 = var4 <<= 16;
                            if (var1 < 0) {
                                var3 -= var26 * var1;
                                var4 -= var27 * var1;
                                var7 -= var31 * var1;
                                var1 = 0;
                            }

                            var5 <<= 16;
                            if (var2 < 0) {
                                var5 -= var28 * var2;
                                var2 = 0;
                            }

                            var41 = var1 - screenCenterY;
                            var32 += var34 * var41;
                            var35 += var37 * var41;
                            var38 += var40 * var41;
                            if ((var1 == var2 || var26 >= var27) && (var1 != var2 || var26 <= var28)) {
                                var0 -= var2;
                                var2 -= var1;
                                var1 = fieldC[var1];

                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        while (true) {
                                            --var0;
                                            if (var0 < 0) {
                                                return;
                                            }

                                            method311(raster, var19, 0, 0, var1, var5 >> 16, var3 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                            var3 += var26;
                                            var5 += var28;
                                            var7 += var31;
                                            var1 += rasterWidth;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method311(raster, var19, 0, 0, var1, var4 >> 16, var3 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                    var3 += var26;
                                    var4 += var27;
                                    var7 += var31;
                                    var1 += rasterWidth;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            } else {
                                var0 -= var2;
                                var2 -= var1;
                                var1 = fieldC[var1];

                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        while (true) {
                                            --var0;
                                            if (var0 < 0) {
                                                return;
                                            }

                                            method311(raster, var19, 0, 0, var1, var3 >> 16, var5 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                            var3 += var26;
                                            var5 += var28;
                                            var7 += var31;
                                            var1 += rasterWidth;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method311(raster, var19, 0, 0, var1, var3 >> 16, var4 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                    var3 += var26;
                                    var4 += var27;
                                    var7 += var31;
                                    var1 += rasterWidth;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            }
                        } else {
                            var5 = var4 <<= 16;
                            if (var1 < 0) {
                                var5 -= var26 * var1;
                                var4 -= var27 * var1;
                                var7 -= var31 * var1;
                                var1 = 0;
                            }

                            var3 <<= 16;
                            if (var0 < 0) {
                                var3 -= var28 * var0;
                                var0 = 0;
                            }

                            var41 = var1 - screenCenterY;
                            var32 += var34 * var41;
                            var35 += var37 * var41;
                            var38 += var40 * var41;
                            if (var26 < var27) {
                                var2 -= var0;
                                var0 -= var1;
                                var1 = fieldC[var1];

                                while (true) {
                                    --var0;
                                    if (var0 < 0) {
                                        while (true) {
                                            --var2;
                                            if (var2 < 0) {
                                                return;
                                            }

                                            method311(raster, var19, 0, 0, var1, var3 >> 16, var4 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                            var3 += var28;
                                            var4 += var27;
                                            var7 += var31;
                                            var1 += rasterWidth;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method311(raster, var19, 0, 0, var1, var5 >> 16, var4 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                    var5 += var26;
                                    var4 += var27;
                                    var7 += var31;
                                    var1 += rasterWidth;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            } else {
                                var2 -= var0;
                                var0 -= var1;
                                var1 = fieldC[var1];

                                while (true) {
                                    --var0;
                                    if (var0 < 0) {
                                        while (true) {
                                            --var2;
                                            if (var2 < 0) {
                                                return;
                                            }

                                            method311(raster, var19, 0, 0, var1, var4 >> 16, var3 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                            var3 += var28;
                                            var4 += var27;
                                            var7 += var31;
                                            var1 += rasterWidth;
                                            var32 += var34;
                                            var35 += var37;
                                            var38 += var40;
                                        }
                                    }

                                    method311(raster, var19, 0, 0, var1, var4 >> 16, var5 >> 16, var7, var30, var32, var35, var38, var33, var36, var39);
                                    var5 += var26;
                                    var4 += var27;
                                    var7 += var31;
                                    var1 += rasterWidth;
                                    var32 += var34;
                                    var35 += var37;
                                    var38 += var40;
                                }
                            }
                        }
                    }
                } else if (var2 < fieldG) {
                    if (var0 > fieldG) {
                        var0 = fieldG;
                    }

                    if (var1 > fieldG) {
                        var1 = fieldG;
                    }

                    var8 = (var8 << 9) - var30 * var5 + var30;
                    if (var0 < var1) {
                        var4 = var5 <<= 16;
                        if (var2 < 0) {
                            var4 -= var27 * var2;
                            var5 -= var28 * var2;
                            var8 -= var31 * var2;
                            var2 = 0;
                        }

                        var3 <<= 16;
                        if (var0 < 0) {
                            var3 -= var26 * var0;
                            var0 = 0;
                        }

                        var41 = var2 - screenCenterY;
                        var32 += var34 * var41;
                        var35 += var37 * var41;
                        var38 += var40 * var41;
                        if (var27 < var28) {
                            var1 -= var0;
                            var0 -= var2;
                            var2 = fieldC[var2];

                            while (true) {
                                --var0;
                                if (var0 < 0) {
                                    while (true) {
                                        --var1;
                                        if (var1 < 0) {
                                            return;
                                        }

                                        method311(raster, var19, 0, 0, var2, var4 >> 16, var3 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                        var4 += var27;
                                        var3 += var26;
                                        var8 += var31;
                                        var2 += rasterWidth;
                                        var32 += var34;
                                        var35 += var37;
                                        var38 += var40;
                                    }
                                }

                                method311(raster, var19, 0, 0, var2, var4 >> 16, var5 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                var4 += var27;
                                var5 += var28;
                                var8 += var31;
                                var2 += rasterWidth;
                                var32 += var34;
                                var35 += var37;
                                var38 += var40;
                            }
                        } else {
                            var1 -= var0;
                            var0 -= var2;
                            var2 = fieldC[var2];

                            while (true) {
                                --var0;
                                if (var0 < 0) {
                                    while (true) {
                                        --var1;
                                        if (var1 < 0) {
                                            return;
                                        }

                                        method311(raster, var19, 0, 0, var2, var3 >> 16, var4 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                        var4 += var27;
                                        var3 += var26;
                                        var8 += var31;
                                        var2 += rasterWidth;
                                        var32 += var34;
                                        var35 += var37;
                                        var38 += var40;
                                    }
                                }

                                method311(raster, var19, 0, 0, var2, var5 >> 16, var4 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                var4 += var27;
                                var5 += var28;
                                var8 += var31;
                                var2 += rasterWidth;
                                var32 += var34;
                                var35 += var37;
                                var38 += var40;
                            }
                        }
                    } else {
                        var3 = var5 <<= 16;
                        if (var2 < 0) {
                            var3 -= var27 * var2;
                            var5 -= var28 * var2;
                            var8 -= var31 * var2;
                            var2 = 0;
                        }

                        var4 <<= 16;
                        if (var1 < 0) {
                            var4 -= var26 * var1;
                            var1 = 0;
                        }

                        var41 = var2 - screenCenterY;
                        var32 += var34 * var41;
                        var35 += var37 * var41;
                        var38 += var40 * var41;
                        if (var27 < var28) {
                            var0 -= var1;
                            var1 -= var2;
                            var2 = fieldC[var2];

                            while (true) {
                                --var1;
                                if (var1 < 0) {
                                    while (true) {
                                        --var0;
                                        if (var0 < 0) {
                                            return;
                                        }

                                        method311(raster, var19, 0, 0, var2, var4 >> 16, var5 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                        var4 += var26;
                                        var5 += var28;
                                        var8 += var31;
                                        var2 += rasterWidth;
                                        var32 += var34;
                                        var35 += var37;
                                        var38 += var40;
                                    }
                                }

                                method311(raster, var19, 0, 0, var2, var3 >> 16, var5 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                var3 += var27;
                                var5 += var28;
                                var8 += var31;
                                var2 += rasterWidth;
                                var32 += var34;
                                var35 += var37;
                                var38 += var40;
                            }
                        } else {
                            var0 -= var1;
                            var1 -= var2;
                            var2 = fieldC[var2];

                            while (true) {
                                --var1;
                                if (var1 < 0) {
                                    while (true) {
                                        --var0;
                                        if (var0 < 0) {
                                            return;
                                        }

                                        method311(raster, var19, 0, 0, var2, var5 >> 16, var4 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                        var4 += var26;
                                        var5 += var28;
                                        var8 += var31;
                                        var2 += rasterWidth;
                                        var32 += var34;
                                        var35 += var37;
                                        var38 += var40;
                                    }
                                }

                                method311(raster, var19, 0, 0, var2, var5 >> 16, var3 >> 16, var8, var30, var32, var35, var38, var33, var36, var39);
                                var3 += var27;
                                var5 += var28;
                                var8 += var31;
                                var2 += rasterWidth;
                                var32 += var34;
                                var35 += var37;
                                var38 += var40;
                            }
                        }
                    }
                }
            }
        }
    }

     final int method320(int var0, int var1) {
        var1 = var1 * (var0 & 127) >> 7;
        if (var1 < 2) {
            var1 = 2;
        } else if (var1 > 126) {
            var1 = 126;
        }

        return (var0 & '\uff80') + var1;
    }

     final void method321(int[] var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
        if (offscreen) {
            if (var5 > fieldJ) {
                var5 = fieldJ;
            }

            if (var4 < 0) {
                var4 = 0;
            }
        }

        if (var4 < var5) {
            var1 += var4;
            var6 += var7 * var4;
            int var8;
            int var9;
            int var10;
            if (fieldB) {
                var3 = var5 - var4 >> 2;
                var7 <<= 2;
                if (alpha == 0) {
                    if (var3 > 0) {
                        do {
                            var2 = fieldAn[var6 >> 8];
                            var6 += var7;
                            var0[var1++] = var2;
                            var0[var1++] = var2;
                            var0[var1++] = var2;
                            var0[var1++] = var2;
                            --var3;
                        } while (var3 > 0);
                    }

                    var3 = var5 - var4 & 3;
                    if (var3 > 0) {
                        var2 = fieldAn[var6 >> 8];

                        do {
                            var0[var1++] = var2;
                            --var3;
                        } while (var3 > 0);
                    }
                } else {
                    var8 = alpha;
                    var9 = 256 - alpha;
                    if (var3 > 0) {
                        do {
                            var2 = fieldAn[var6 >> 8];
                            var6 += var7;
                            var2 = ((var2 & 16711935) * var9 >> 8 & 16711935) + ((var2 & '\uff00') * var9 >> 8 & '\uff00');
                            var10 = var0[var1];
                            var0[var1++] = var2 + ((var10 & 16711935) * var8 >> 8 & 16711935) + ((var10 & '\uff00') * var8 >> 8 & '\uff00');
                            var10 = var0[var1];
                            var0[var1++] = var2 + ((var10 & 16711935) * var8 >> 8 & 16711935) + ((var10 & '\uff00') * var8 >> 8 & '\uff00');
                            var10 = var0[var1];
                            var0[var1++] = var2 + ((var10 & 16711935) * var8 >> 8 & 16711935) + ((var10 & '\uff00') * var8 >> 8 & '\uff00');
                            var10 = var0[var1];
                            var0[var1++] = var2 + ((var10 & 16711935) * var8 >> 8 & 16711935) + ((var10 & '\uff00') * var8 >> 8 & '\uff00');
                            --var3;
                        } while (var3 > 0);
                    }

                    var3 = var5 - var4 & 3;
                    if (var3 > 0) {
                        var2 = fieldAn[var6 >> 8];
                        var2 = ((var2 & 16711935) * var9 >> 8 & 16711935) + ((var2 & '\uff00') * var9 >> 8 & '\uff00');

                        do {
                            var10 = var0[var1];
                            var0[var1++] = var2 + ((var10 & 16711935) * var8 >> 8 & 16711935) + ((var10 & '\uff00') * var8 >> 8 & '\uff00');
                            --var3;
                        } while (var3 > 0);
                    }
                }

            } else {
                var3 = var5 - var4;
                if (alpha == 0) {
                    do {
                        var0[var1++] = fieldAn[var6 >> 8];
                        var6 += var7;
                        --var3;
                    } while (var3 > 0);
                } else {
                    var8 = alpha;
                    var9 = 256 - alpha;

                    do {
                        var2 = fieldAn[var6 >> 8];
                        var6 += var7;
                        var2 = ((var2 & 16711935) * var9 >> 8 & 16711935) + ((var2 & '\uff00') * var9 >> 8 & '\uff00');
                        var10 = var0[var1];
                        var0[var1++] = var2 + ((var10 & 16711935) * var8 >> 8 & 16711935) + ((var10 & '\uff00') * var8 >> 8 & '\uff00');
                        --var3;
                    } while (var3 > 0);
                }

            }
        }
    }

    public  final void fillTriangle(int y1, int y2, int y3, int x1, int x2, int x3, int rgb) {
        int var7 = 0;
        if (y2 != y1) {
            var7 = (x2 - x1 << 16) / (y2 - y1);
        }

        int var8 = 0;
        if (y3 != y2) {
            var8 = (x3 - x2 << 16) / (y3 - y2);
        }

        int var9 = 0;
        if (y3 != y1) {
            var9 = (x1 - x3 << 16) / (y1 - y3);
        }

        if (y1 <= y2 && y1 <= y3) {
            if (y1 < fieldG) {
                if (y2 > fieldG) {
                    y2 = fieldG;
                }

                if (y3 > fieldG) {
                    y3 = fieldG;
                }

                if (y2 < y3) {
                    x3 = x1 <<= 16;
                    if (y1 < 0) {
                        x3 -= var9 * y1;
                        x1 -= var7 * y1;
                        y1 = 0;
                    }

                    x2 <<= 16;
                    if (y2 < 0) {
                        x2 -= var8 * y2;
                        y2 = 0;
                    }

                    if ((y1 == y2 || var9 >= var7) && (y1 != y2 || var9 <= var8)) {
                        y3 -= y2;
                        y2 -= y1;
                        y1 = fieldC[y1];

                        while (true) {
                            --y2;
                            if (y2 < 0) {
                                while (true) {
                                    --y3;
                                    if (y3 < 0) {
                                        return;
                                    }

                                    method317(raster, y1, rgb, 0, x2 >> 16, x3 >> 16);
                                    x3 += var9;
                                    x2 += var8;
                                    y1 += rasterWidth;
                                }
                            }

                            method317(raster, y1, rgb, 0, x1 >> 16, x3 >> 16);
                            x3 += var9;
                            x1 += var7;
                            y1 += rasterWidth;
                        }
                    } else {
                        y3 -= y2;
                        y2 -= y1;
                        y1 = fieldC[y1];

                        while (true) {
                            --y2;
                            if (y2 < 0) {
                                while (true) {
                                    --y3;
                                    if (y3 < 0) {
                                        return;
                                    }

                                    method317(raster, y1, rgb, 0, x3 >> 16, x2 >> 16);
                                    x3 += var9;
                                    x2 += var8;
                                    y1 += rasterWidth;
                                }
                            }

                            method317(raster, y1, rgb, 0, x3 >> 16, x1 >> 16);
                            x3 += var9;
                            x1 += var7;
                            y1 += rasterWidth;
                        }
                    }
                } else {
                    x2 = x1 <<= 16;
                    if (y1 < 0) {
                        x2 -= var9 * y1;
                        x1 -= var7 * y1;
                        y1 = 0;
                    }

                    x3 <<= 16;
                    if (y3 < 0) {
                        x3 -= var8 * y3;
                        y3 = 0;
                    }

                    if ((y1 == y3 || var9 >= var7) && (y1 != y3 || var8 <= var7)) {
                        y2 -= y3;
                        y3 -= y1;
                        y1 = fieldC[y1];

                        while (true) {
                            --y3;
                            if (y3 < 0) {
                                while (true) {
                                    --y2;
                                    if (y2 < 0) {
                                        return;
                                    }

                                    method317(raster, y1, rgb, 0, x1 >> 16, x3 >> 16);
                                    x3 += var8;
                                    x1 += var7;
                                    y1 += rasterWidth;
                                }
                            }

                            method317(raster, y1, rgb, 0, x1 >> 16, x2 >> 16);
                            x2 += var9;
                            x1 += var7;
                            y1 += rasterWidth;
                        }
                    } else {
                        y2 -= y3;
                        y3 -= y1;
                        y1 = fieldC[y1];

                        while (true) {
                            --y3;
                            if (y3 < 0) {
                                while (true) {
                                    --y2;
                                    if (y2 < 0) {
                                        return;
                                    }

                                    method317(raster, y1, rgb, 0, x3 >> 16, x1 >> 16);
                                    x3 += var8;
                                    x1 += var7;
                                    y1 += rasterWidth;
                                }
                            }

                            method317(raster, y1, rgb, 0, x2 >> 16, x1 >> 16);
                            x2 += var9;
                            x1 += var7;
                            y1 += rasterWidth;
                        }
                    }
                }
            }
        } else if (y2 <= y3) {
            if (y2 < fieldG) {
                if (y3 > fieldG) {
                    y3 = fieldG;
                }

                if (y1 > fieldG) {
                    y1 = fieldG;
                }

                if (y3 < y1) {
                    x1 = x2 <<= 16;
                    if (y2 < 0) {
                        x1 -= var7 * y2;
                        x2 -= var8 * y2;
                        y2 = 0;
                    }

                    x3 <<= 16;
                    if (y3 < 0) {
                        x3 -= var9 * y3;
                        y3 = 0;
                    }

                    if ((y2 == y3 || var7 >= var8) && (y2 != y3 || var7 <= var9)) {
                        y1 -= y3;
                        y3 -= y2;
                        y2 = fieldC[y2];

                        while (true) {
                            --y3;
                            if (y3 < 0) {
                                while (true) {
                                    --y1;
                                    if (y1 < 0) {
                                        return;
                                    }

                                    method317(raster, y2, rgb, 0, x3 >> 16, x1 >> 16);
                                    x1 += var7;
                                    x3 += var9;
                                    y2 += rasterWidth;
                                }
                            }

                            method317(raster, y2, rgb, 0, x2 >> 16, x1 >> 16);
                            x1 += var7;
                            x2 += var8;
                            y2 += rasterWidth;
                        }
                    } else {
                        y1 -= y3;
                        y3 -= y2;
                        y2 = fieldC[y2];

                        while (true) {
                            --y3;
                            if (y3 < 0) {
                                while (true) {
                                    --y1;
                                    if (y1 < 0) {
                                        return;
                                    }

                                    method317(raster, y2, rgb, 0, x1 >> 16, x3 >> 16);
                                    x1 += var7;
                                    x3 += var9;
                                    y2 += rasterWidth;
                                }
                            }

                            method317(raster, y2, rgb, 0, x1 >> 16, x2 >> 16);
                            x1 += var7;
                            x2 += var8;
                            y2 += rasterWidth;
                        }
                    }
                } else {
                    x3 = x2 <<= 16;
                    if (y2 < 0) {
                        x3 -= var7 * y2;
                        x2 -= var8 * y2;
                        y2 = 0;
                    }

                    x1 <<= 16;
                    if (y1 < 0) {
                        x1 -= var9 * y1;
                        y1 = 0;
                    }

                    if (var7 < var8) {
                        y3 -= y1;
                        y1 -= y2;
                        y2 = fieldC[y2];

                        while (true) {
                            --y1;
                            if (y1 < 0) {
                                while (true) {
                                    --y3;
                                    if (y3 < 0) {
                                        return;
                                    }

                                    method317(raster, y2, rgb, 0, x1 >> 16, x2 >> 16);
                                    x1 += var9;
                                    x2 += var8;
                                    y2 += rasterWidth;
                                }
                            }

                            method317(raster, y2, rgb, 0, x3 >> 16, x2 >> 16);
                            x3 += var7;
                            x2 += var8;
                            y2 += rasterWidth;
                        }
                    } else {
                        y3 -= y1;
                        y1 -= y2;
                        y2 = fieldC[y2];

                        while (true) {
                            --y1;
                            if (y1 < 0) {
                                while (true) {
                                    --y3;
                                    if (y3 < 0) {
                                        return;
                                    }

                                    method317(raster, y2, rgb, 0, x2 >> 16, x1 >> 16);
                                    x1 += var9;
                                    x2 += var8;
                                    y2 += rasterWidth;
                                }
                            }

                            method317(raster, y2, rgb, 0, x2 >> 16, x3 >> 16);
                            x3 += var7;
                            x2 += var8;
                            y2 += rasterWidth;
                        }
                    }
                }
            }
        } else if (y3 < fieldG) {
            if (y1 > fieldG) {
                y1 = fieldG;
            }

            if (y2 > fieldG) {
                y2 = fieldG;
            }

            if (y1 < y2) {
                x2 = x3 <<= 16;
                if (y3 < 0) {
                    x2 -= var8 * y3;
                    x3 -= var9 * y3;
                    y3 = 0;
                }

                x1 <<= 16;
                if (y1 < 0) {
                    x1 -= var7 * y1;
                    y1 = 0;
                }

                if (var8 < var9) {
                    y2 -= y1;
                    y1 -= y3;
                    y3 = fieldC[y3];

                    while (true) {
                        --y1;
                        if (y1 < 0) {
                            while (true) {
                                --y2;
                                if (y2 < 0) {
                                    return;
                                }

                                method317(raster, y3, rgb, 0, x2 >> 16, x1 >> 16);
                                x2 += var8;
                                x1 += var7;
                                y3 += rasterWidth;
                            }
                        }

                        method317(raster, y3, rgb, 0, x2 >> 16, x3 >> 16);
                        x2 += var8;
                        x3 += var9;
                        y3 += rasterWidth;
                    }
                } else {
                    y2 -= y1;
                    y1 -= y3;
                    y3 = fieldC[y3];

                    while (true) {
                        --y1;
                        if (y1 < 0) {
                            while (true) {
                                --y2;
                                if (y2 < 0) {
                                    return;
                                }

                                method317(raster, y3, rgb, 0, x1 >> 16, x2 >> 16);
                                x2 += var8;
                                x1 += var7;
                                y3 += rasterWidth;
                            }
                        }

                        method317(raster, y3, rgb, 0, x3 >> 16, x2 >> 16);
                        x2 += var8;
                        x3 += var9;
                        y3 += rasterWidth;
                    }
                }
            } else {
                x1 = x3 <<= 16;
                if (y3 < 0) {
                    x1 -= var8 * y3;
                    x3 -= var9 * y3;
                    y3 = 0;
                }

                x2 <<= 16;
                if (y2 < 0) {
                    x2 -= var7 * y2;
                    y2 = 0;
                }

                if (var8 < var9) {
                    y1 -= y2;
                    y2 -= y3;
                    y3 = fieldC[y3];

                    while (true) {
                        --y2;
                        if (y2 < 0) {
                            while (true) {
                                --y1;
                                if (y1 < 0) {
                                    return;
                                }

                                method317(raster, y3, rgb, 0, x2 >> 16, x3 >> 16);
                                x2 += var7;
                                x3 += var9;
                                y3 += rasterWidth;
                            }
                        }

                        method317(raster, y3, rgb, 0, x1 >> 16, x3 >> 16);
                        x1 += var8;
                        x3 += var9;
                        y3 += rasterWidth;
                    }
                } else {
                    y1 -= y2;
                    y2 -= y3;
                    y3 = fieldC[y3];

                    while (true) {
                        --y2;
                        if (y2 < 0) {
                            while (true) {
                                --y1;
                                if (y1 < 0) {
                                    return;
                                }

                                method317(raster, y3, rgb, 0, x3 >> 16, x2 >> 16);
                                x2 += var7;
                                x3 += var9;
                                y3 += rasterWidth;
                            }
                        }

                        method317(raster, y3, rgb, 0, x3 >> 16, x1 >> 16);
                        x1 += var8;
                        x3 += var9;
                        y3 += rasterWidth;
                    }
                }
            }
        }
    }

    public  final void method323(int var0, int var1) {
        int var2 = fieldC[0];
        int var3 = var2 / rasterWidth;
        int var4 = var2 - var3 * rasterWidth;
        screenCenterX = var0 - var4;
        screenCenterY = var1 - var3;
        fieldY = -screenCenterX;
        fieldP = fieldJ - screenCenterX;
        fieldR = -screenCenterY;
        fieldZ = fieldG - screenCenterY;
    }

    public  final void method324() {
        screenCenterX = fieldJ / 2;
        screenCenterY = fieldG / 2;
        fieldY = -screenCenterX;
        fieldP = fieldJ - screenCenterX;
        fieldR = -screenCenterY;
        fieldZ = fieldG - screenCenterY;
    }

     final void method325(double var0, int var2, int var3) {
        var0 += Math.random() * 0.03D - 0.015D;
        int var4 = var2 * 128;

        for (int var5 = var2; var5 < var3; ++var5) {
            double var6 = (double) (var5 >> 3) / 64.0D + 0.0078125D;
            double var8 = (double) (var5 & 7) / 8.0D + 0.0625D;

            for (int var10 = 0; var10 < 128; ++var10) {
                double var11 = (double) var10 / 128.0D;
                double var13 = var11;
                double var15 = var11;
                double var17 = var11;
                if (var8 != 0.0D) {
                    double var19;
                    if (var11 < 0.5D) {
                        var19 = var11 * (1.0D + var8);
                    } else {
                        var19 = var11 + var8 - var11 * var8;
                    }

                    double var21 = 2.0D * var11 - var19;
                    double var23 = var6 + 0.3333333333333333D;
                    if (var23 > 1.0D) {
                        --var23;
                    }

                    double var27 = var6 - 0.3333333333333333D;
                    if (var27 < 0.0D) {
                        ++var27;
                    }

                    if (6.0D * var23 < 1.0D) {
                        var13 = var21 + (var19 - var21) * 6.0D * var23;
                    } else if (2.0D * var23 < 1.0D) {
                        var13 = var19;
                    } else if (3.0D * var23 < 2.0D) {
                        var13 = var21 + (var19 - var21) * (0.6666666666666666D - var23) * 6.0D;
                    } else {
                        var13 = var21;
                    }

                    if (6.0D * var6 < 1.0D) {
                        var15 = var21 + (var19 - var21) * 6.0D * var6;
                    } else if (2.0D * var6 < 1.0D) {
                        var15 = var19;
                    } else if (3.0D * var6 < 2.0D) {
                        var15 = var21 + (var19 - var21) * (0.6666666666666666D - var6) * 6.0D;
                    } else {
                        var15 = var21;
                    }

                    if (6.0D * var27 < 1.0D) {
                        var17 = var21 + (var19 - var21) * 6.0D * var27;
                    } else if (2.0D * var27 < 1.0D) {
                        var17 = var19;
                    } else if (3.0D * var27 < 2.0D) {
                        var17 = var21 + (var19 - var21) * (0.6666666666666666D - var27) * 6.0D;
                    } else {
                        var17 = var21;
                    }
                }

                int var30 = (int) (var13 * 256.0D);
                int var20 = (int) (var15 * 256.0D);
                int var29 = (int) (var17 * 256.0D);
                int var22 = (var30 << 16) + (var20 << 8) + var29;
                var22 = method314(var22, var0);
                if (var22 == 0) {
                    var22 = 1;
                }

                fieldAn[var4++] = var22;
            }
        }

    }
}
