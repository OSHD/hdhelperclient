package com.hdhelper.agent;

import java.awt.*;

public class ClientCanvas extends Canvas {

    protected int[] engine_raster;
    protected Image engine_img;
    protected int reshape_count = 0;

    static {
        AgentSecrets.setClientCanvasAccess(new ClientCanvasAccess() {
            @Override
            public void setBitmap(ClientCanvas target, int[] raster, Image representative) {
                target.setBitmap(raster, representative);
            }
        });
    }

    public ClientCanvas() {
        setIgnoreRepaint(true);
    }

    //Callbacked
    private void setBitmap(int[] raster, Image img) {
        engine_raster = raster;
        engine_img = img;
        reshape_count++;
    }

}

