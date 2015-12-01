package com.hdhelper.client.plugins.overlays;

import com.hdhelper.client.api.Combat;
import com.hdhelper.client.api.ge.RTFont;
import com.hdhelper.client.api.ge.RTFontImpl;
import com.hdhelper.client.api.ge.RTGlyphVector;
import com.hdhelper.client.api.ge.RTGraphics;
import com.hdhelper.client.api.plugin.Plugin;
import com.hdhelper.client.ui.HDCanvas;

import java.awt.*;
import java.util.Arrays;

public class AttackStyle extends Plugin {

    RTFont font;

    @Override
    public void init() {
        font = new RTFontImpl(RTGlyphVector.getB12Full());
    }

    @Override
    public void render(RTGraphics g) {

        String[] skills = Combat.getStyleSkills();

        if(skills == null || skills.length == 0) return;

        String txt;

        if(skills.length == 1) {
            txt = skills[0];
        } else if(skills.length == 2) {
            txt = skills[0] + " and " + skills[1];
        } else {
            txt = Arrays.toString(skills);
        }


        boolean training_defence;
        for(String skill : skills) {
            if(skill.equals("Shared") || skill.equals("Defence")) {
                training_defence = true;
                break;
            }
        }


        int x = 375;
        int y = HDCanvas.height - 180;

        int width  = font.getStringWidth(txt) + 4;
        int height = font.getHeight();

        font.setGraphics(g);

        g.fillRectangle(x, y, width, height, Color.BLACK.getRGB(), 156);

        font.drawString(
                txt,
                x + 2,
                y + font.getMaxAscent() + (height - font.getHeight()) / 2,
                Color.GREEN.getRGB()
        );

    }


}
