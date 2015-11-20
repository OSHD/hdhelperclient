package com.hdhelper.loader.security;

import com.hdhelper.agent.ClientLoader;

import java.security.*;

public class BarrierPolicy extends Policy {

    @Override
    public PermissionCollection getPermissions(ProtectionDomain domain) {
        if (isPlugin(domain)) {
            return pluginPermissions();
        }
        else {
            return applicationPermissions();
        }
    }

    private static boolean isPlugin(final ProtectionDomain domain) {
        return domain.getClassLoader() instanceof ClientLoader;
    }

    private PermissionCollection pluginPermissions() {
        Permissions permissions = new Permissions(); // No permissions
        return permissions;
    }

    private PermissionCollection applicationPermissions() {
        Permissions permissions = new Permissions();
        permissions.add(new AllPermission());
        return permissions;
    }

}
