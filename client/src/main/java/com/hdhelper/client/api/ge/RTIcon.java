package com.hdhelper.client.api.ge;

public final class RTIcon extends RTGraphics {

    public int insetY;
    public int insetX;

    public int[] palette;
    public byte[] indices;

    public int maxY;
    public int maxX;

    public int height;
    public int width;

    static void method275(int[] var0, byte[] var1, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8) {
        int var9 = -(var5 >> 2);
        var5 = -(var5 & 3);

        for (int var10 = -var6; var10 < 0; ++var10) {
            int var11;
            byte var12;
            for (var11 = var9; var11 < 0; ++var11) {
                var12 = var1[var3++];
                if (var12 != 0) {
                    var0[var4++] = var2[var12 & 255];
                } else {
                    ++var4;
                }

                var12 = var1[var3++];
                if (var12 != 0) {
                    var0[var4++] = var2[var12 & 255];
                } else {
                    ++var4;
                }

                var12 = var1[var3++];
                if (var12 != 0) {
                    var0[var4++] = var2[var12 & 255];
                } else {
                    ++var4;
                }

                var12 = var1[var3++];
                if (var12 != 0) {
                    var0[var4++] = var2[var12 & 255];
                } else {
                    ++var4;
                }
            }

            for (var11 = var5; var11 < 0; ++var11) {
                var12 = var1[var3++];
                if (var12 != 0) {
                    var0[var4++] = var2[var12 & 255];
                } else {
                    ++var4;
                }
            }

            var4 += var7;
            var3 += var8;
        }

    }

    public void translate(int r, int g, int b) {
        for (int var4 = 0; var4 < this.palette.length; ++var4) {
            int var5 = this.palette[var4] >> 16 & 255;
            var5 += r;
            if (var5 < 0) {
                var5 = 0;
            } else if (var5 > 255) {
                var5 = 255;
            }

            int var6 = this.palette[var4] >> 8 & 255;
            var6 += g;
            if (var6 < 0) {
                var6 = 0;
            } else if (var6 > 255) {
                var6 = 255;
            }

            int var7 = this.palette[var4] & 255;
            var7 += b;
            if (var7 < 0) {
                var7 = 0;
            } else if (var7 > 255) {
                var7 = 255;
            }

            this.palette[var4] = (var5 << 16) + (var6 << 8) + var7;
        }

    }

    public void crop() {
        if (this.maxX != this.width || this.maxY != this.height) {
            byte[] var1 = new byte[this.width * this.height];
            int var2 = 0;

            for (int var3 = 0; var3 < this.maxY; ++var3) {
                for (int var4 = 0; var4 < this.maxX; ++var4) {
                    var1[var4 + this.insetX + (var3 + this.insetY) * this.width] = this.indices[var2++];
                }
            }

            this.indices = var1;
            this.maxX = this.width;
            this.maxY = this.height;
            this.insetX = 0;
            this.insetY = 0;
        }
    }

    public void draw(int x, int y) {
        x += this.insetX;
        y += this.insetY;
        int var3 = x + y * rasterWidth;
        int var4 = 0;
        int var5 = this.maxY;
        int var6 = this.maxX;
        int var7 = rasterWidth - var6;
        int var8 = 0;
        int var9;
        if (y < viewportY) {
            var9 = viewportY - y;
            var5 -= var9;
            y = viewportY;
            var4 += var9 * var6;
            var3 += var9 * rasterWidth;
        }

        if (y + var5 > viewportMaxY) {
            var5 -= y + var5 - viewportMaxY;
        }

        if (x < viewportX) {
            var9 = viewportX - x;
            var6 -= var9;
            x = viewportX;
            var4 += var9;
            var3 += var9;
            var8 += var9;
            var7 += var9;
        }

        if (x + var6 > viewportMaxX) {
            var9 = x + var6 - viewportMaxX;
            var6 -= var9;
            var8 += var9;
            var7 += var9;
        }

        if (var6 > 0 && var5 > 0) {
            method275(raster, this.indices, this.palette, var4, var3, var6, var5, var7, var8);
        }
    }

}