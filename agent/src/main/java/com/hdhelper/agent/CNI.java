package com.hdhelper.agent;

import com.hdhelper.agent.net.JAVConfig;
import com.hdhelper.agent.services.RSClient;

import java.applet.Applet;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// Client Native Interface
public final class CNI {

    private boolean did_init = false;
    private boolean did_start = false;
    private ClassLoader loader;
    private Class client_class;
    private CNIStub stub;
    private RSClient game;
    private CNIRuntimeArgs args;

    private CNI(ClassLoader loader) {
        this.loader = loader;
    }

    public static CNI get(ClassLoader loader) {
        return new CNI(loader);
    }

    public void init(CNIRuntimeArgs args) throws Exception {
        if(did_init) return;
        stub = new CNIStub(getConfig());
        client_class = loader.loadClass("client");
        this.args = args;
        initCNI(client_class,this,args);
        game = (RSClient) client_class.newInstance(); //Empty constructor
        did_init = true;
    }

    private static void initCNI(Class client, CNI cni, CNIRuntimeArgs args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method init = client.getDeclaredMethod("initCNI", CNI.class, CNIRuntimeArgs.class);
        init.setAccessible(true);
        init.invoke(null,cni,args);
    }

    private JAVConfig cfg = null;
    private JAVConfig getConfig() throws IOException {
        if(cfg != null) return cfg;
        InputStream cfg_stream = loader.getResourceAsStream("META-INF/config.ws");
        if(cfg_stream == null)
            throw new RuntimeException("config is missing");
        JAVConfig cfg = JAVConfig.decode(cfg_stream);
        if(cfg == null)
            throw new RuntimeException("config is corrupt");
        this.cfg = cfg;
        return cfg;
    }


    public void initAndStartGame() {

        if(!did_init)
            throw new IllegalStateException("CNI must be initialized");
        if(did_start) // One engine per CNI
            return;

        Applet app = (Applet) game;
        app.setStub(stub);
        app.init();
        app.start();

        did_start = true;

        // Start the game engine
    }

    public void stop() {
        // Stop the game engine

    }

    public void destroy() {
        // Destroy the game engine, and this interface

    }

    // Get a reference to the game engine
    public RSClient get() {
        // Get a reference to the game engine
        if(!did_start)
            throw new IllegalStateException("Engine not started");
        return game;
    }

    // Get the class loader used to load the client and this interface
    public ClassLoader getLoader() {
        return loader;
    }

    public Class getService(String name) {
        return null;
    }

}
