package com.hdhelper.client.api.ge;

public final class RTFontImpl extends RTFont {
    
    public RTFontImpl(RTGlyphVector v) {
        super(v);
    }

    @Override
    final void y(byte[] flags, int x, int y, int w, int h, int color) {
        int var7 = x + y * rasterWidth;
        int var8 = rasterWidth - w;
        int var9 = 0;
        int var10 = 0;
        int var11;
        if (y < viewportY) {
            var11 = viewportY - y;
            h -= var11;
            y = viewportY;
            var10 += var11 * w;
            var7 += var11 * rasterWidth;
        }

        if (y + h > viewportMaxY) {
            h -= y + h - viewportMaxY;
        }

        if (x < viewportX) {
            var11 = viewportX - x;
            w -= var11;
            x = viewportX;
            var10 += var11;
            var7 += var11;
            var9 += var11;
            var8 += var11;
        }

        if (x + w > viewportMaxX) {
            var11 = x + w - viewportMaxX;
            w -= var11;
            var9 += var11;
            var8 += var11;
        }

        if (w > 0 && h > 0) {
            method34(raster, flags, color, var10, var7, w, h, var8, var9);
        }
    }

    @Override
    final void p(byte[] var1, int var2, int var3, int var4, int var5, int var6, int var7) {
        int var8 = var2 + var3 * rasterWidth;
        int var9 = rasterWidth - var4;
        int var10 = 0;
        int var11 = 0;
        int var12;
        if (var3 < viewportY) {
            var12 = viewportY - var3;
            var5 -= var12;
            var3 = viewportY;
            var11 += var12 * var4;
            var8 += var12 * rasterWidth;
        }

        if (var3 + var5 > viewportMaxY) {
            var5 -= var3 + var5 - viewportMaxY;
        }

        if (var2 < viewportX) {
            var12 = viewportX - var2;
            var4 -= var12;
            var2 = viewportX;
            var11 += var12;
            var8 += var12;
            var10 += var12;
            var9 += var12;
        }

        if (var2 + var4 > viewportMaxX) {
            var12 = var2 + var4 - viewportMaxX;
            var4 -= var12;
            var10 += var12;
            var9 += var12;
        }

        if (var4 > 0 && var5 > 0) {
            method35(raster, var1, var6, var11, var8, var4, var5, var9, var10, var7);
        }
    }
}