package com.hdhelper.injector;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class ByteClassLoader extends ClassLoader {

    private Map<String, byte[]> classes = new HashMap<String, byte[]>();

    private final Map<String, Class<?>> loaded  = new HashMap<String,Class<?>>();
    private final Map<String, Class<?>> defined = new HashMap<String,Class<?>>();

    public ByteClassLoader(final Map<String, byte[]> classes) {
        this.classes = classes;
    }

    //loads all the class so java would throw any verify errors if they existed -roughly-
    public void verify() {

        for(String clazz : classes.keySet()) {
            try { Class.forName(clazz,true,this);
            } catch (Throwable e) {
                throw new Error("Error in class " + clazz, e);
            }
        }
    }

    public HashMap<String, ClassNode> inflate(int flags) {
        HashMap<String,ClassNode> nodes = new HashMap<String,ClassNode>(classes.size());
        for(byte[] class_def : classes.values()) {
            ClassNode node = new ClassNode();
            ClassReader reader = new ClassReader(class_def);
            reader.accept(node,flags);
            nodes.put(node.name,node);
        }
        return nodes;
    }

    public void save(File target) throws IOException {
        FileOutputStream fos = new FileOutputStream(target);
        save(fos);
        fos.close();
    }

    public void save(OutputStream dest) throws IOException {
        JarOutputStream out = null;
        try {
            out = new JarOutputStream(dest);
            for (Map.Entry<String,byte[]> cn : classes.entrySet()) {
                JarEntry entry = new JarEntry(cn.getKey().replace( '.', '/' ) + ".class");
                out.putNextEntry(entry);
                out.write(cn.getValue());
                out.closeEntry();
            }
            out.close();
        } finally {
            if(out != null) out.close();
        }
    }

    @Override
    public Class<?> loadClass(final String name) throws ClassNotFoundException {
        if (loaded.containsKey(name)) {
            return loaded.get(name);
        } else if (!classes.containsKey(name)) {
            return super.loadClass(name);
        }
        if (defined.containsKey(name)) {
            return defined.get(name);
        } else {
            final byte[] bytes = classes.get(name);
            final Class<?> clazz = super.defineClass(name, bytes, 0, bytes.length);
            loaded.put(name, clazz);
            defined.put(name, clazz);
            return clazz;
        }
    }
}
