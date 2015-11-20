package com.hdhelper.client.api.ge;

import com.hdhelper.agent.services.RSImage;

import java.awt.*;
import java.awt.image.PixelGrabber;

public final class RTImage extends RTGraphics {

    int[] pixels;

    int width;//TODO this was package private
    int height; //TODO this was  package private

    int maxX;
    int maxY;

    int insetX;
    int insetY;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public static RTImage create(RSImage src, boolean unsafe) {
        RTImage dest = new RTImage();
        dest.pixels = unsafe ? src.getPixels() : src.getPixels().clone();
        dest.width = src.getHeight();
        dest.height = src.getWidth();
        dest.insetX = src.getInsetX();
        dest.insetY = src.getInsetY();
        dest.maxX = src.getMaxX();
        dest.maxY = src.getMaxY();
        return dest;
    }

    RTImage() {}

    RTImage(int[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = this.maxX = width;
        this.height = this.maxY = height;
        this.insetX = 0;
        this.insetY = 0;
    }

    public RTImage(int width, int height) {
        this.pixels = new int[width * height];
        this.width = this.maxX = width;
        this.height = this.maxY = height;
        this.insetY = 0;
        this.insetX = 0;
    }

    public RTImage(Image var3) { //TODO should we provide an observer?
        try {
            this.width = var3.getWidth(null);
            this.height = var3.getHeight(null);
            this.maxX = this.width;
            this.maxY = this.height;
            this.insetX = 0;
            this.insetY = 0;
            this.pixels = new int[this.width * this.height];
            PixelGrabber var5 = new PixelGrabber(var3, 0, 0, this.width, this.height, this.pixels, 0, this.width);
            var5.grabPixels();
        } catch (InterruptedException ignored) {
            throw new Error(ignored);
        }
    }

    public RTImage(byte[] imageData, Component var2) {
        try {
            Image var3 = Toolkit.getDefaultToolkit().createImage(imageData);
            MediaTracker var4 = new MediaTracker(var2);
            var4.addImage(var3, 0);
            var4.waitForAll();
            this.width = var3.getWidth(var2);
            this.height = var3.getHeight(var2);
            this.maxX = this.width;
            this.maxY = this.height;
            this.insetX = 0;
            this.insetY = 0;
            this.pixels = new int[this.width * this.height];
            PixelGrabber var5 = new PixelGrabber(var3, 0, 0, this.width, this.height, this.pixels, 0, this.width);
            var5.grabPixels();
        } catch (InterruptedException ignored) {
        }
    }

    static void method360(int[] var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7) {
        for (int var8 = -var5; var8 < 0; ++var8) {
            int var9;
            for (var9 = var3 + var4 - 3; var3 < var9; var0[var3++] = var1[var2++]) {
                var0[var3++] = var1[var2++];
                var0[var3++] = var1[var2++];
                var0[var3++] = var1[var2++];
            }

            for (var9 += 3; var3 < var9; var0[var3++] = var1[var2++]) {
                ;
            }

            var3 += var6;
            var2 += var7;
        }

    }

    static void method361(int[] var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
        int var9 = -(var5 >> 2);
        var5 = -(var5 & 3);

        for (int var10 = -var6; var10 < 0; ++var10) {
            int var11;
            for (var11 = var9; var11 < 0; ++var11) {
                var2 = var1[var3++];
                if (var2 != 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }

                var2 = var1[var3++];
                if (var2 != 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }

                var2 = var1[var3++];
                if (var2 != 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }

                var2 = var1[var3++];
                if (var2 != 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }
            }

            for (var11 = var5; var11 < 0; ++var11) {
                var2 = var1[var3++];
                if (var2 != 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }
            }

            var4 += var7;
            var3 += var8;
        }

    }

    static void method362(int[] var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11) {
        int var12 = var3;

        for (int var13 = -var8; var13 < 0; ++var13) {
            int var14 = (var4 >> 16) * var11;

            for (int var15 = -var7; var15 < 0; ++var15) {
                var2 = var1[(var3 >> 16) + var14];
                if (var2 != 0) {
                    var0[var5++] = var2;
                } else {
                    ++var5;
                }

                var3 += var9;
            }

            var4 += var10;
            var3 = var12;
            var5 += var6;
        }

    }

    static void method363(int[] var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
        int var11 = 256 - var9;
        int var12 = (var10 & 16711935) * var11 & -16711936;
        int var13 = (var10 & '\uff00') * var11 & 16711680;
        var10 = (var12 | var13) >>> 8;

        for (int var14 = -var6; var14 < 0; ++var14) {
            for (int var15 = -var5; var15 < 0; ++var15) {
                var2 = var1[var3++];
                if (var2 != 0) {
                    var12 = (var2 & 16711935) * var9 & -16711936;
                    var13 = (var2 & '\uff00') * var9 & 16711680;
                    var0[var4++] = ((var12 | var13) >>> 8) + var10;
                } else {
                    ++var4;
                }
            }

            var4 += var7;
            var3 += var8;
        }

    }

    static void method364(int[] var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
        int var10 = 256 - var9;

        for (int var11 = -var6; var11 < 0; ++var11) {
            for (int var12 = -var5; var12 < 0; ++var12) {
                var2 = var1[var3++];
                if (var2 != 0) {
                    int var13 = var0[var4];
                    var0[var4++] = ((var2 & 16711935) * var9 + (var13 & 16711935) * var10 & -16711936) + ((var2 & '\uff00') * var9 + (var13 & '\uff00') * var10 & 16711680) >> 8;
                } else {
                    ++var4;
                }
            }

            var4 += var7;
            var3 += var8;
        }

    }

    static void method365(int[] var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12) {
        int var13 = 256 - var12;
        int var14 = var3;

        for (int var15 = -var8; var15 < 0; ++var15) {
            int var16 = (var4 >> 16) * var11;

            for (int var17 = -var7; var17 < 0; ++var17) {
                var2 = var1[(var3 >> 16) + var16];
                if (var2 != 0) {
                    int var18 = var0[var5];
                    var0[var5++] = ((var2 & 16711935) * var12 + (var18 & 16711935) * var13 & -16711936) + ((var2 & '\uff00') * var12 + (var18 & '\uff00') * var13 & 16711680) >> 8;
                } else {
                    ++var5;
                }

                var3 += var9;
            }

            var4 += var10;
            var3 = var14;
            var5 += var6;
        }

    }

    static void method366(int[] var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, byte[] var11) {
        int var12 = -(var6 >> 2);
        var6 = -(var6 & 3);

        for (int var13 = -var7; var13 < 0; ++var13) {
            int var14;
            for (var14 = var12; var14 < 0; ++var14) {
                var2 = var1[var3++];
                if (var2 != 0 && var11[var5] == 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }

                ++var5;
                var2 = var1[var3++];
                if (var2 != 0 && var11[var5] == 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }

                ++var5;
                var2 = var1[var3++];
                if (var2 != 0 && var11[var5] == 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }

                ++var5;
                var2 = var1[var3++];
                if (var2 != 0 && var11[var5] == 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }

                ++var5;
            }

            for (var14 = var6; var14 < 0; ++var14) {
                var2 = var1[var3++];
                if (var2 != 0 && var11[var5] == 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }

                ++var5;
            }

            var4 += var8;
            var3 += var9;
            var5 += var10;
        }

    }

    public void e(int var1) {
        int[] var2 = new int[this.width * this.height];
        int var3 = 0;

        for (int var4 = 0; var4 < this.height; ++var4) {
            for (int var5 = 0; var5 < this.width; ++var5) {
                int var6 = this.pixels[var3];
                if (var6 == 0) {
                    if (var5 > 0 && this.pixels[var3 - 1] != 0) {
                        var6 = var1;
                    } else if (var4 > 0 && this.pixels[var3 - this.width] != 0) {
                        var6 = var1;
                    } else if (var5 < this.width - 1 && this.pixels[var3 + 1] != 0) {
                        var6 = var1;
                    } else if (var4 < this.height - 1 && this.pixels[var3 + this.width] != 0) {
                        var6 = var1;
                    }
                }

                var2[var3++] = var6;
            }
        }

        this.pixels = var2;
    }

    // Flip Vertically
    public void m() {
        int[] var1 = new int[this.width * this.height];
        int var2 = 0;

        for (int var3 = 0; var3 < this.height; ++var3) {
            for (int var4 = this.width - 1; var4 >= 0; --var4) {
                var1[var2++] = this.pixels[var4 + var3 * this.width];
            }
        }

        this.pixels = var1;
        this.insetX = this.maxX - this.width - this.insetX;
    }

    // Translate Pallet ( R, G, B) adds rgb to the existing image
    public void i(int var1, int var2, int var3) {
        for (int var4 = 0; var4 < this.pixels.length; ++var4) {
            int var5 = this.pixels[var4];
            if (var5 != 0) {
                int var6 = var5 >> 16 & 255;
                var6 += var1;
                if (var6 < 1) {
                    var6 = 1;
                } else if (var6 > 255) {
                    var6 = 255;
                }

                int var7 = var5 >> 8 & 255;
                var7 += var2;
                if (var7 < 1) {
                    var7 = 1;
                } else if (var7 > 255) {
                    var7 = 255;
                }

                int var8 = var5 & 255;
                var8 += var3;
                if (var8 < 1) {
                    var8 = 1;
                } else if (var8 > 255) {
                    var8 = 255;
                }

                this.pixels[var4] = (var6 << 16) + (var7 << 8) + var8;
            }
        }

    }

    public void crop() {
        if (this.width != this.maxX || this.height != this.maxY) {
            int[] var1 = new int[this.maxX * this.maxY];

            for (int var2 = 0; var2 < this.height; ++var2) {
                for (int var3 = 0; var3 < this.width; ++var3) {
                    var1[(var2 + this.insetY) * this.maxX + var3 + this.insetX] = this.pixels[var2 * this.width + var3];
                }
            }

            this.pixels = var1;
            this.width = this.maxX;
            this.height = this.maxY;
            this.insetX = 0;
            this.insetY = 0;
        }
    }

    public void l(int var1) {
        if (this.width != this.maxX || this.height != this.maxY) {
            int var2 = var1;
            if (var1 > this.insetX) {
                var2 = this.insetX;
            }

            int var3 = var1;
            if (var1 + this.insetX + this.width > this.maxX) {
                var3 = this.maxX - this.insetX - this.width;
            }

            int var4 = var1;
            if (var1 > this.insetY) {
                var4 = this.insetY;
            }

            int var5 = var1;
            if (var1 + this.insetY + this.height > this.maxY) {
                var5 = this.maxY - this.insetY - this.height;
            }

            int var6 = this.width + var2 + var3;
            int var7 = this.height + var4 + var5;
            int[] var8 = new int[var6 * var7];

            for (int var9 = 0; var9 < this.height; ++var9) {
                for (int var10 = 0; var10 < this.width; ++var10) {
                    var8[(var9 + var4) * var6 + var10 + var2] = this.pixels[var9 * this.width + var10];
                }
            }

            this.pixels = var8;
            this.width = var6;
            this.height = var7;
            this.insetX -= var2;
            this.insetY -= var4;
        }
    }

    //Flip Horizontally
    public void w() {
        int[] var1 = new int[this.width * this.height];
        int var2 = 0;

        for (int var3 = this.height - 1; var3 >= 0; --var3) {
            for (int var4 = 0; var4 < this.width; ++var4) {
                var1[var2++] = this.pixels[var4 + var3 * this.width];
            }
        }

        this.pixels = var1;
        this.insetY = this.maxY - this.height - this.insetY;
    }

    //draw
    public void s(int var1, int var2) {
        var1 += this.insetX;
        var2 += this.insetY;
        int var3 = var1 + var2 * rasterWidth;
        int var4 = 0;
        int var5 = this.height;
        int var6 = this.width;
        int var7 = rasterWidth - var6;
        int var8 = 0;
        int var9;
        if (var2 < viewportY) {
            var9 = viewportY - var2;
            var5 -= var9;
            var2 = viewportY;
            var4 += var9 * var6;
            var3 += var9 * rasterWidth;
        }

        if (var2 + var5 > viewportMaxY) {
            var5 -= var2 + var5 - viewportMaxY;
        }

        if (var1 < viewportX) {
            var9 = viewportX - var1;
            var6 -= var9;
            var1 = viewportX;
            var4 += var9;
            var3 += var9;
            var8 += var9;
            var7 += var9;
        }

        if (var1 + var6 > viewportMaxX) {
            var9 = var1 + var6 - viewportMaxX;
            var6 -= var9;
            var8 += var9;
            var7 += var9;
        }

        if (var6 > 0 && var5 > 0) {
            method360(raster, this.pixels, var4, var3, var6, var5, var7, var8);
        }
    }

    // drawToFit
    // X,Y,TargetWidth,TargetHeight
    public void x(int var1, int var2, int var3, int var4) {
        if (var3 > 0 && var4 > 0) {
            int var5 = this.width;
            int var6 = this.height;
            int var7 = 0;
            int var8 = 0;
            int var9 = this.maxX;
            int var10 = this.maxY;
            int var11 = (var9 << 16) / var3;
            int var12 = (var10 << 16) / var4;
            int var13;
            if (this.insetX > 0) {
                var13 = ((this.insetX << 16) + var11 - 1) / var11;
                var1 += var13;
                var7 += var13 * var11 - (this.insetX << 16);
            }

            if (this.insetY > 0) {
                var13 = ((this.insetY << 16) + var12 - 1) / var12;
                var2 += var13;
                var8 += var13 * var12 - (this.insetY << 16);
            }

            if (var5 < var9) {
                var3 = ((var5 << 16) - var7 + var11 - 1) / var11;
            }

            if (var6 < var10) {
                var4 = ((var6 << 16) - var8 + var12 - 1) / var12;
            }

            var13 = var1 + var2 * rasterWidth;
            int var14 = rasterWidth - var3;
            if (var2 + var4 > viewportMaxY) {
                var4 -= var2 + var4 - viewportMaxY;
            }

            int var15;
            if (var2 < viewportY) {
                var15 = viewportY - var2;
                var4 -= var15;
                var13 += var15 * rasterWidth;
                var8 += var12 * var15;
            }

            if (var1 + var3 > viewportMaxX) {
                var15 = var1 + var3 - viewportMaxX;
                var3 -= var15;
                var14 += var15;
            }

            if (var1 < viewportX) {
                var15 = viewportX - var1;
                var3 -= var15;
                var13 += var15;
                var7 += var11 * var15;
                var14 += var15;
            }

            method362(raster, this.pixels, 0, var7, var8, var13, var14, var3, var4, var11, var12, var5);
        }
    }

    /**
     * Fades the image into a target color.
     * when Alpha is 256 no shade is applied,
     * as the alpha approaches 0 the target
     * color becomes more dominant.
     *
     * @param var1 X
     * @param var2 Y
     * @param var3 Alpha
     * @param var4 TargetColor
     */
    public void q(int var1, int var2, int var3, int var4) {
        if (var3 == 256) {
            this.f(var1, var2);
        } else {
            var1 += this.insetX;
            var2 += this.insetY;
            int var5 = var1 + var2 * rasterWidth;
            int var6 = 0;
            int var7 = this.height;
            int var8 = this.width;
            int var9 = rasterWidth - var8;
            int var10 = 0;
            int var11;
            if (var2 < viewportY) {
                var11 = viewportY - var2;
                var7 -= var11;
                var2 = viewportY;
                var6 += var11 * var8;
                var5 += var11 * rasterWidth;
            }

            if (var2 + var7 > viewportMaxY) {
                var7 -= var2 + var7 - viewportMaxY;
            }

            if (var1 < viewportX) {
                var11 = viewportX - var1;
                var8 -= var11;
                var1 = viewportX;
                var6 += var11;
                var5 += var11;
                var10 += var11;
                var9 += var11;
            }

            if (var1 + var8 > viewportMaxX) {
                var11 = var1 + var8 - viewportMaxX;
                var8 -= var11;
                var10 += var11;
                var9 += var11;
            }

            if (var8 > 0 && var7 > 0) {
                method363(raster, this.pixels, 0, var6, var5, var8, var7, var9, var10, var3, var4);
            }
        }
    }

    /**
     * drawToFit
     *
     * draws the sprite to fit within a specific bounds,
     * with an alpha.
     *
     * @param var1 X to draw the sprite
     * @param var2 Y
     * @param var3 Target Width
     * @param var4 Target Height
     * @param var5 Alpha
     */
    public void j(int var1, int var2, int var3, int var4, int var5) {
        if (var3 > 0 && var4 > 0) {
            int var6 = this.width;
            int var7 = this.height;
            int var8 = 0;
            int var9 = 0;
            int var10 = this.maxX;
            int var11 = this.maxY;
            int var12 = (var10 << 16) / var3;
            int var13 = (var11 << 16) / var4;
            int var14;
            if (this.insetX > 0) {
                var14 = ((this.insetX << 16) + var12 - 1) / var12;
                var1 += var14;
                var8 += var14 * var12 - (this.insetX << 16);
            }

            if (this.insetY > 0) {
                var14 = ((this.insetY << 16) + var13 - 1) / var13;
                var2 += var14;
                var9 += var14 * var13 - (this.insetY << 16);
            }

            if (var6 < var10) {
                var3 = ((var6 << 16) - var8 + var12 - 1) / var12;
            }

            if (var7 < var11) {
                var4 = ((var7 << 16) - var9 + var13 - 1) / var13;
            }

            var14 = var1 + var2 * rasterWidth;
            int var15 = rasterWidth - var3;
            if (var2 + var4 > viewportMaxY) {
                var4 -= var2 + var4 - viewportMaxY;
            }

            int var16;
            if (var2 < viewportY) {
                var16 = viewportY - var2;
                var4 -= var16;
                var14 += var16 * rasterWidth;
                var9 += var13 * var16;
            }

            if (var1 + var3 > viewportMaxX) {
                var16 = var1 + var3 - viewportMaxX;
                var3 -= var16;
                var15 += var16;
            }

            if (var1 < viewportX) {
                var16 = viewportX - var1;
                var3 -= var16;
                var14 += var16;
                var8 += var12 * var16;
                var15 += var16;
            }

            method365(raster, this.pixels, 0, var8, var9, var14, var15, var3, var4, var12, var13, var6, var5);
        }
    }



    public void y(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int[] var9, int[] var10) {
        try {
            int var11 = -var3 / 2;
            int var12 = -var4 / 2;
            int var13 = (int) (Math.sin((double) var7 / 326.11D) * 65536.0D);
            int var14 = (int) (Math.cos((double) var7 / 326.11D) * 65536.0D);
            var13 = var13 * var8 >> 8;
            var14 = var14 * var8 >> 8;
            int var15 = (var5 << 16) + var12 * var13 + var11 * var14;
            int var16 = (var6 << 16) + (var12 * var14 - var11 * var13);
            int var17 = var1 + var2 * rasterWidth;

            for (var2 = 0; var2 < var4; ++var2) {
                int var18 = var9[var2];
                int var19 = var17 + var18;
                int var20 = var15 + var14 * var18;
                int var21 = var16 - var13 * var18;

                for (var1 = -var10[var2]; var1 < 0; ++var1) {
                    raster[var19++] = this.pixels[(var20 >> 16) + (var21 >> 16) * this.width];
                    var20 += var14;
                    var21 -= var13;
                }

                var15 += var13;
                var16 += var14;
                var17 += rasterWidth;
            }
        } catch (Exception var22) {
            ;
        }

    }

    void z(int var1, int var2, int var3, int var4, int var5, int var6) {
        if (var6 != 0) {
            var1 -= this.insetX << 4;
            var2 -= this.insetY << 4;
            double var7 = (double) (var5 & 65535) * 9.587379924285257E-5D;
            int var9 = (int) Math.floor(Math.sin(var7) * (double) var6 + 0.5D);
            int var10 = (int) Math.floor(Math.cos(var7) * (double) var6 + 0.5D);
            int var11 = -var1 * var10 + -var2 * var9;
            int var12 = -(-var1) * var9 + -var2 * var10;
            int var13 = ((this.width << 4) - var1) * var10 + -var2 * var9;
            int var14 = -((this.width << 4) - var1) * var9 + -var2 * var10;
            int var15 = -var1 * var10 + ((this.height << 4) - var2) * var9;
            int var16 = -(-var1) * var9 + ((this.height << 4) - var2) * var10;
            int var17 = ((this.width << 4) - var1) * var10 + ((this.height << 4) - var2) * var9;
            int var18 = -((this.width << 4) - var1) * var9 + ((this.height << 4) - var2) * var10;
            int var19;
            int var20;
            if (var11 < var13) {
                var19 = var11;
                var20 = var13;
            } else {
                var19 = var13;
                var20 = var11;
            }

            if (var15 < var19) {
                var19 = var15;
            }

            if (var17 < var19) {
                var19 = var17;
            }

            if (var15 > var20) {
                var20 = var15;
            }

            if (var17 > var20) {
                var20 = var17;
            }

            int var21;
            int var22;
            if (var12 < var14) {
                var21 = var12;
                var22 = var14;
            } else {
                var21 = var14;
                var22 = var12;
            }

            if (var16 < var21) {
                var21 = var16;
            }

            if (var18 < var21) {
                var21 = var18;
            }

            if (var16 > var22) {
                var22 = var16;
            }

            if (var18 > var22) {
                var22 = var18;
            }

            var19 >>= 12;
            var20 = var20 + 4095 >> 12;
            var21 >>= 12;
            var22 = var22 + 4095 >> 12;
            var19 += var3;
            var20 += var3;
            var21 += var4;
            var22 += var4;
            var19 >>= 4;
            var20 = var20 + 15 >> 4;
            var21 >>= 4;
            var22 = var22 + 15 >> 4;
            if (var19 < viewportX) {
                var19 = viewportX;
            }

            if (var20 > viewportMaxX) {
                var20 = viewportMaxX;
            }

            if (var21 < viewportY) {
                var21 = viewportY;
            }

            if (var22 > viewportMaxY) {
                var22 = viewportMaxY;
            }

            var20 = var19 - var20;
            if (var20 < 0) {
                var22 = var21 - var22;
                if (var22 < 0) {
                    int var23 = var21 * rasterWidth + var19;
                    double var24 = 1.6777216E7D / (double) var6;
                    int var26 = (int) Math.floor(Math.sin(var7) * var24 + 0.5D);
                    int var27 = (int) Math.floor(Math.cos(var7) * var24 + 0.5D);
                    int var28 = (var19 << 4) + 8 - var3;
                    int var29 = (var21 << 4) + 8 - var4;
                    int var30 = (var1 << 8) - (var29 * var26 >> 4);
                    int var31 = (var2 << 8) + (var29 * var27 >> 4);
                    int var32;
                    int var33;
                    int var34;
                    int var35;
                    int var36;
                    int var37;
                    int var38;
                    if (var27 == 0) {
                        if (var26 == 0) {
                            for (var33 = var22; var33 < 0; var23 += rasterWidth) {
                                var34 = var23;
                                var35 = var30;
                                var36 = var31;
                                var37 = var20;
                                if (var30 >= 0 && var31 >= 0 && var30 - (this.width << 12) < 0 && var31 - (this.height << 12) < 0) {
                                    for (; var37 < 0; ++var37) {
                                        var38 = this.pixels[(var36 >> 12) * this.width + (var35 >> 12)];
                                        if (var38 != 0) {
                                            raster[var34++] = var38;
                                        } else {
                                            ++var34;
                                        }
                                    }
                                }

                                ++var33;
                            }
                        } else if (var26 < 0) {
                            for (var33 = var22; var33 < 0; var23 += rasterWidth) {
                                var34 = var23;
                                var35 = var30;
                                var36 = var31 + (var28 * var26 >> 4);
                                var37 = var20;
                                if (var30 >= 0 && var30 - (this.width << 12) < 0) {
                                    if ((var32 = var36 - (this.height << 12)) >= 0) {
                                        var32 = (var26 - var32) / var26;
                                        var37 = var20 + var32;
                                        var36 += var26 * var32;
                                        var34 = var23 + var32;
                                    }

                                    if ((var32 = (var36 - var26) / var26) > var37) {
                                        var37 = var32;
                                    }

                                    while (var37 < 0) {
                                        var38 = this.pixels[(var36 >> 12) * this.width + (var35 >> 12)];
                                        if (var38 != 0) {
                                            raster[var34++] = var38;
                                        } else {
                                            ++var34;
                                        }

                                        var36 += var26;
                                        ++var37;
                                    }
                                }

                                ++var33;
                                var30 -= var26;
                            }
                        } else {
                            for (var33 = var22; var33 < 0; var23 += rasterWidth) {
                                var34 = var23;
                                var35 = var30;
                                var36 = var31 + (var28 * var26 >> 4);
                                var37 = var20;
                                if (var30 >= 0 && var30 - (this.width << 12) < 0) {
                                    if (var36 < 0) {
                                        var32 = (var26 - 1 - var36) / var26;
                                        var37 = var20 + var32;
                                        var36 += var26 * var32;
                                        var34 = var23 + var32;
                                    }

                                    if ((var32 = (1 + var36 - (this.height << 12) - var26) / var26) > var37) {
                                        var37 = var32;
                                    }

                                    while (var37 < 0) {
                                        var38 = this.pixels[(var36 >> 12) * this.width + (var35 >> 12)];
                                        if (var38 != 0) {
                                            raster[var34++] = var38;
                                        } else {
                                            ++var34;
                                        }

                                        var36 += var26;
                                        ++var37;
                                    }
                                }

                                ++var33;
                                var30 -= var26;
                            }
                        }
                    } else if (var27 < 0) {
                        if (var26 == 0) {
                            for (var33 = var22; var33 < 0; var23 += rasterWidth) {
                                var34 = var23;
                                var35 = var30 + (var28 * var27 >> 4);
                                var36 = var31;
                                var37 = var20;
                                if (var31 >= 0 && var31 - (this.height << 12) < 0) {
                                    if ((var32 = var35 - (this.width << 12)) >= 0) {
                                        var32 = (var27 - var32) / var27;
                                        var37 = var20 + var32;
                                        var35 += var27 * var32;
                                        var34 = var23 + var32;
                                    }

                                    if ((var32 = (var35 - var27) / var27) > var37) {
                                        var37 = var32;
                                    }

                                    while (var37 < 0) {
                                        var38 = this.pixels[(var36 >> 12) * this.width + (var35 >> 12)];
                                        if (var38 != 0) {
                                            raster[var34++] = var38;
                                        } else {
                                            ++var34;
                                        }

                                        var35 += var27;
                                        ++var37;
                                    }
                                }

                                ++var33;
                                var31 += var27;
                            }
                        } else if (var26 < 0) {
                            for (var33 = var22; var33 < 0; var23 += rasterWidth) {
                                var34 = var23;
                                var35 = var30 + (var28 * var27 >> 4);
                                var36 = var31 + (var28 * var26 >> 4);
                                var37 = var20;
                                if ((var32 = var35 - (this.width << 12)) >= 0) {
                                    var32 = (var27 - var32) / var27;
                                    var37 = var20 + var32;
                                    var35 += var27 * var32;
                                    var36 += var26 * var32;
                                    var34 = var23 + var32;
                                }

                                if ((var32 = (var35 - var27) / var27) > var37) {
                                    var37 = var32;
                                }

                                if ((var32 = var36 - (this.height << 12)) >= 0) {
                                    var32 = (var26 - var32) / var26;
                                    var37 += var32;
                                    var35 += var27 * var32;
                                    var36 += var26 * var32;
                                    var34 += var32;
                                }

                                if ((var32 = (var36 - var26) / var26) > var37) {
                                    var37 = var32;
                                }

                                while (var37 < 0) {
                                    var38 = this.pixels[(var36 >> 12) * this.width + (var35 >> 12)];
                                    if (var38 != 0) {
                                        raster[var34++] = var38;
                                    } else {
                                        ++var34;
                                    }

                                    var35 += var27;
                                    var36 += var26;
                                    ++var37;
                                }

                                ++var33;
                                var30 -= var26;
                                var31 += var27;
                            }
                        } else {
                            for (var33 = var22; var33 < 0; var23 += rasterWidth) {
                                var34 = var23;
                                var35 = var30 + (var28 * var27 >> 4);
                                var36 = var31 + (var28 * var26 >> 4);
                                var37 = var20;
                                if ((var32 = var35 - (this.width << 12)) >= 0) {
                                    var32 = (var27 - var32) / var27;
                                    var37 = var20 + var32;
                                    var35 += var27 * var32;
                                    var36 += var26 * var32;
                                    var34 = var23 + var32;
                                }

                                if ((var32 = (var35 - var27) / var27) > var37) {
                                    var37 = var32;
                                }

                                if (var36 < 0) {
                                    var32 = (var26 - 1 - var36) / var26;
                                    var37 += var32;
                                    var35 += var27 * var32;
                                    var36 += var26 * var32;
                                    var34 += var32;
                                }

                                if ((var32 = (1 + var36 - (this.height << 12) - var26) / var26) > var37) {
                                    var37 = var32;
                                }

                                while (var37 < 0) {
                                    var38 = this.pixels[(var36 >> 12) * this.width + (var35 >> 12)];
                                    if (var38 != 0) {
                                        raster[var34++] = var38;
                                    } else {
                                        ++var34;
                                    }

                                    var35 += var27;
                                    var36 += var26;
                                    ++var37;
                                }

                                ++var33;
                                var30 -= var26;
                                var31 += var27;
                            }
                        }
                    } else if (var26 == 0) {
                        for (var33 = var22; var33 < 0; var23 += rasterWidth) {
                            var34 = var23;
                            var35 = var30 + (var28 * var27 >> 4);
                            var36 = var31;
                            var37 = var20;
                            if (var31 >= 0 && var31 - (this.height << 12) < 0) {
                                if (var35 < 0) {
                                    var32 = (var27 - 1 - var35) / var27;
                                    var37 = var20 + var32;
                                    var35 += var27 * var32;
                                    var34 = var23 + var32;
                                }

                                if ((var32 = (1 + var35 - (this.width << 12) - var27) / var27) > var37) {
                                    var37 = var32;
                                }

                                while (var37 < 0) {
                                    var38 = this.pixels[(var36 >> 12) * this.width + (var35 >> 12)];
                                    if (var38 != 0) {
                                        raster[var34++] = var38;
                                    } else {
                                        ++var34;
                                    }

                                    var35 += var27;
                                    ++var37;
                                }
                            }

                            ++var33;
                            var31 += var27;
                        }
                    } else if (var26 < 0) {
                        for (var33 = var22; var33 < 0; var23 += rasterWidth) {
                            var34 = var23;
                            var35 = var30 + (var28 * var27 >> 4);
                            var36 = var31 + (var28 * var26 >> 4);
                            var37 = var20;
                            if (var35 < 0) {
                                var32 = (var27 - 1 - var35) / var27;
                                var37 = var20 + var32;
                                var35 += var27 * var32;
                                var36 += var26 * var32;
                                var34 = var23 + var32;
                            }

                            if ((var32 = (1 + var35 - (this.width << 12) - var27) / var27) > var37) {
                                var37 = var32;
                            }

                            if ((var32 = var36 - (this.height << 12)) >= 0) {
                                var32 = (var26 - var32) / var26;
                                var37 += var32;
                                var35 += var27 * var32;
                                var36 += var26 * var32;
                                var34 += var32;
                            }

                            if ((var32 = (var36 - var26) / var26) > var37) {
                                var37 = var32;
                            }

                            while (var37 < 0) {
                                var38 = this.pixels[(var36 >> 12) * this.width + (var35 >> 12)];
                                if (var38 != 0) {
                                    raster[var34++] = var38;
                                } else {
                                    ++var34;
                                }

                                var35 += var27;
                                var36 += var26;
                                ++var37;
                            }

                            ++var33;
                            var30 -= var26;
                            var31 += var27;
                        }
                    } else {
                        for (var33 = var22; var33 < 0; var23 += rasterWidth) {
                            var34 = var23;
                            var35 = var30 + (var28 * var27 >> 4);
                            var36 = var31 + (var28 * var26 >> 4);
                            var37 = var20;
                            if (var35 < 0) {
                                var32 = (var27 - 1 - var35) / var27;
                                var37 = var20 + var32;
                                var35 += var27 * var32;
                                var36 += var26 * var32;
                                var34 = var23 + var32;
                            }

                            if ((var32 = (1 + var35 - (this.width << 12) - var27) / var27) > var37) {
                                var37 = var32;
                            }

                            if (var36 < 0) {
                                var32 = (var26 - 1 - var36) / var26;
                                var37 += var32;
                                var35 += var27 * var32;
                                var36 += var26 * var32;
                                var34 += var32;
                            }

                            if ((var32 = (1 + var36 - (this.height << 12) - var26) / var26) > var37) {
                                var37 = var32;
                            }

                            while (var37 < 0) {
                                var38 = this.pixels[(var36 >> 12) * this.width + (var35 >> 12)];
                                if (var38 != 0) {
                                    raster[var34++] = var38;
                                } else {
                                    ++var34;
                                }

                                var35 += var27;
                                var36 += var26;
                                ++var37;
                            }

                            ++var33;
                            var30 -= var26;
                            var31 += var27;
                        }
                    }

                }
            }
        }
    }

    public void c(RTIcon var1, int var2, int var3) {
        if (viewportMaxX - viewportX == var1.maxX && viewportMaxY - viewportY == var1.maxY) {
            var2 += this.insetX;
            var3 += this.insetY;
            int var4 = var2 + var3 * rasterWidth;
            int var5 = 0;
            int var6 = this.height;
            int var7 = this.width;
            int var8 = rasterWidth - var7;
            int var9 = 0;
            int var10;
            if (var3 < viewportY) {
                var10 = viewportY - var3;
                var6 -= var10;
                var3 = viewportY;
                var5 += var10 * var7;
                var4 += var10 * rasterWidth;
            }

            if (var3 + var6 > viewportMaxY) {
                var6 -= var3 + var6 - viewportMaxY;
            }

            if (var2 < viewportX) {
                var10 = viewportX - var2;
                var7 -= var10;
                var2 = viewportX;
                var5 += var10;
                var4 += var10;
                var9 += var10;
                var8 += var10;
            }

            if (var2 + var7 > viewportMaxX) {
                var10 = var2 + var7 - viewportMaxX;
                var7 -= var10;
                var9 += var10;
                var8 += var10;
            }

            if (var7 > 0 && var6 > 0) {
                var10 = var2 - viewportX + (var3 - viewportY) * var1.maxX;
                int var11 = var1.maxX - var7;
                method366(raster, this.pixels, 0, var5, var4, var10, var7, var6, var8, var9, var11, var1.indices);
            }
        } else {
            throw new IllegalStateException();
        }
    }

    public void r(int axisX, int axisY, int rotation, int radius) {
        this.z(this.maxX << 3, this.maxY << 3, axisX << 4, axisY << 4, rotation, radius);
    }

    public void setBackgroundColor ( int var1 ) {
        for (int var2 = this.height - 1; var2 > 0; --var2) {
            int var3 = var2 * this.width;

            for (int var4 = this.width - 1; var4 > 0; --var4) {
                if (this.pixels[var4 + var3] == 0 && this.pixels[var4 + var3 - 1 - this.width] != 0) {
                    this.pixels[var4 + var3] = var1;
                }
            }
        }

    }

    // Clone
    public RTImage a() {
        RTImage var1 = new RTImage(this.width, this.height);
        var1.maxX = this.maxX;
        var1.maxY = this.maxY;
        var1.insetX = this.maxX - this.width - this.insetX;
        var1.insetY = this.insetY;

        for (int var2 = 0; var2 < this.height; ++var2) {
            for (int var3 = 0; var3 < this.width; ++var3) {
                var1.pixels[var2 * this.width + var3] = this.pixels[var2 * this.width + this.width - 1 - var3];
            }
        }

        return var1;
    }

    // drawAlpha
    public void h(int var1, int var2, int var3) {
        var1 += this.insetX;
        var2 += this.insetY;
        int var4 = var1 + var2 * rasterWidth;
        int var5 = 0;
        int var6 = this.height;
        int var7 = this.width;
        int var8 = rasterWidth - var7;
        int var9 = 0;
        int var10;
        if (var2 < viewportY) {
            var10 = viewportY - var2;
            var6 -= var10;
            var2 = viewportY;
            var5 += var10 * var7;
            var4 += var10 * rasterWidth;
        }

        if (var2 + var6 > viewportMaxY) {
            var6 -= var2 + var6 - viewportMaxY;
        }

        if (var1 < viewportX) {
            var10 = viewportX - var1;
            var7 -= var10;
            var1 = viewportX;
            var5 += var10;
            var4 += var10;
            var9 += var10;
            var8 += var10;
        }

        if (var1 + var7 > viewportMaxX) {
            var10 = var1 + var7 - viewportMaxX;
            var7 -= var10;
            var9 += var10;
            var8 += var10;
        }

        if (var7 > 0 && var6 > 0) {
            method364(raster, this.pixels, 0, var5, var4, var7, var6, var8, var9, var3);
        }
    }

    public void v() {
        setRaster(this.pixels, this.width, this.height);
    }

    public void f(int var1, int var2) {
        var1 += this.insetX;
        var2 += this.insetY;
        int var3 = var1 + var2 * rasterWidth;
        int var4 = 0;
        int var5 = this.height;
        int var6 = this.width;
        int var7 = rasterWidth - var6;
        int var8 = 0;
        int var9;
        if (var2 < viewportY) {
            var9 = viewportY - var2;
            var5 -= var9;
            var2 = viewportY;
            var4 += var9 * var6;
            var3 += var9 * rasterWidth;
        }

        if (var2 + var5 > viewportMaxY) {
            var5 -= var2 + var5 - viewportMaxY;
        }

        if (var1 < viewportX) {
            var9 = viewportX - var1;
            var6 -= var9;
            var1 = viewportX;
            var4 += var9;
            var3 += var9;
            var8 += var9;
            var7 += var9;
        }

        if (var1 + var6 > viewportMaxX) {
            var9 = var1 + var6 - viewportMaxX;
            var6 -= var9;
            var8 += var9;
            var7 += var9;
        }

        if (var6 > 0 && var5 > 0) {
            method361(raster, this.pixels, 0, var4, var3, var6, var5, var7, var8);
        }
    }

    /**
     *
     * @param var1
     * @param var2
     * @param var3
     * @param var4
     * @param var5
     * @param var6
     * @param var7
     * @param var9 Magnification: units of 256.   1 == 256
     *
     *             128/256 --> 0.5
     */
    public void p(int var1, int var2, int var3, int var4, int var5, int var6, double var7, int var9) {
        try {
            int var10 = -var3 / 2;
            int var11 = -var4 / 2;
            int var12 = (int) (Math.sin(var7) * 65536.0D);
            int var13 = (int) (Math.cos(var7) * 65536.0D);
            var12 = var12 * var9 >> 8;
            var13 = var13 * var9 >> 8;
            int var14 = (var5 << 16) + var11 * var12 + var10 * var13;
            int var15 = (var6 << 16) + (var11 * var13 - var10 * var12);
            int var16 = var1 + var2 * rasterWidth;

            for (var2 = 0; var2 < var4; ++var2) {
                int var17 = var16;
                int var18 = var14;
                int var19 = var15;

                for (var1 = -var3; var1 < 0; ++var1) {
                    int var20 = this.pixels[(var18 >> 16) + (var19 >> 16) * this.width];
                    if (var20 != 0) {
                        raster[var17++] = var20;
                    } else {
                        ++var17;
                    }

                    var18 += var13;
                    var19 -= var12;
                }

                var14 += var12;
                var15 += var13;
                var16 += rasterWidth;
            }
        } catch (Exception var21) {
            ;
        }

    }
}
