package com.hdhelper.injector;

import java.util.jar.JarFile;

public class Injector2 extends AbstractInjector {

    public Injector2(InjectorConfig cfg) {
        super(cfg);
    }

    @Override
    public JarFile inject(JarFile target) throws Exception {

        return null;
    }



    @Override
    public boolean verifyExisting(JarFile injected) throws Exception {
        return false;
    }

}
