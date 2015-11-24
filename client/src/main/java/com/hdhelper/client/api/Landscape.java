package com.hdhelper.client.api;

import com.hdhelper.agent.services.RSClient;
import com.hdhelper.agent.services.RSEntityMarker;
import com.hdhelper.agent.services.RSLandscape;
import com.hdhelper.client.Main;

public class Landscape {

    static boolean[][] vismap;

    static int renderMinX;
    static int renderMinY;
    static int renderMaxX;
    static int renderMaxY;

    static int camX;
    static int camY;

    public static void update() {

        RSClient client = Main.client;
        RSLandscape landscape = client.getLandscape();

        final int pitch = client.getPitch();
        final int yaw   = client.getYaw();

        if( pitch==0 && yaw==0 ) return;

        vismap = landscape.getVisibilityMap()[(pitch-128)/32][yaw/64];

        camX = client.getCameraX()/128;
        camY = client.getCameraY()/128;

        renderMinX = camX - 25;
        renderMinY = camY - 25;
        renderMaxX = camX + 25;
        renderMaxY = camY + 25;

        if(renderMinX < 0)   renderMinX = 0;
        if(renderMinY < 0)   renderMinY = 0;
        if(renderMinX > 104) renderMaxX = 104;
        if(renderMinY > 104) renderMaxY = 104;

    }

    public static boolean cull(int rx, int ry) {
        rx -= renderMinX;
        ry -= renderMinY;
        return !(rx > 0 && ry < 50 && ry > 0 && ry < 50) || !vismap[rx][ry];
    }


    public static void accept(LandscapeVisitor visitor) {
        RSLandscape ls = Main.client.getLandscape();
        for(int floor = 0; floor < 4; floor++) {
            for(int x = 0; x < 104; x++) {
                for(int y = 0; y < 104; y++) {
                    RSEntityMarker m = ls.getObjectAt(floor,x,y);
                    if(m == null) continue;
                    visitor.acceptObject(m);
                }
            }
        }
    }
}
