package com.hdhelper.client.plugins;

import com.hdhelper.agent.services.RSPlayer;
import com.hdhelper.client.api.W2S;
import com.hdhelper.client.api.ge.*;
import com.hdhelper.client.api.plugin.Plugin;

import java.awt.*;

public class ClanView extends Plugin {

    RTFont p12_full;

    public ClanView() {
    }

    @Override
    public void init() {
        p12_full = new RTFontImpl(RTGlyphVector.getP12Full());
    }


    @Override
    public void render(RTGraphics g) {

        p12_full.setGraphics(g);
        RSPlayer me = client.getMyPlayer();
        if(me == null) return;

        final int my_team = me.getTeam();
        final int floor = client.getFloor();

        if(my_team == 0) return; // We're not in any team

        Point C =  Minimap.tileToMinimap(me.getStrictX()>>7, me.getStrictY()>>7);

        for(RSPlayer p : client.getPlayers()) {

            if(p == null) continue;
            if(p.getTeam() == my_team) {

                if(p.getHeight() == 1000) continue; //Not rendered

                final int rx = p.getStrictX() >> 7;
                final int ry = p.getStrictY() >> 7;

                W2S.draw3DBox(floor, rx, ry, p.getHeight(), g, Color.GREEN.getRGB());

                Point P = Minimap.tileToMinimap(rx, ry);

                g.fillRectangle(P.x - 2, P.y - 2, 4, 4, Color.YELLOW.getRGB());

                g.drawLine(C,P,Color.RED.getRGB());

                p12_full.drawCenterString(p.getName(), P.x, P.y, Color.GREEN.getRGB(), Color.RED.getRGB());

            }
        }
    }


}
