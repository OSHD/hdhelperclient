package com.hdhelper.client.plugins.overlays;

import com.hdhelper.agent.services.RSImage;
import com.hdhelper.client.api.Skill;
import com.hdhelper.client.api.ge.*;
import com.hdhelper.client.api.plugin.Plugin;

import java.awt.*;
import java.util.ArrayList;

public class CurrentBoosts extends Plugin {

    RTFont font;
    RTFont bold;

    RTImage[] skillImages;


    @Override
    public void init() {
        font = new RTFontImpl(RTGlyphVector.getP12Full());
        bold = new RTFontImpl(RTGlyphVector.getB12Full());
        lastTick = System.currentTimeMillis();
        skillImages = new RTImage[Skill.values().length];
        for(Skill skill : Skill.values()) {
            int imageId = CachedImaged.SKILL2IMAGE[skill.getId()];
            RSImage img0 = CachedImaged.getImage(imageId);
            RTImage img = RTImage.create(img0,false);
            img.crop();
            skillImages[skill.getId()] = img;
        }
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

        int x = 15;
        int y = 45;

        int[] curLevels = client.getCurrentLevels();
        int[] realLevels = client.getLevels();
        int remaining = getRemaining();



        class Boost {
            Skill skill;
            String info;
            int infoWidth;
        }

        java.util.List<Boost> boosts = new ArrayList<Boost>();

        for(int i = 0; i < curLevels.length; i++) {

            if(i == Skill.PRAYER.getId()) continue;

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
            boost.skill = Skill.values()[i];
            boost.info = boostText;

            boosts.add(boost);

        }

        if(boosts.isEmpty()) return;

        int boostHeight = 25 + 2;
        int height = font.getHeight() + boosts.size() * boostHeight + 4 + 4 + 4;
        int width = 0;
        for(Boost boost : boosts) {
            int txtWidth = font.getStringWidth(boost.info);
            boost.infoWidth = txtWidth;
            if(txtWidth > width) width = txtWidth;
        }

        width += 25 + 4 + 4 + 4;


        g.fillRectangle(x, y, width, height, Color.BLACK.getRGB(), 128);

        g.drawRectangle(x, y, width, height, Color.BLACK.getRGB(), 64);
        g.drawRectangle(x+1, y+1, width-2, height-2, Color.BLACK.getRGB(), 64);

        x++;
        y++;

        font.setGraphics(g);
        bold.setGraphics(g);


        y += font.getHeight();

        font.drawCenterString("Change in:" + remaining + "s", x + width / 2, y - 2, Color.WHITE.getRGB());

        g.drawHorizontalLine(x+2,y+4,width-10,Color.BLACK.getRGB());

        y += 6;

        int index = 0;
        for(Boost boost : boosts) {

            RTImage img = skillImages[boost.skill.getId()];

            img.setGraphics(g);
            img.f(x, y + ((boostHeight - 25) / 2)-1);

            int x0 = x;
            x += (img.getWidth() + 4);

            font.drawString(boost.info, x, y + boostHeight/2 + 4, Color.YELLOW.getRGB());
            if(index != (boosts.size()-1)) {
                g.drawHorizontalLine(x0 + 2, y + boostHeight + 1, width - 10, Color.BLACK.getRGB());
            }
            y += boostHeight + 4;
            x = x0;
            index++;
        }

    }

}
