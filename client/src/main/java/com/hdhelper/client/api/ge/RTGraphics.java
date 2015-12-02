package com.hdhelper.client.api.ge;

import java.awt.*;
import java.awt.image.*;
import java.util.Hashtable;

public class RTGraphics {

    public int[] raster;
    public int rasterWidth;
    public int rasterHeight;

    int viewportX    = 0;
    int viewportY    = 0;
    int viewportMaxX = 0;
    int viewportMaxY = 0;

    BufferedImage img;

    RTGraphics() {
    }

    public RTGraphics(RTGraphics from) {
        copy(from, this, false);
    }

    public RTGraphics(int w, int h) {
        this(new int[w * h], w, h);
    }

    public RTGraphics(int[] raster, int w, int h) {
        setRaster(raster, w, h);
    }

    // Sets the graphics object in which this graphic will render to
    public void setGraphics(RTGraphics g) {
        setRaster(g.raster, g.rasterWidth, g.rasterHeight);
        this.img = g.img;
    }

    public void copy(RTGraphics dest, boolean cloneRaster) {
        copy(this, dest, cloneRaster);
    }

    public static void copy(RTGraphics from, RTGraphics to, boolean cloneRaster) {
        to.raster = cloneRaster ? from.raster.clone() : from.raster;
        to.rasterWidth  = from.rasterWidth;
        to.rasterHeight = from.rasterHeight;
        to.viewportX    = from.viewportX;
        to.viewportY    = from.viewportY;
        to.viewportMaxX = from.viewportMaxX;
        to.viewportMaxY = from.viewportMaxY;
    }
    
    public void setRaster(int[] var0, int var1, int var2) {
        flush(); //Flush existing resources
        raster = var0;
        rasterWidth = var1;
        rasterHeight = var2;
        setViewport(0, 0, var1, var2);
    }

    // Release any held resources
    public void flush() {
        raster = null;
    }

    public void noClip() {
        viewportX = 0;
        viewportY = 0;
        viewportMaxX = rasterWidth;
        viewportMaxY = rasterHeight;
    }

    public void setViewport(int var0, int var1, int var2, int var3) {
        if (var0 < 0) {
            var0 = 0;
        }

        if (var1 < 0) {
            var1 = 0;
        }

        if (var2 > rasterWidth) {
            var2 = rasterWidth;
        }

        if (var3 > rasterHeight) {
            var3 = rasterHeight;
        }

        viewportX = var0;
        viewportY = var1;
        viewportMaxX = var2;
        viewportMaxY = var3;
    }

    public void setInnerViewport(int var0, int var1, int var2, int var3) {
        if (viewportX < var0) {
            viewportX = var0;
        }

        if (viewportY < var1) {
            viewportY = var1;
        }

        if (viewportMaxX > var2) {
            viewportMaxX = var2;
        }

        if (viewportMaxY > var3) {
            viewportMaxY = var3;
        }
    }

    public void method233(int[] var0) {
        var0[0] = viewportX;
        var0[1] = viewportY;
        var0[2] = viewportMaxX;
        var0[3] = viewportMaxY;
    }

    public void fillRectangle(int x, int y, int w, int h, int rgb, int alpha) {
        if (x < viewportX) {
            w -= viewportX - x;
            x = viewportX;
        }

        if (y < viewportY) {
            h -= viewportY - y;
            y = viewportY;
        }

        if (x + w > viewportMaxX) {
            w = viewportMaxX - x;
        }

        if (y + h > viewportMaxY) {
            h = viewportMaxY - y;
        }

        rgb = ((rgb & 16711935) * alpha >> 8 & 16711935) + ((rgb & '\uff00') * alpha >> 8 & '\uff00');
        int var6 = 256 - alpha;
        int var7 = rasterWidth - w;
        int var8 = x + y * rasterWidth;

        for (int var9 = 0; var9 < h; ++var9) {
            for (int var10 = -w; var10 < 0; ++var10) {
                int var11 = raster[var8];
                var11 = ((var11 & 16711935) * var6 >> 8 & 16711935) + ((var11 & '\uff00') * var6 >> 8 & '\uff00');
                raster[var8++] = rgb + var11;
            }

            var8 += var7;
        }

    }

