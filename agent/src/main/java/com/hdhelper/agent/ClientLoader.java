package com.hdhelper.agent;

import java.io.ByteArrayInputStream;
import java.io.FilePermission;
import java.io.InputStream;
import java.net.URL;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.SecureClassLoader;
import java.util.HashMap;
import java.util.Map;

public final class ClientLoader extends SecureClassLoader {

    private Map<String, byte[]> resources = new HashMap<String, byte[]>();

    private final Map<String, Class<?>> loaded  = new HashMap<String,Class<?>>();
    private final Map<String, Class<?>> defined = new HashMap<String,Class<?>>();

    public ClientLoader(Map<String, byte[]> resources) {
        this.resources = secureResources(resources);
    }

    public void destroy() { //TODO remove/revoke any permissions given to the client?
        loaded.clear();
        defined.clear();
        resources.clear();
    }

    // Remove references to eateries and data to ensure its finalized
    private static Map<String,byte[]> secureResources(Map<String, byte[]> src) {
        Map<String,byte[]> dest = new HashMap<String, byte[]>();
        for(Map.Entry<String,byte[]> entry : src.entrySet()) {
            String name = entry.getKey();
            byte[] unsafeData = entry.getValue();
            byte[] safeData = new byte[unsafeData.length];
            System.arraycopy(unsafeData,0,safeData,0,unsafeData.length);
            dest.put(name,safeData);
        }
        return dest;
    }


    @Override
    public URL getResource(String name) { //TODO
        return null;
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        byte[] data = resources.get(name);
        if(data == null) return null;
        return new ByteArrayInputStream(data.clone()); // Ensure Read-Only
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (loaded.containsKey(name)) {
            return loaded.get(name);
        } else if (!resources.containsKey(name)) {
            return super.loadClass(name);
        }
        if (defined.containsKey(name)) {
            return defined.get(name);
        } else {
            final byte[] bytes = resources.get(name);
            final Class<?> clazz = super.defineClass(name, bytes, 0, bytes.length);
            loaded.put(name, clazz);
            defined.put(name, clazz);
            return clazz;
        }
    }

    @Override
    protected PermissionCollection getPermissions(CodeSource codesource) {
        System.out.println(codesource);
        Permissions permissions = new Permissions();
        permissions.add(new FilePermission("/some/file", "read"));
        return permissions;
    }

}
