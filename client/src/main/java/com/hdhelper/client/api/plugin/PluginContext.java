package com.hdhelper.client.api.plugin;

import java.util.Enumeration;

/**
 * This interface corresponds to an plugins environment.
 */
public interface PluginContext {

    /**
     * Finds and returns the applet in the environment represented by this
     * plugin context with the given name.
     *
     * @param name The name of the plugin.
     * @return The plugin with the given name, or null if
     *         not found.
     */
    Plugin getPlugin(String name);

    /**
     * Finds all the plugins in the environment represented by this plugin
     * context.
     *
     * @return  an enumeration of all plugins in the environment represented by
     *          this plugin context.
     */
    Enumeration<Plugin> getPlugins();

}
