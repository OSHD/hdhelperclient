package com.hdhelper.api.ge;


public class BasicOverlay implements Overlay {

    RTGraphics g;
    int[] buffer;
    boolean unsafe=true;

    @Override
    public final void render(RTGraphics g0) {

        if(unsafe) {

            paint(g0);

        } else {

            if(buffer == null || buffer.length != g0.raster.length) {
                buffer = new int[g0.raster.length];
                g = new RTGraphics();
                g.setRaster(buffer,g0.rasterWidth,g0.rasterHeight);
            }

            final int l = g0.raster.length;

            System.arraycopy(g0.raster, 0, buffer, 0, l);

            paint(g);

            System.arraycopy(buffer, 0, g0.raster, 0, l);

        }


    }

    public void paint(RTGraphics g) {
    }

}
