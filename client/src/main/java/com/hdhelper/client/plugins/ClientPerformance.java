package com.hdhelper.client.plugins;

import com.hdhelper.client.api.ge.RTFont;
import com.hdhelper.client.api.ge.RTFontImpl;
import com.hdhelper.client.api.ge.RTGlyphVector;
import com.hdhelper.client.api.ge.RTGraphics;
import com.hdhelper.client.api.plugin.Plugin;

import java.awt.*;

public class ClientPerformance extends Plugin {

    private RTFont font;

    @Override
    public void init() {
        font = new RTFontImpl(RTGlyphVector.getB12Full());
    }

    @Override
    public void render(RTGraphics g) {

        font.setGraphics(g);

        int fps = client.getFps();

        Runtime var7 = Runtime.getRuntime();
        int used_memory = (int) ((var7.totalMemory() - var7.freeMemory()) / (1024L*1024L));

        font.drawString("Fps:" + String.valueOf(fps), 15, 20, Color.YELLOW.getRGB());
        font.drawString("Memory:" + String.valueOf(used_memory) + "m", 15, 35, Color.YELLOW.getRGB(), Color.BLACK.getRGB());

    }

}
