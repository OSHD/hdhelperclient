package com.hdhelper.client.api.plugin;

import javax.swing.*;

public interface ConfigurablePlugin {

    /**
     * Gets the configuration GUI that this plugin is creates to interface
     * configuration input from the user, if this plugin is configurable.
     */
    JPanel getConfigPanel();


}
