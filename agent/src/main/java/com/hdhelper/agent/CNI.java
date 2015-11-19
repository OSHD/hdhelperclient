package com.hdhelper.agent;

import com.hdhelper.agent.net.JAVJavaConfig;
import com.hdhelper.agent.services.RSClient;

import java.applet.Applet;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

// Client Native Interface
public final class CNI {

    private final File client;
    private boolean did_init = false;
    private boolean did_start = false;
    private ClassLoader loader;
    private Class client_class;
    private CNIStub stub;
    private RSClient game;

    private CNI(File client) {
        this.client = client;
    }

    public static CNI get(File client) {
        return new CNI(client);
    }

    public void init(CNIRuntimeArgs args) throws Exception {
        if(did_init) return;
        loader = new URLClassLoader(new URL[]{client.toURL()});
         //TODO force a strict classloader to ensure no references to the app are made. SecurityManager should work?
        stub = new CNIStub(getConfig());
        client_class = loader.loadClass("client");
        initCNI(client_class, args);
        did_init = true;
    }

    private static void initCNI(Class client, CNIRuntimeArgs args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method init = client.getDeclaredMethod("initCNI", CNIRuntimeArgs.class);
        init.setAccessible(true);
        init.invoke(null,args);
    }

    private JAVJavaConfig cfg = null;
    private JAVJavaConfig getConfig() throws IOException {
        if(cfg != null) return cfg;
        InputStream cfg_stream = loader.getResourceAsStream("META-INF/config.ws");
        if(cfg_stream == null)
            throw new RuntimeException("config is missing");
        JAVJavaConfig cfg = JAVJavaConfig.decode(cfg_stream);
        if(cfg == null)
            throw new RuntimeException("config is corrupt");
        this.cfg = cfg;
        return cfg;
    }

    public void start() {

        if(!did_init)
            throw new IllegalStateException("CNI must be initialized, call init()");
        if(did_start) // One engine per CNI
            return;

        try {
            game = (RSClient) client_class.newInstance();
            Applet app = (Applet) game;
            app.setStub(stub);
            app.init();
            app.start();
            did_start = true;
        } catch (InstantiationException ignored) {
            ignored.printStackTrace();
        } catch (IllegalAccessException ignored) {
            ignored.printStackTrace();
        }

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
