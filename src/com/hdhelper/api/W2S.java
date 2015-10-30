package com.hdhelper.api;

import com.hdhelper.Main;
import com.hdhelper.api.ge.RTGraphics;
import com.hdhelper.peer.RSClient;

import java.awt.*;

public class W2S {

    public static final int JAGEX_CIRCULAR_ANGLE = 2048;
    public static final double ANGULAR_RATIO = 360D / JAGEX_CIRCULAR_ANGLE;
    public static final double JAGEX_RADIAN = Math.toRadians(ANGULAR_RATIO);

    public static final int[] SIN = new int[JAGEX_CIRCULAR_ANGLE];
    public static final int[] COS = new int[JAGEX_CIRCULAR_ANGLE];

    static {
        for (int i = 0; i < JAGEX_CIRCULAR_ANGLE; i++) {
            SIN[i] = (int) (65536.0D * Math.sin((double) i * JAGEX_RADIAN));
            COS[i] = (int) (65536.0D * Math.cos((double) i * JAGEX_RADIAN));
        }
    }



    public static Point regionToViewport(int x, int y, int floor, int height) {

        if (x < 0 || y < 0 || x > 102 || y > 102) {
            return null;
        }

        final RSClient client = Main.client;

        int h = client.getTileHeights()[floor][x][y];
        int z = h - height;

        x <<= 7;
        y <<= 7;

        x -= client.getCameraX();
        z -= client.getCameraZ();
        y -= client.getCameraY();

        int sinCurveY = SIN[client.getPitch()];
        int cosCurveY = COS[client.getPitch()];
        int sinCurveX = SIN[client.getYaw()];
        int cosCurveX = COS[client.getYaw()];

        int calculation = sinCurveX * y + cosCurveX * x >> 16;
        y = y * cosCurveX - x * sinCurveX >> 16;
        x = calculation;

        calculation = cosCurveY * z - sinCurveY * y >> 16;
        y = sinCurveY * z + cosCurveY * y >> 16;
        z = calculation;

        if (y >= 50) {

            int screenX = x * client.getViewportScale() / y + client.getViewportWidth() / 2;
            int screenY = z * client.getViewportScale() / y + client.getViewportHeight() / 2;

            return new Point(screenX, screenY);

        }

        return null;

    }

    public static boolean tileToViewportNULL(int x, int y, final int floor, final int height, Point dest) {
        final RSClient client = Main.client;
        if (x < 128 || y < 128 || x > 13056 || y > 13056) {
            return false;
        }
        int z = getTileHeight(floor, x, y) - height;
        x -= client.getCameraX();
        z -= client.getCameraZ();
        y -= client.getCameraY();
        int sinCurveY = SIN[client.getPitch()];
        int cosCurveY = COS[client.getPitch()];
        int sinCurveX = SIN[client.getYaw()];
        int cosCurveX = COS[client.getYaw()];

        int calculation = sinCurveX * y + cosCurveX * x >> 16;
        y = y * cosCurveX - x * sinCurveX >> 16;
        x = calculation;

        calculation = cosCurveY * z - sinCurveY * y >> 16;
        y = sinCurveY * z + cosCurveY * y >> 16;
        z = calculation;

        if (y >= 50) {
            int screenX = x * client.getViewportScale() / y + client.getViewportWidth() / 2;
            int screenY = z * client.getViewportScale() / y + client.getViewportHeight() / 2;
            dest.setLocation(screenX,screenY);
            return true;
        }

        return false;

    }


    public static Point tileToViewport(int x, int y, final int z0, final int height) {
        final RSClient client = Main.client;
        if (x < 128 || y < 128 || x > 13056 || y > 13056) {
            return new Point(-1, -1);
        }
        int z = getTileHeight(z0, x, y) - height;
        x -= client.getCameraX();
        z -= client.getCameraZ();
        y -= client.getCameraY();
        int sinCurveY = SIN[client.getPitch()];
        int cosCurveY = COS[client.getPitch()];
        int sinCurveX = SIN[client.getYaw()];
        int cosCurveX = COS[client.getYaw()];

        int calculation = sinCurveX * y + cosCurveX * x >> 16;
        y = y * cosCurveX - x * sinCurveX >> 16;
        x = calculation;

        calculation = cosCurveY * z - sinCurveY * y >> 16;
        y = sinCurveY * z + cosCurveY * y >> 16;
        z = calculation;

        if (y >= 50) {
            int screenX = x * client.getViewportScale() / y + client.getViewportWidth() / 2;
            int screenY = z * client.getViewportScale() / y + client.getViewportHeight() / 2;

            return new Point(screenX, screenY);
        }

        return new Point(-1, -1);
    }