    public void fillRectangle(int x, int y, int w, int h, int rgb) {
        if (x < viewportX) {
            w -= viewportX - x;
            x = viewportX;
        }

        if (y < viewportY) {
            h -= viewportY - y;
            y = viewportY;
        }

        if (x + w > viewportMaxX) {
            w = viewportMaxX - x;
        }

        if (y + h > viewportMaxY) {
            h = viewportMaxY - y;
        }

        int var5 = rasterWidth - w;
        int var6 = x + y * rasterWidth;

        for (int var7 = -h; var7 < 0; ++var7) {
            for (int var8 = -w; var8 < 0; ++var8) {
                raster[var6++] = rgb;
            }

            var6 += var5;
        }

    }

    public void fillGradient(int x, int y, int w, int h, int rgbFrom, int rgbTo) {
        int var6 = 0;
        int var7 = 65536 / h;
        if (x < viewportX) {
            w -= viewportX - x;
            x = viewportX;
        }


        if (y < viewportY) {
            var6 += (viewportY - y) * var7;
            h -= viewportY - y;
            y = viewportY;
        }

        if (x + w > viewportMaxX) {
            w = viewportMaxX - x;
        }

        if (y + h > viewportMaxY) {
            h = viewportMaxY - y;
        }

        int var8 = rasterWidth - w;
        int var9 = x + y * rasterWidth;

        for (int var10 = -h; var10 < 0; ++var10) {
            int var11 = 65536 - var6 >> 8;
            int var12 = var6 >> 8;
            int var13 = ((rgbFrom & 16711935) * var11 + (rgbTo & 16711935) * var12 & -16711936) + ((rgbFrom & '\uff00') * var11 + (rgbTo & '\uff00') * var12 & 16711680) >>> 8;

            for (int var14 = -w; var14 < 0; ++var14) {
                raster[var9++] = var13;
            }

            var9 += var8;
            var6 += var7;
        }

    }

    public void drawRectangle(int x, int var1, int var2, int var3, int var4) {
        drawHorizontalLine(x, var1, var2, var4);
        drawHorizontalLine(x, var1 + var3 - 1, var2, var4);
        drawVerticalLine(x, var1, var3, var4);
        drawVerticalLine(x + var2 - 1, var1, var3, var4);
    }

    public void drawHorizontalLine(int var0, int var1, int var2, int var3) {
        if (var1 >= viewportY && var1 < viewportMaxY) {
            if (var0 < viewportX) {
                var2 -= viewportX - var0;
                var0 = viewportX;
            }

            if (var0 + var2 > viewportMaxX) {
                var2 = viewportMaxX - var0;
            }

            int var4 = var0 + var1 * rasterWidth;

            for (int var5 = 0; var5 < var2; ++var5) {
                raster[var4 + var5] = var3;
            }

        }
    }

    void drawHorizontalLine(int x, int y, int w, int rgb, int alpha) {
        if (y >= viewportY && y < viewportMaxY) {
            if (x < viewportX) {
                w -= viewportX - x;
                x = viewportX;
            }

            if (x + w > viewportMaxX) {
                w = viewportMaxX - x;
            }

            int var5 = 256 - alpha;
            int var6 = (rgb >> 16 & 255) * alpha;
            int var7 = (rgb >> 8 & 255) * alpha;
            int var8 = (rgb & 255) * alpha;
            int var12 = x + y * rasterWidth;

            for (int var13 = 0; var13 < w; ++var13) {
                int var9 = (raster[var12] >> 16 & 255) * var5;
                int var10 = (raster[var12] >> 8 & 255) * var5;
                int var11 = (raster[var12] & 255) * var5;
                int var14 = (var6 + var9 >> 8 << 16) + (var7 + var10 >> 8 << 8) + (var8 + var11 >> 8);
                raster[var12++] = var14;
            }

        }
    }

