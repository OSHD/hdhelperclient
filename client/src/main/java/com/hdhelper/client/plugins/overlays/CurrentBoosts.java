package com.hdhelper.client.plugins.overlays;

import com.hdhelper.client.api.Skill;
import com.hdhelper.client.api.ge.RTFont;
import com.hdhelper.client.api.ge.RTFontImpl;
import com.hdhelper.client.api.ge.RTGlyphVector;
import com.hdhelper.client.api.ge.RTGraphics;
import com.hdhelper.client.api.plugin.Plugin;
import com.hdhelper.client.ui.HDCanvas;

import java.awt.*;
import java.util.*;

public class CurrentBoosts extends Plugin {

    RTFont font;
    RTFont bold;

    @Override
    public void init() {
        font = new RTFontImpl(RTGlyphVector.getP12Full());
        bold = new RTFontImpl(RTGlyphVector.getB12Full());
        lastTick = System.currentTimeMillis();
    }

    int[] prevLevels;
    long lastTick;

    int getTheoreticalRemaining() {
        long timeSinceLastTick = (System.currentTimeMillis() - lastTick)/1000;
        if(timeSinceLastTick >= 60) return 0; //Error
        return (int) (60 - timeSinceLastTick);
    }

    private boolean serverDidTick() {
        int[] curLevels = client.getCurrentLevels();
        if(prevLevels == null) {
            prevLevels = curLevels.clone();
            return false;
        } else {
            boolean server_ticked = false;
            for(int i = 0; i < curLevels.length; i++) {
                if(curLevels[i] != prevLevels[i]) { //Level changed
                    prevLevels[i] = curLevels[i];
                    server_ticked = true;
                }
            }
            return server_ticked;
        }
    }

    int getRemaining() {
        if(serverDidTick()) {
            lastTick = System.currentTimeMillis();
        }
        return getTheoreticalRemaining();
    }

    @Override
    public void render(RTGraphics g) {

        //Skill Boots:

        RTFont font = this.font;

        int x = HDCanvas.mouseX;
        int y = HDCanvas.mouseY;

        int[] curLevels = client.getCurrentLevels();
        int[] realLevels = client.getLevels();
        int remaining = getRemaining();



        class Boost {
            String skill;
            String info;
        }

        java.util.List<Boost> boosts = new ArrayList<Boost>();

        for(int i = 0; i < curLevels.length; i++) {

            int curLevel = curLevels[i];
            int realLevel = realLevels[i];

            int change = curLevel - realLevel;

            if(change == 0) continue;

            String lvlColor = change > 0 ? "00FF00" : "FF0000";
            char changeChar = change > 0 ? '+' : '-';

            int minutesRemaining = change; // 1 level change takes 60 seconds, or 1 minute
            int secondsRemaining = remaining;

            String timeText = "";
            if(minutesRemaining > 1) timeText += (minutesRemaining + "m");
            if(secondsRemaining > 0) timeText += (secondsRemaining + "s");

            String boostText = "<u=" + lvlColor + "><col=" + lvlColor + ">" + curLevel + "</col></u>/" + realLevel
                             + "(<col=" + lvlColor + ">" + changeChar + change + "</col>)"
                             + " ETA:" + timeText;

            Boost boost = new Boost();
            boost.skill = Skill.values()[i].name();
            boost.info = boostText;

            boosts.add(boost);

        }

        if(boosts.isEmpty()) return;

        int boostHeight = font.getHeight() * 2 + 4;
        int height = font.getHeight() + boosts.size() * boostHeight;
        int width = 0;
        for(Boost boost : boosts) {
            int txtWidth = font.getStringWidth(boost.info);
            if(txtWidth > width) width = txtWidth;
        }

        width += 6;
        height += 6;

        g.fillRectangle(x, y, width, height, Color.BLACK.getRGB(), 128);
        g.drawRectangle(x, y, width, height, Color.BLUE.getRGB(), 128);

        x += 3;
        y += 3;

        font.setGraphics(g);
        bold.setGraphics(g);

        y += font.getHeight();
        y -= 3;

        font.drawString("Change in:" + remaining + "s", x, y - 2, Color.WHITE.getRGB());

        int index = 0;
        for(Boost boost : boosts) {

            bold.drawWordWrap("<u=FFFF00>" + boost.skill + "</u>",x,y+2, width, font.getHeight(), Color.ORANGE.getRGB(), -1, RTFont.ROW_LAYOUT_CENTER, RTFont.TEXT_LAYOUT_CENTER,0);
            font.drawString(boost.info, x, y + font.getHeight() * 2, Color.YELLOW.getRGB());
            if(index != (boosts.size()-1)) {
                g.drawHorizontalLine(x + 2, y + boostHeight + 1, width - 8, Color.BLUE.getRGB());
            }
            y += boostHeight + 1;
            index++;
        }

    }
    
}
