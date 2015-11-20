package com.hdhelper.agent;

import java.awt.*;

public class ClientCanvas extends Canvas {

    protected int[] engine_raster;
    protected Image engine_img;
    protected int reshape_count = 0;

    private Component delegate;

    static {
        AgentSecrets.setClientCanvasAccess(new ClientCanvasAccess() {
            @Override
            public void setBitmap(ClientCanvas target, int[] raster, Image representative) {
                target.setBitmap(raster, representative);
            }
            @Override
            public void setDelegate(ClientCanvas target, Component c) {
                target.delegate = c;
            }
        });
    }

    public ClientCanvas() {
    }

    //Callbacked
    private void setBitmap(int[] raster, Image img) {
        engine_raster = raster;
        engine_img = img;
        reshape_count++;
    }

    @Override
    public final void update(Graphics g) {
        delegate.update(g);
        update0(g);
    }

    @Override
    public final void paint(Graphics g) {
        delegate.paint(g);
        paint0(g);
    }

    @Override
    public Graphics getGraphics() {
        return super.getGraphics();
    }

    public void update0(Graphics g) {
    }

    public void paint0(Graphics g) {
    }

}