    public void drawVerticalLine(int var0, int var1, int var2, int var3) {
        if (var0 >= viewportX && var0 < viewportMaxX) {
            if (var1 < viewportY) {
                var2 -= viewportY - var1;
                var1 = viewportY;
            }

            if (var1 + var2 > viewportMaxY) {
                var2 = viewportMaxY - var1;
            }

            int var4 = var0 + var1 * rasterWidth;

            for (int var5 = 0; var5 < var2; ++var5) {
                raster[var4 + var5 * rasterWidth] = var3;
            }

        }
    }

    public void drawLine(Point A, Point B, int color) {
        drawLine(A.x, A.y, B.x, B.y, color);
    }

    public void drawSquareDot(Point P, int color) {
        drawSquareDot(P.x, P.y, color);
    }

    public void drawSquareDot(int x, int y,int color) {
        drawSquareDot(x,y,2,color);
    }

    public void drawSquareDot(int x, int y, int size, int color) {
        fillRectangle(x - size / 2, y - size / 2, size * 2, size * 2, color);
    }

    public void drawLine(int x1, int y1, int x2, int y2, int rgb) {
        x2 -= x1;
        y2 -= y1;
        if (y2 == 0) {
            if (x2 >= 0) {
                drawHorizontalLine(x1, y1, x2 + 1, rgb);
            } else {
                drawHorizontalLine(x1 + x2, y1, -x2 + 1, rgb);
            }

        } else if (x2 == 0) {
            if (y2 >= 0) {
                drawVerticalLine(x1, y1, y2 + 1, rgb);
            } else {
                drawVerticalLine(x1, y1 + y2, -y2 + 1, rgb);
            }

        } else {
            if (x2 + y2 < 0) {
                x1 += x2;
                x2 = -x2;
                y1 += y2;
                y2 = -y2;
            }

            int var5;
            int var6;
            if (x2 > y2) {
                y1 <<= 16;
                y1 += '\u8000';
                y2 <<= 16;
                var5 = (int) Math.floor((double) y2 / (double) x2 + 0.5D);
                x2 += x1;
                if (x1 < viewportX) {
                    y1 += var5 * (viewportX - x1);
                    x1 = viewportX;
                }

                if (x2 >= viewportMaxX) {
                    x2 = viewportMaxX - 1;
                }

                while (x1 <= x2) {
                    var6 = y1 >> 16;
                    if (var6 >= viewportY && var6 < viewportMaxY) {
                        raster[x1 + var6 * rasterWidth] = rgb;
                    }

                    y1 += var5;
                    ++x1;
                }
            } else {
                x1 <<= 16;
                x1 += '\u8000';
                x2 <<= 16;
                var5 = (int) Math.floor((double) x2 / (double) y2 + 0.5D);
                y2 += y1;
                if (y1 < viewportY) {
                    x1 += var5 * (viewportY - y1);
                    y1 = viewportY;
                }

                if (y2 >= viewportMaxY) {
                    y2 = viewportMaxY - 1;
                }

                while (y1 <= y2) {
                    var6 = x1 >> 16;
                    if (var6 >= viewportX && var6 < viewportMaxX) {
                        raster[var6 + y1 * rasterWidth] = rgb;
                    }

                    x1 += var5;
                    ++y1;
                }
            }

        }
    }

    public void copyViewport(int[] var0) {
        viewportX = var0[0];
        viewportY = var0[1];
        viewportMaxX = var0[2];
        viewportMaxY = var0[3];
    }

    public void drawVerticalLine(int var0, int var1, int var2, int var3, int var4) {
        if (var0 >= viewportX && var0 < viewportMaxX) {
            if (var1 < viewportY) {
                var2 -= viewportY - var1;
                var1 = viewportY;
            }

            if (var1 + var2 > viewportMaxY) {
                var2 = viewportMaxY - var1;
            }

            int var5 = 256 - var4;
            int var6 = (var3 >> 16 & 255) * var4;
            int var7 = (var3 >> 8 & 255) * var4;
            int var8 = (var3 & 255) * var4;
            int var12 = var0 + var1 * rasterWidth;

            for (int var13 = 0; var13 < var2; ++var13) {
                int var9 = (raster[var12] >> 16 & 255) * var5;
                int var10 = (raster[var12] >> 8 & 255) * var5;
                int var11 = (raster[var12] & 255) * var5;
                int var14 = (var6 + var9 >> 8 << 16) + (var7 + var10 >> 8 << 8) + (var8 + var11 >> 8);
                raster[var12] = var14;
                var12 += rasterWidth;
            }

        }
    }

