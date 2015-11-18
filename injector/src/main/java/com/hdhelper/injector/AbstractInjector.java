package com.hdhelper.injector;

import java.util.jar.JarFile;

public abstract class AbstractInjector {

    public final InjectorConfig cfg;

    public AbstractInjector(InjectorConfig cfg) {
        if(cfg == null)
            throw new IllegalArgumentException("cfg == null");
        this.cfg = cfg;
    }

    // Inject the CNI into the target jar and establish a JarFile in memory.
    // The returned jar file is to not be stored to a local file by the injector.
    // Throw any reasons why the injection could not be performed.
    public abstract JarFile inject(JarFile target) throws Exception;

    // Verify the existing jarfile is properly injected, and not tampered with.
    // Throw any invalidating reason.
    public abstract boolean verifyExisting(JarFile injected) throws Exception;

    // Gives the injector a change to clean up and release any system resources.
    public void destroy() {
    }

}
