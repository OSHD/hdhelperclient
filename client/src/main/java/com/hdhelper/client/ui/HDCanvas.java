package com.hdhelper.client.ui;

import com.hdhelper.client.api.Game;
import com.hdhelper.client.api.ge.Overlay;
import com.hdhelper.client.api.ge.RTGraphics;
import com.hdhelper.client.api.ge.impl.Debug;
import com.hdhelper.client.api.plugin.Plugin;
import com.hdhelper.client.plugins.overlays.AltarLocator;
import com.hdhelper.client.plugins.overlays.ClanView;
import com.hdhelper.client.plugins.overlays.ClientPerformance;
import com.hdhelper.client.plugins.overlays.Cooking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

public class HDCanvas extends com.hdhelper.agent.ClientCanvas {


    private RTGraphics g = null;
    private int[] offscreen_buffer;
    private int cur_shape = 0;

    public static int mouseX = 0;
    public static int mouseY = 0;

    public HDCanvas() {
        super();
        setIgnoreRepaint(true);
        attach();
    }

    void attach() {
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
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


    boolean init = false;

    Plugin[] plugins = new Plugin[] {
            new AltarLocator(),
            new ClanView(),
            new ClientPerformance(),
            new Cooking()
    };

    void draw0(RTGraphics g) {

        if(engine_raster == null || engine_raster.length == 1) {
            return;
        }



        if(Game.isLoaded()) {

            if(debug == null) {
                debug = new Debug();
            }
            debug.render(g);

            if(!init) { //TODO init on luanch
                for(Plugin p : plugins) {
                    p.init();
                }
                init = true;
            }

            //Plugins...
            for(Plugin p : plugins) {
                renderSafe(p, g);
            }

        }


    }

    private static void renderSafe(Overlay o, RTGraphics g) {
        final int[] raster = g.raster;
        final int rw       = g.rasterWidth;
        final int rh       = g.rasterHeight;
        o.render(g);
        g.setRaster(raster,rw,rh); //Set the raster back
    }


    @Override
    public Graphics getGraphics() {
        assert reshape_count > 0;
        // ... Frame complete...
        validateGraphics();

        System.arraycopy(engine_raster, 0, offscreen_buffer, 0, engine_raster.length); //Update the offscreen frame

        draw0(g); // Draw on top this frame

        //Draw the final frame onto this canvas graphic
        Graphics canvasG = super.getGraphics();
        canvasG.drawImage(g.crate(),0,0,null); //Draw the game

        return new DebugGraphics(canvasG) { // Prevent the game from rendering //TODO inject into the engine not to draw its frame
            @Override
            public boolean drawImage(Image m, int x, int y, ImageObserver o) {
                return false;
            }
        };

    }

}
