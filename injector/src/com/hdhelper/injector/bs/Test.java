package com.hdhelper.injector.bs;

import com.bytescript.common.ReflectionProfiler;
import com.bytescript.compiler.BCompiler;
import com.hdhelper.agent.bs.scripts.Client;
import com.hdhelper.agent.patch.GPatch;
import com.hdhelper.agent.util.ClassWriterFix;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

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
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(file));
            ClassReader reader = new ClassReader(in);
            ClassNode dest = new ClassNode();
            reader.accept(dest,ClassReader.EXPAND_FRAMES);
            return dest;
        } catch (Exception e) {
            e.printStackTrace();;
        } finally {
            if(in != null) try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static ClassNode save(ClassNode cn, String file) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(file));
            ClassWriter writer = new ClassWriterFix(ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES, new URLClassLoader(new URL[] { new File("C:\\Users\\Jamie\\HDUpdater\\Updater\\jars\\96\\gamepack.jar").toURL() }));
            cn.accept(writer);
            byte[] compiled = writer.toByteArray();
            out.write(compiled);
        } catch (Exception e) {
            e.printStackTrace();;
        } finally {
            if(out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
