package com.hdhelper.client.api.ge;

import com.hdhelper.agent.services.RSClient;
import com.hdhelper.agent.services.RSPlayer;
import com.hdhelper.client.Main;

import java.awt.*;

public class Minimap {

    public static Point tileToMinimap(int rx, int ry) {
        RSClient client = Main.client;
        RSPlayer me = client.getMyPlayer();
        final int x = (rx << 2) + 2;
        final int y = (ry << 2) + 2;
        return worldToMinimap(
                x - (me.getStrictX() >> 5),
                y - (me.getStrictY() >> 5)
        );
    }


    private static Point worldToMinimap(final int regionX, final int regionY) {


        RSClient client = Main.client;

     //   System.out.println(regionX + "," + regionY + "," + client.getMinimapRotation() + "," + client.getMinimapScale() + ","  + client.getViewRotation());

        final int angle = client.getMinimapRotation() + client.getMinimapScale() & 0x7FF;
        final int j = regionX * regionX + regionY * regionY;
        if (j > 6400) {
            return new Point(-1, -1);
        }
        final int sin = RTGraphics2D.SIN_TABLE[angle] * 256 / (client.getViewRotation() + 256);
        final int cos = RTGraphics2D.COS_TABLE[angle] * 256 / (client.getViewRotation() + 256);
        final int x = regionY * sin + regionX * cos >> 16;
        final int y = regionY * cos - regionX * sin >> 16;
        return new Point(client.getViewportWidth() - 78 + x, 83 - y); //TODO Grab the minimap
    }


}
