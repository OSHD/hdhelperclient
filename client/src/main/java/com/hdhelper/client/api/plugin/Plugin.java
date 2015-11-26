package com.hdhelper.client.api.plugin;

import com.hdhelper.agent.services.RSClient;
import com.hdhelper.client.Game;
import com.hdhelper.client.api.ge.Overlay;
import com.hdhelper.client.api.ge.RTGraphics;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class Plugin implements Overlay {

    protected final RSClient client;
    private PluginStub stub;



    public Plugin() {
        client = Game.get();
    }

    public final void setStub(PluginStub stub) {
        this.stub = stub;
    }

    public URL getCodeBase() {
        return stub.getCodeBase();
    }

    public PluginContext getPluginContext() {
        return stub.getPluginContext();
    }

    public URL getResource(String name) {
        try {
            return new URL(getCodeBase(), name);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    /**
     * Called by the environment to inform the plugin that it has
     * been loaded into the system. It is always called before the
     * first time the start method is called.
     *
     * A subclass of Plugin should override this method if
     * it has 'heavy' initialization to perform. For example,
     * an plugin with threads would use the init method to create the
     * threads and the destroy method to kill them.
     */
    public void init() {
    }

    /**
     * Called by the environment to inform this plugin that it should start
     * its execution. It is called after the init method and each time
     * the plugin is re-enabled.
     *
     * A subclass of Plugin should override this method if
     * it has any operation that it wants to perform each time the
     * Plugin is re-enabled.
     *
     * For example, an plugin with time-operations might want to
     * use the start method to resume clocks, and the stop method
     * to suspend the timers.
     */
    public void start() {
    }

    /**
     * Called by the environment to inform= this plugin that
     * it should stop its execution. It is called when the
     * plugin has been disabled, and also just before the plugin
     * is to be destroyed.
     *
     * A subclass of Plugin should override this method if
     * it has any operation that it wants to perform each time
     * its disabled.
     *
     * Plugins may maintain initialized data when disabled/stop, for
     * they might be resumed/started again later on. Plugins are
     * revoked of any graphical object to render upon when not started.
     */
    public void stop() {
    }

    /**
     * Called by the environment to inform this applet that it is
     * being reclaimed and that it should destroy any resources that
     * it has allocated. The stop method will always be called before destroy.
     *
     * A subclass of Plugin should override this method if
     * it has any operation that it wants to perform before it is
     * destroyed. For example, an plugin with threads would use the
     * init method to create the threads and the destroy method to kill them.
     */
    public void destroy() {
    }


    /**
     * Render any graphical information into the frame represented
     * by the provided graphical object. The method is called when
     * the from the game-engine thread, or when the game engine is
     * parked. This method is only called when the plugin is started.
     * This method should complete as soon as possible and never
     * throw any errors or cause deadlocks.
     */
    @Override
    public void render(RTGraphics g) {
    }

}