package com.hdhelper.client.api.ge;


public class BasicOverlay implements Overlay {

    @Override
    public final void render(RTGraphics g0) {

        final int[] raster = g0.raster;
        final int rw       = g0.rasterWidth;
        final int rh       = g0.rasterHeight;

        paint(g0);

        g0.setRaster(raster,rw,rh); //Set the raster back

    }

    public void paint(RTGraphics g) {
    }

}
