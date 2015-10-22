package com.hdhelper;

import com.hdhelper.agent.ByteClassLoader;
import com.hdhelper.agent.Injector;
import com.hdhelper.agent.io.ParameterLoader;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Context implements AppletStub {

    private static final Logger LOG = Logger.getLogger(Context.class.getName());

    public final HashMap<String, String> client_parameters;
    public final String gamepack;

    public ByteClassLoader CLASS_LOADER;

    public Applet applet;


    public static Context create() throws IOException {
        return new Context();
    }


    private static String getServerAddress(int world) {
        return "http://oldschool" + world + ".runescape.com";
    }

    private Context() throws IOException {

        Map<String, byte[]> classes;

        try {

            LOG.info("Injecting...");

            classes = new Injector().inject();

            LOG.info("Fetching parameters...");

            String clientSource = getServerAddress(Environment.WORLD);
            client_parameters = ParameterLoader.getParameters(new URL(clientSource));
            gamepack = clientSource + "/" + client_parameters.get("archive");

            LOG.info("Gamepack: " + gamepack);

        } catch (final Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(); //silly finalizers
        }

        CLASS_LOADER = new ByteClassLoader(classes);

        CLASS_LOADER.save(Environment.INJECTED);

        CLASS_LOADER.verify();

        try {
            LOG.info("Starting client...");
            final Class<?> client = CLASS_LOADER.loadClass("client");
            this.applet = (Applet) client.newInstance();
            applet.setStub(this);
            applet.init();
            applet.start();
            LOG.info("Client started!");
        } catch (final Exception e) {
            e.printStackTrace();
        }

        LOG.info("Done...");

    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public URL getDocumentBase() {
        try {
            return new URL(gamepack);
        } catch (final MalformedURLException e) {
            return null;
        }
    }

    @Override
    public URL getCodeBase() {
        try {
            return new URL(gamepack);
        } catch (final MalformedURLException e) {
            return null;
        }
    }

    @Override
    public String getParameter(final String name) {
        return client_parameters.get(name);
    }

    @Override
    public AppletContext getAppletContext() {
        return null;
    }

    @Override
    public void appletResize(final int width, final int height) {
    }
}
