package com.hdhelper.agent;

import com.hdhelper.agent.services.RSClient;

import java.io.File;

// Client Native Interface
public final class CNI {

    private CNI() {
    }

    public static CNI get(File f) {
        return null;
    }

    public void init(CNIRuntimeArgs args) {
        // Initialize the CNI
    }

    public void start() {
        // Start the game engine
    }

    public void stop() {
        // Stop the game engine
    }

    public void destroy() {
        // Destory the game engine, and this interface
    }

    // Get a reference to the game engine
    public RSClient get() {
        // Get a reference to the game engine
        return null;
    }

    // Get the class loader used to load the client and this interface
    public ClassLoader getLoader() {
        return null;
    }

    public Class getService(String name) {
        return null;
    }
}
