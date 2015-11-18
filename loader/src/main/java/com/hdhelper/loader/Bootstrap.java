package com.hdhelper.loader;

import com.hdhelper.loader.net.JAVJavaConfig;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class Bootstrap {

    public static void main(String[] args) {

        System.out.println(getConfig(1).getInitialJar());

        JarFile client = null;
        if(Environment.CLIENT.exists()) {
            // See if we can use the existing gamepack instead of downloading it again:
            try {
                JarFile existing = new JarFile(Environment.CLIENT);
                JAVJavaConfig cfg = getConfig(existing);
                if(cfg == null)
                    throw new RuntimeException("cfg is missing or corrupt");
                if(verify(existing,cfg)) {
                    client = existing;
                }
            } catch (Exception ignored) {
            }

            //Verify Jar
        }



    }

    static JarFile download(int world) {
        try {
            JAVJavaConfig cfg = getConfig(world);
            if(cfg == null) return null;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static JAVJavaConfig getConfig(int world) {
        try {
            String cfg_url_str = getConfigURL(world);
            URL cfg_url = new URL(cfg_url_str);
            Reader reader = new InputStreamReader(cfg_url.openStream());
            BufferedReader buff_reader = new BufferedReader(reader);
            JAVJavaConfig cfg = JAVJavaConfig.parse(buff_reader);
            buff_reader.close();
            return cfg;
        } catch (Exception ignored) {
        }
        return null;
    }

    static String getConfigURL(int world) {
        return "http://oldschool" + world + ".runescape.com/jav_config.ws";
    }

    // Verify the existing gamepack
    static boolean verify(JarFile raw_client, JAVJavaConfig cfg) {
        try {
            //TODO verify existing jar-file RSA/security
            Manifest raw_man = raw_client.getManifest();
            if(raw_man == null) return false;
            int raw_hash = raw_man.hashCode();
            Integer live_hash = getLiveHash(cfg.getJarURL());
            if(live_hash == null) {
                throw new Error("Unable to acquire live-client hash"); //TODO try to use sockets/major-version as a fall-back?
            }
            return raw_hash == live_hash;
        } catch (IOException ignored) {
        }
        return false;
    }

    static Integer getLiveHash(URL gp) {
        JarInputStream jin = null;
        try {
            jin = new JarInputStream(gp.openStream());
            Manifest live_man = jin.getManifest();
            if(live_man == null) return null;
            return live_man.hashCode();
            //TODO verify streamed jarfile security
        } catch (MalformedURLException ignored) {
        } catch (IOException ignored) {
        } finally {
            if(jin != null) {
                try {
                    jin.close();
                } catch (IOException ignored) {
                }
            }
        }
        return null;
    }

    // Extracts the saved config used to configure the gamepack.
    // We save the respecting config with the gamepack jarfile when we download it.
    static JAVJavaConfig getConfig(JarFile src) {
        JarEntry cfg_entry = src.getJarEntry("config.ws");
        if(cfg_entry == null) return null;
        try {
            InputStream stream = src.getInputStream(cfg_entry);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            return JAVJavaConfig.parse(reader);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
