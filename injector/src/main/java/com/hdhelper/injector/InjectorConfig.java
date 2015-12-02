package com.hdhelper.injector;

import java.io.File;
import java.util.logging.Logger;

public class InjectorConfig { // We should not have any references to user provided objects

    private Logger logger;
    private File outLoc;
    private boolean useCaches; // If we have an inject client, and its valid, should we use that?

    public InjectorConfig() {
    }


    //Verify config and establish defaults (if possible):
    //TODO verifyException
    void verify() {
        if(outLoc == null) { //TODO default?
            throw new Error("dest == null");
        }
        if(logger == null) {
            logger = Logger.getLogger("injector"); // Default
        }
    }



    // Getters:

    public Logger getLogger() {
        return logger;
    }

    public File getOutputFile() {
        return outLoc;
    }

    public boolean useCaches() {
        return useCaches;
    }


    // Setters:

    public InjectorConfig setLogger(Logger logger) {
        this.logger = logger;
        return this;
    }

    public InjectorConfig setOutputLoc(File loc) {
        this.outLoc = loc;
        return this;
    }

    public InjectorConfig setUseCaches(boolean caches) {
        this.useCaches = caches;
        return this;
    }

}