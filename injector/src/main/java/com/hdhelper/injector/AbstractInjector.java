package com.hdhelper.injector;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public abstract class AbstractInjector {

    public final InjectorConfig cfg;

    public AbstractInjector(InjectorConfig cfg) {
        if(cfg == null)
            throw new IllegalArgumentException("cfg == null");
        this.cfg = cfg;
    }

    // Inject the CNI into the target jar and establish a new JarFile.
    // Throw any reasons why the injection could not be performed.
    public abstract Map<String,byte[]> inject(JarFile target) throws Exception;

    // Verify the existing jarfile is properly injected, and not tampered with.
    // Throw any invalidating reason.
    public abstract boolean verifyExisting(JarFile injected) throws Exception;

    // Gives the injector a change to clean up and release any system resources.
    public void destroy() {
    }


    // The version of the injector that was used to inject the target client
    public static int getInjectorVersion(JarFile client) {
        ZipEntry entry = client.getEntry("META-INF/iv");
        if(entry == null) return -1;
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(client.getInputStream(entry));
            return dis.readInt();
        } catch (IOException ignored) {
        } finally {
            if(dis != null) {
                try {
                    dis.close();
                } catch (IOException ignored) {
                }
            }
        }
        return -1;
    }

}
