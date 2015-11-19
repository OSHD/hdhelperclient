package com.hdhelper.agent;

import com.hdhelper.agent.net.JAVJavaConfig;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.applet.AudioClip;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Enumeration;
import java.util.Iterator;

public class CNIStub implements AppletStub, AppletContext {

    private final JAVJavaConfig cfg;
    public CNIStub(JAVJavaConfig cfg) {
        this.cfg = cfg;
    }

    @Override
    public AudioClip getAudioClip(URL url) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Image getImage(URL url) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Applet getApplet(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Enumeration<Applet> getApplets() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void showDocument(URL url) {
        //TODO implement popup?
        throw new UnsupportedOperationException();
    }

    @Override
    public void showDocument(URL url, String target) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void showStatus(String status) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setStream(String key, InputStream stream) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getStream(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<String> getStreamKeys() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public URL getDocumentBase() {
        try {
            return new URL(cfg.getCodeBase());
        } catch (MalformedURLException e) {
            throw new InvalidParameterException();
        }
    }

    @Override
    public URL getCodeBase() {
        try {
            return new URL(cfg.getCodeBase());
        } catch (MalformedURLException e) {
            throw new InvalidParameterException();
        }
    }

    @Override
    public String getParameter(String name) {
        return cfg.getParam(name);
    }

    @Override
    public AppletContext getAppletContext() {
        return this;
    }

    @Override
    public void appletResize(int width, int height) {
    }

}