    public void clear() {
        int var0 = 0;

        int var1;
        for (var1 = rasterWidth * rasterHeight - 7; var0 < var1; raster[var0++] = 0) {
            raster[var0++] = 0;
            raster[var0++] = 0;
            raster[var0++] = 0;
            raster[var0++] = 0;
            raster[var0++] = 0;
            raster[var0++] = 0;
            raster[var0++] = 0;
        }

        for (var1 += 7; var0 < var1; raster[var0++] = 0) {
            ;
        }

    }

    public void drawRectangle(int var0, int var1, int var2, int var3, int var4, int var5) {
        drawHorizontalLine(var0, var1, var2, var4, var5);
        drawHorizontalLine(var0, var1 + var3 - 1, var2, var4, var5);
        if (var3 >= 3) {
            drawVerticalLine(var0, var1 + 1, var3 - 2, var4, var5);
            drawVerticalLine(var0 + var2 - 1, var1 + 1, var3 - 2, var4, var5);
        }

    }

    public void method246(int var0, int var1, int var2, int[] var3, int[] var4) {
        int var5 = var0 + var1 * rasterWidth;

        for (var1 = 0; var1 < var3.length; ++var1) {
            int var6 = var5 + var3[var1];

            for (var0 = -var4[var1]; var0 < 0; ++var0) {
                raster[var6++] = var2;
            }

            var5 += rasterWidth;
        }
    }

   /* public void fillCircle(int x, int y, int radius, int color, int alpha) {
        int lightness = 256 - alpha;
        int red = (color >> 16 & 0xff) * alpha;
        int green = (color >> 8 & 0xff) * alpha;
        int blue = (color & 0xff) * alpha;
        int y_min = y - radius;
        if (y_min < 0)
            y_min = 0;
        int y_max = y + radius;
        if (y_max >= rasterHeight)
            y_max = map_height - 1;
        for (int y0 = y_min; y0 <= y_max; y0++) {
            int deltaY = y0 - y;
            int x_length = (int) Math.sqrt(radius * radius - deltaY * deltaY); //R^2 - Y^2 = X^2
            int min_x = x - x_length;
            if (min_x < 0) min_x = 0;
            int max_x = x + x_length;
            if (max_x >= map_with)
                max_x = map_with - 1;
            int index = min_x + y0 * map_with; // i = y * w + x
            for (int x0 = min_x; x0 <= max_x; x0++) {
                int existing_red = (cluster[index] >> 16 & 0xff) * lightness;
                int existing_green = (cluster[index] >> 8 & 0xff) * lightness;
                int existing_blue = (cluster[index] & 0xff) * lightness;
                int new_rgb = ((red + existing_red >> 8) << 16) + ((green + existing_green >> 8) << 8) + (blue + existing_blue >> 8);
                cluster[index++] = new_rgb;
            }
        }
    }
*/



    public BufferedImage crate() {
        if(img == null) {
            img = create(raster,rasterWidth,rasterHeight);
        }
        return img;
    }

    // Creates a compatible java graphics object that will render itself onto the existing
    // raster/RTGraphics
    public Graphics2D getGraphics() {
        Image img = crate();
        if(img == null) return null;
        return (Graphics2D) img.getGraphics();
    }


    public static BufferedImage create(int[] raster, int w, int h) {
        //TODO verify
        DataBufferInt var5 = new DataBufferInt(raster,raster.length);
        DirectColorModel var6 = new DirectColorModel(32, 16711680, '\uff00', 255);
        WritableRaster var7 = Raster.createWritableRaster(var6.createCompatibleSampleModel(w, h), var5, null);
        return new BufferedImage(var6, var7, false, new Hashtable());
    }



    public int size() {
        if(raster == null) return 0;
        return raster.length;
    }

}
