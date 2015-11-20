package com.hdhelper.client.api.plugin;

import com.hdhelper.client.api.ge.RTGraphics;

public abstract class Plugin {

    protected boolean enabled;

    public Plugin() {
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






    public void render(RTGraphics g) {
    }

}