package com.hdhelper.agent;

import com.hdhelper.Environment;
import com.hdhelper.agent.io.ClientLoader;
import com.hdhelper.agent.mod.InjectionModule;
import com.hdhelper.agent.util.ClassWriterFix;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;


public final class Injector {

    private static final Logger LOG = Logger.getLogger(Injector.class.getName());

    public HashMap<String, byte[]> classes_final = new HashMap<>();
    public HashMap<String, ClassNode> classes = new HashMap<>();

    private void loadJar() throws InterruptedException, IOException {
        LOG.info("Loading Jar...");
        ClientLoader.loadClient(Environment.WORLD);
    }

    public Map<String,byte[]> inject() throws Exception {

        loadJar();

        Map<String,byte[]> defs = inflate();
        ClassLoader default_def_loader = new ByteClassLoader(defs);

        for(InjectionModule mod : InjectionModule.getModules()) {
            mod.inject(classes);
        }

        compile(default_def_loader);

        return classes_final;

    }


    Map<String,byte[]> inflate() throws IOException {
        JarFile jar = new JarFile(Environment.CLIENT);
        Enumeration<JarEntry> jarEntries = jar.entries();
        Map<String,byte[]> def_map = new HashMap<>(jar.size());
        while (jarEntries.hasMoreElements()) {
            JarEntry entry = jarEntries.nextElement();
            String entryName = entry.getName();
            if (!entryName.endsWith(".class")) continue;
            ClassNode node = new ClassNode();
            InputStream in = jar.getInputStream(entry);
            ClassReader reader = new ClassReader(in);
            reader.accept(node, ClassReader.SKIP_DEBUG);
            classes.put(node.name, node);
            def_map.put(node.name,reader.b);
            in.close();
        }
        jar.close();
        return def_map;
    }


    private void compile(ClassLoader default_loader) {
        for(Map.Entry<String,ClassNode> clazz : classes.entrySet()) {
            try {
                String name = clazz.getKey();
                ClassNode node = clazz.getValue();
                ClassWriter writer = new ClassWriterFix( ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES, default_loader );
                node.accept(writer);
                byte[] def = writer.toByteArray();
                classes_final.put(name.replace("/", "."),def);
            } catch (Throwable e) {
                throw new Error("Failed to transform " + clazz.getKey(),e);
            }
        }
    }

}
