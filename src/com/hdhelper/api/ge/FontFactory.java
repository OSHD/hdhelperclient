package com.hdhelper.api.ge;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

/**
 * Created by Jamie on 10/27/2015.
 */
public class FontFactory {




    private static class FontData {
        int[] widths;
        int[] heights;
        int[] xOffset;
        int[] yOffset;
        int[] absWidth;
        byte[][] flags;

        int baseLine;

        FontData(int numChars) {
            widths   = new int[numChars];
            heights  = new int[numChars];
            xOffset  = new int[numChars];
            yOffset  = new int[numChars];
            absWidth = new int[numChars];
            flags    = new byte[numChars][];
        }

        public byte[] getHeader() {
            byte[] buffer = new byte[257];
            for(int w = 0; w < 256; w++) {
                buffer[w] = (byte) absWidth[w];
            }
            buffer[256] = (byte) baseLine;
            return buffer;
        }
    }


    public static RTFont create(Font f) {
        FontData d = profile(f);
        byte[] header = d.getHeader();
        RTFont font = new RTFontImpl(header,d.xOffset,d.yOffset,d.widths,d.heights,null,d.flags);
        return font;
    }

    public static FontData profile(Font font) {
        //Test
        Canvas c = new Canvas();
        FontMetrics fm = c.getFontMetrics(font);
        FontData data = new FontData(256);
        data.baseLine = fm.getHeight() - fm.getDescent();
        for (int i = 0; i < 256; i++)
            profileCharacter(font, fm, (char) i, i, false, data);
        return data;
    }

    public static void l() {

    }
    public static void profileCharacter(Font font, FontMetrics fontmetrics, char c, int index, boolean bool, FontData dest) {
        int char_with = fontmetrics.charWidth(c);
        int y = fontmetrics.getMaxAscent();

        int char_height = fontmetrics.getMaxAscent() + fontmetrics.getMaxDescent();

    //    if(char_with==0||char_height==0) System.out.println("EEK:" + c);
        if(char_with==0||char_height==0) return;

        Image image = new BufferedImage(char_with,char_height,BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, char_with, char_height);
        graphics.setColor(Color.white);
        graphics.setFont(font);

        graphics.drawString((new StringBuilder()).append(c).append("").toString(), 0, y);

        int raster[] = new int[char_with * char_height];
        PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, char_with, char_height, raster, 0, char_with);
        try {
            pixelgrabber.grabPixels();
        } catch (Exception ignored) {
        }
        image.flush();

        int minX = 0;
        int minY = 0;
        int maxX = char_with;
        int maxY = char_height;

        label0:
        for (int y0 = 0; y0 < char_height; y0++) {
            for (int x0 = 0; x0 < char_with; x0++) {
                int color = raster[x0 + y0 * char_with];
                if ((color & 0xffffff) == 0) continue;
                minY = y0;
                break label0;
            }

        }

        label1:
        for (int x0 = 0; x0 < char_with; x0++) {
            for (int y0 = 0; y0 < char_height; y0++) {
                int color = raster[x0 + y0 * char_with];
                if ((color & 0xffffff) == 0)
                    continue;
                minX = x0;
                break label1;
            }

        }

        label2:
        for (int y0 = char_height - 1; y0 >= 0; y0--) {
            for (int x0 = 0; x0 < char_with; x0++) {
                int color = raster[x0 + y0 * char_with];
                if ((color & 0xffffff) == 0) continue;
                maxY = y0 + 1;
                break label2;
            }

        }

        label3:
        for (int x0 = char_with - 1; x0 >= 0; x0--) {
            for (int y0 = 0; y0 < char_height; y0++) {
                int color = raster[x0 + y0 * char_with];
                if ((color & 0xffffff) == 0) continue;
                maxX = x0 + 1;
                break label3;
            }
        }

        dest.widths[index] = (maxX - minX);
        dest.heights[index] =(maxY - minY);
        dest.xOffset[index] = (minX);
        dest.yOffset[index] = minY;
        dest.absWidth[index] = char_with;

        int w = maxX-minX;
        int h = maxY-minY;
        dest.flags[index] = new byte[w*h];
        byte[] flags = dest.flags[index];
        for (int y0 = minY,by=0; y0 < maxY; y0++,by++) {
            for (int x0 = minX,bx=0; x0 < maxX; x0++,bx++) {
                flags[by*w+bx] = (byte) (raster[x0 + y0 * char_with] & 0xFF);
            }
        }

    }

}
