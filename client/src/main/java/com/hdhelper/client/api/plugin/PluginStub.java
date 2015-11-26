package com.hdhelper.client.api.plugin;

import java.net.URL;

public interface PluginStub {

    /**
     * Gets the base URL. This is the URL of the directory in which this plugin stores any data.
     *
     * @return the base {@link java.net.URL} of
     *          the directory which contains the plugin data.
     */
    URL getCodeBase();

    /**
     * Returns the plugin's context.
     *
     * @return  the plugin's context.
     */
    PluginContext getPluginContext();

}
