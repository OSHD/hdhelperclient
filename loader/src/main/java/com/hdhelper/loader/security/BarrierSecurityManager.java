package com.hdhelper.loader.security;

import com.hdhelper.agent.ClientLoader;

import java.security.Permission;

public class BarrierSecurityManager extends SecurityManager {

    public BarrierSecurityManager() {
        super();
    }

    @Override
    public void checkPermission(Permission perm) {
        return;
    }

    @Override
    public void checkPackageAccess(String p) {
        if(isGameClass()) {
            //white list of classes the client may access
            if(p.startsWith("java")) return;
            if(p.startsWith("com.hdhelper.agent")) return;
            throw new SecurityException("Illegal client-access:" + p); // Enforce we don't do anything bad
        }
    }

    private boolean isGameClass() {
        return getClientClassLoader()!=null;
    }

    private ClientLoader getClientClassLoader() {
        Class[] var2 = this.getClassContext();
        int var3;
        ClassLoader var1;
        for (var3 = 0; var3 < var2.length; ++var3) {
            var1 = var2[var3].getClassLoader();
            if (var1 instanceof ClientLoader) {
                return (ClientLoader) var1;
            }
        }
        return null;
    }

}
