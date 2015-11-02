package com.hdhelper.agent.bs;

import com.hdhelper.agent.bs.compiler.BCompiler;
import com.hdhelper.agent.bs.impl.ReflectionProfiler;
import com.hdhelper.agent.bs.impl.ResolverImpl;
import com.hdhelper.agent.bs.impl.patch.GPatch;
import com.hdhelper.agent.bs.impl.scripts.Client;
import com.hdhelper.agent.util.ClassWriterFix;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;

public class Test {

    public static void main(String... args) throws Exception {
        String gson = read(new File("C:\\Users\\Jamie\\HDUpdater\\Updater\\hooks.gson").toURL().toString());
        GPatch cr = GPatch.parse(gson);
        BCompiler compiler = new BCompiler(new ReflectionProfiler(),new ResolverImpl(cr));

        ClassNode cn = load("C:\\Users\\Jamie\\HDUpdater\\Updater\\jars\\96\\client.class");
        compiler.inject(Client.class, cn);
        save(cn,"C:\\Users\\Jamie\\Desktop\\ff\\client.class");

    }

    public static String read(String url) throws Exception {

        URL oracle = new URL(url);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        StringBuilder b = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            b.append(inputLine);
        in.close();

        return b.toString();

    }


    private static ClassNode load(String file) {
        try(FileInputStream in = new FileInputStream(new File(file))) {
            ClassReader reader = new ClassReader(in);
            ClassNode dest = new ClassNode();
            reader.accept(dest,ClassReader.EXPAND_FRAMES);
            return dest;
        } catch (Exception e) {
            e.printStackTrace();;
        }
        return null;
    }

    private static ClassNode save(ClassNode cn, String file) {

        try(FileOutputStream out = new FileOutputStream(new File(file))) {
            ClassWriter writer = new ClassWriterFix(ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES, new URLClassLoader(new URL[] { new File("C:\\Users\\Jamie\\HDUpdater\\Updater\\jars\\96\\gamepack.jar").toURL() }));
            cn.accept(writer);
            byte[] compiled = writer.toByteArray();
            out.write(compiled);
        } catch (Exception e) {
            e.printStackTrace();;
        }
        return null;
    }
}
