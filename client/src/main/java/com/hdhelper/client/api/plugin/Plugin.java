package com.hdhelper.client.api.plugin;

import com.hdhelper.agent.services.RSClient;
import com.hdhelper.client.Main;
import com.hdhelper.client.api.ge.Overlay;
import com.hdhelper.client.api.ge.RTGraphics;

public abstract class Plugin implements Overlay {

    protected boolean enabled;
    protected final RSClient client;

    public Plugin() {
        client = Main.client;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }


    public String getPluginInfo() {
        return null;
    }




    public void init() {
    }

    public void start() {
    }

    public void stop() {
    }

    public void destroy() {
    }





    @Override
    public void render(RTGraphics g) {
    }

}