    public static int getTileHeight(final int floorLevel, final int x, final int y) {
        final int rx = x >> 7;
        final int ry = y >> 7;
        if (rx < 0 || ry < 0 || floorLevel < 0 || rx > 103 || ry > 103 || floorLevel > 3) {
            return 0;
        }
        final int[][] h = Main.client.getTileHeights()[floorLevel];
        final int aa = h[rx][ry] * (128 - (x & 0x7f)) + h[rx + 1][ry] * (x & 0x7F) >> 7;
        final int ab = h[rx][ry + 1] * (128 - (x & 0x7F)) + h[rx + 1][ry + 1] * (x & 0x7F) >> 7;
        return aa * (128 - (y & 0x7F)) + ab * (y & 0x7F) >> 7;
    }



    public static void draw3DBox(int floor, int rx, int ry, int height, Graphics2D g) {

        Point BA = regionToViewport(rx,ry,floor,0);
        if(BA == null) return;
        Point BB = regionToViewport(rx,ry+1,floor,0);
        if(BB == null) return;
        Point BC = regionToViewport(rx+1,ry+1,floor,0);
        if(BC == null) return;
        Point BD = regionToViewport(rx+1,ry,floor,0);
        if(BD == null) return;



        Point TA = regionToViewport(rx,ry,floor,height);
        if(TA == null) return;
        Point TB = regionToViewport(rx,ry+1,floor,height);
        if(TB == null) return;
        Point TC = regionToViewport(rx+1,ry+1,floor,height);
        if(TC == null) return;
        Point TD = regionToViewport(rx+1,ry,floor,height);
        if(TD == null) return;
        
        //Bottom
        drawLine(BA,BB,g);
        drawLine(BB,BC,g);
        drawLine(BC,BD,g);
        drawLine(BD,BA,g);

        //Top
        drawLine(TA,TB,g);
        drawLine(TB,TC,g);
        drawLine(TC,TD,g);
        drawLine(TD,TA,g);

        //Sides
        drawLine(BA,TA,g);
        drawLine(BB,TB,g);
        drawLine(BC,TC,g);
        drawLine(BD,TD,g);

    }


    public static void draw3DBox(int floor, int rx, int ry, int height, RTGraphics g, int color) {

        Point BA = regionToViewport(rx,ry,floor,0);
        if(BA == null) return;
        Point BB = regionToViewport(rx,ry+1,floor,0);
        if(BB == null) return;
        Point BC = regionToViewport(rx+1,ry+1,floor,0);
        if(BC == null) return;
        Point BD = regionToViewport(rx+1,ry,floor,0);
        if(BD == null) return;



        Point TA = regionToViewport(rx,ry,floor,height);
        if(TA == null) return;
        Point TB = regionToViewport(rx,ry+1,floor,height);
        if(TB == null) return;
        Point TC = regionToViewport(rx+1,ry+1,floor,height);
        if(TC == null) return;
        Point TD = regionToViewport(rx+1,ry,floor,height);
        if(TD == null) return;

        //Bottom
        g.drawLine(BA, BB, color);
        g.drawLine(BA, BB, color);
        g.drawLine(BB, BC, color);
        g.drawLine(BC, BD, color);
        g.drawLine(BD, BA, color);

        //Top
        g.drawLine(TA, TB, color);
        g.drawLine(TB, TC, color);
        g.drawLine(TC, TD, color);
        g.drawLine(TD, TA, color);

        //Sides
        g.drawLine(BA, TA, color);
        g.drawLine(BB, TB, color);
        g.drawLine(BC, TC, color);
        g.drawLine(BD, TD, color);

    }
    
    private static void drawLine(Point A, Point B, Graphics2D g) {
        g.drawLine(A.x,A.y,B.x,B.y);
    }
}
