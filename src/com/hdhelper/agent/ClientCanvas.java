package com.hdhelper.agent;

import com.hdhelper.api.ge.RTGraphics;
import com.hdhelper.api.ge.impl.Debug;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ClientCanvas extends Canvas {

    private BufferedImage rawImage;
    private BufferedImage backBuffer;

    private RTGraphics g = null;
    private static int[] engine_raster;
    private static Image engine_img;
    private static int reshape_count = 0;

    private int cur_shape = 0;

    public ClientCanvas() {
        super();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screen.getWidth();
        int height = (int) screen.getHeight();
        rawImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        backBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        System.out.println("LOADED");
    }

    //Callbacked
    public static void reshape(int[] raster, Image img) {
        engine_raster = raster;
        engine_img = img;
        reshape_count++;

    }


    void validateGraphics() {
        if(cur_shape != reshape_count) {
            final int w = engine_img.getWidth(null);
            final int h = engine_img.getHeight(null);
            if(g != null) g.flush();
            g = new RTGraphics(engine_raster,w,h);
            cur_shape = reshape_count;
        }
    }






    Debug debug;

    void draw00(RTGraphics g) {

        if(debug == null) {
            debug = new Debug();
        }
        debug.render(g);

    }



    void draw0(Graphics2D g0) {

        if(engine_raster == null || engine_raster.length == 1) {
            return;
        }

        validateGraphics();

        RTGraphics rtg = g;

        draw00(rtg);

        Image rtg_image = rtg.crate();
        assert rtg_image != null;

        g0.drawImage(rtg_image, 0, 0, null);

    }


    @Override
    public Graphics getGraphics() {

        Graphics g = super.getGraphics();
        Graphics2D paint = (Graphics2D) backBuffer.getGraphics();
        paint.clearRect(0, 0, getWidth(), getHeight());
        paint.drawImage(rawImage, 0, 0, null);
        draw0(paint);
        paint.dispose();
        g.drawImage(backBuffer, 0, 0, null);
        backBuffer.flush();
        g.dispose();
        Graphics rawG = rawImage.getGraphics();
        rawImage.flush();

        return rawG;

    }

}

