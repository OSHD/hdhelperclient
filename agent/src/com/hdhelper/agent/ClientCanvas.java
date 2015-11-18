package com.hdhelper.agent;

import com.hdhelper.api.ge.RTGraphics;
import com.hdhelper.api.ge.impl.Debug;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class ClientCanvas extends Canvas {

    private BufferedImage lastFrame;
    private BufferedImage backBuffer;

    private RTGraphics g = null;
    private static int[] engine_raster;
    private static Image engine_img;
    private static int reshape_count = 0;

    private static int[] offscreen_buffer;

    public static int mouseX = 0;
    public static int mouseY = 0;

    private int cur_shape = 0;

    public ClientCanvas() {
        super();
        super.setIgnoreRepaint(true);

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
    }

    //Callbacked
    public static void reshape(int[] raster, Image img) {
        engine_raster = raster;
        engine_img = img;
        reshape_count++;
    }

    private void validateGraphics() {
        if(cur_shape != reshape_count) {
            final int w = engine_img.getWidth(null);
            final int h = engine_img.getHeight(null);
            assert w == this.getWidth() && h == this.getHeight();
            if(g != null) g.flush();
            offscreen_buffer = new int[w*h+1];
            g = new RTGraphics(offscreen_buffer,w,h);
            cur_shape = reshape_count;
        }
    }

    Debug debug;

    void draw00(RTGraphics g) {

        if(engine_raster == null || engine_raster.length == 1) {
            return;
        }

        if(debug == null) {
            debug = new Debug();
        }

        debug.render(g);

    }



    @Override
    public Graphics getGraphics() {
        // ... Frame complete...
        validateGraphics();

        System.arraycopy(engine_raster, 0, offscreen_buffer, 0, engine_raster.length);
        draw00(g);
        Graphics canvasG = super.getGraphics();
        canvasG.drawImage(g.crate(),0,0,null); //Draw the game

        return new DebugGraphics(canvasG) { // Prevent runescape from rendering //TODO inject into the engine not to draw its frame
            @Override
            public boolean drawImage(Image m, int x, int y, ImageObserver o) {
                return false;
            }
        };

    }

}

