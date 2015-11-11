package com.hdhelper.api.ge;

public interface Overlay {

    void render(RTGraphics g);

    default void renderSafe(RTGraphics g) {
        final int[] raster = g.raster;
        final int rw       = g.rasterWidth;
        final int rh       = g.rasterHeight;
        render(g);
        g.setRaster(raster,rw,rh); //Set the raster back
    }

}
