package com.hdhelper.loader;

import com.hdhelper.injector.AbstractInjector;
import com.hdhelper.injector.Injector;
import com.hdhelper.injector.InjectorConfig;
import com.hdhelper.loader.net.JAVJavaConfig;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.jar.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Bootstrap {

    public static void main(String[] args) throws IOException {

        System.out.println("Start Bootstrap");


        System.out.println("Loading game-client...");

        int world = 1;
        JarFile client = null;

        // Acquire the latest gamepack for us to inject the CNI into:

        // We'll uses caches, because why not?

        if(Environment.CLIENT.exists()) {
            // See if we can use the existing gamepack instead of downloading it again:
            try {
                JarFile existing = new JarFile(Environment.CLIENT, true);
                // Can/should we use this existing gamepack?
                if(verifyLocalGamepack(existing)) {
                    // The local gamepack is OK to be re-used...
                    client = existing;
                } else { // The local gamepack or Outdated
                    client = downloadAndStashLive(world, Environment.CLIENT);
                }
            } catch (Exception ignored) {
                // The local gamepack is corrupt
                client = downloadAndStashLive(world, Environment.CLIENT);
            }

        } else { // No cache hit, re-download
            client = downloadAndStashLive(world, Environment.CLIENT);
        }

        if(client == null) {
            System.err.println("Failed to acquire client");
            return;
        }

        // Inject the CNI:

        System.out.println("Injecting...");

        InjectorConfig config = new InjectorConfig();
        config.setOutputLoc(Environment.INJECTED);

        JarFile injected = inject(client,config);

        // Start the client:

        System.out.println("Starting client...");








    }

    //TODO should the injector be within out class-path or dynamically loaded?
    static JarFile inject(JarFile client, InjectorConfig cfg) {

        try {
            AbstractInjector injector = new Injector(cfg);
            JarFile injected = injector.inject(client);
            injector.destroy();
            return injected;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    static byte[] getLiveOSRSConfigBytes(int world) {
        try {
            String cfg_url_str = getOSRSConfigURLSpec(world);
            URL cfg_url = new URL(cfg_url_str);
            InputStream cfg_stream = cfg_url.openStream();
            // Download the config:
            ByteArrayOutputStream cfg_data_clone = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = cfg_stream.read(buf)) > 0) {
                cfg_data_clone.write(buf, 0, len);
            }
            cfg_stream.close();
            return cfg_data_clone.toByteArray();
        } catch (IOException ignored) {
        }
        return null;
    }

    static JarFile downloadAndStashLive(int world, File dest_loc) {

        try {

            // Download the config for us to use:
            byte[] config_bytes = getLiveOSRSConfigBytes(world);
            if(config_bytes == null) {
                return null; // Unable to get latest config, unable to download.
            }

            // Decode the config so we can find the spec for the target jar
            JAVJavaConfig cfg = JAVJavaConfig.decode(config_bytes); //Assuming read-only
            if(cfg == null) {
                return null; // Decode error
            }

            // We finally have the config, now we can download the jar:
            URLConnection con = cfg.getJarURL().openConnection();
            JarInputStream jis = new JarInputStream(con.getInputStream());

            // Download:

            long size = con.getContentLength(); //TODO callback download progress

            // TODO verify security
            Manifest manifest = jis.getManifest();
            if(manifest == null) // We ensure the manfiest exists
                throw new IOException("missing manifest");

            // Stream the jar to the designated file:
            FileOutputStream fos = new FileOutputStream(dest_loc);
            JarOutputStream out = new JarOutputStream(fos,manifest);

            // Stream initial entry's:
            ZipEntry e;
            byte[] buffer = new byte[1024];
            while ((e = jis.getNextEntry())!=null) {
                out.putNextEntry(e);
                if (!e.isDirectory()) {
                    int bytesRead;
                    while ((bytesRead = jis.read(buffer))!= -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }
                out.closeEntry();
            }

            // Stash the config as a entry:
            JarEntry je = new JarEntry("META-INF/config.ws");
            out.putNextEntry(je);
            out.write(config_bytes);
            out.closeEntry();;

            // Clean up:
            out.close();
            jis.close();

            //TODO should/can we keep the jarFile in memory instead of have re-stream is back from the disk?

            return new JarFile(dest_loc, true, ZipFile.OPEN_READ); // Read only

        } catch (Throwable e) { // Any and all
            e.printStackTrace();
        }

        return null;

    }

    // Fetch the a live config file.
    static JAVJavaConfig getLiveOSRSConfig(int world) {
        try {
            String cfg_url_str = getOSRSConfigURLSpec(world);
            URL cfg_url = new URL(cfg_url_str);
            InputStream stream = cfg_url.openStream();
            JAVJavaConfig cfg = JAVJavaConfig.decode(stream);
            stream.close();
            return cfg;
        } catch (Exception ignored) {
        }
        return null;
    }

    static String getOSRSConfigURLSpec(int world) {
        return "http://oldschool" + world + ".runescape.com/jav_config.ws";
    }

    // Verify the existing gamepack:
    // 1. Verify its not outdated / Version Control
    // 2. Security //TODO
    static boolean verifyLocalGamepack(JarFile local_gamepack) {
        try {

            //TODO verify local RSA/security throughout
            Manifest raw_man = local_gamepack.getManifest();
            if(raw_man == null)
                throw new Exception("manifest is missing from local gamepack");

            JAVJavaConfig cfg = getStashedConfig(local_gamepack);
            if(cfg == null)
                throw new Exception("stashed config is missing or corrupt");

            Integer live_hash = getLiveHash(cfg.getJarURLSpec());
            if(live_hash == null)
                throw new Error("Unable to acquire live-client hash"); //TODO try to use sockets/major-version as a fall-back?

            int local_hash = raw_man.hashCode();

            boolean outdated = local_hash != live_hash;

            return !outdated;

        } catch (Exception ignored) {
        }
        return false;
    }

    static Integer getLiveHash(String spec) {
        try {
            URL jar_url = new URL( spec);
            JarInputStream jin = new JarInputStream(jar_url.openStream());
            Manifest live_man = jin.getManifest();
            if(live_man == null) return null;
            return live_man.hashCode();
            //TODO verify streamed jarfile security
        } catch (MalformedURLException ignored) {
        } catch (IOException ignored) {
        }
        return null;
    }

    // Extracts the stashed config used to configure the gamepack.
    // We use the standard config provided by jagex to define meta.
    // We save the respecting config with the gamepack when we download it.
    static JAVJavaConfig getStashedConfig(JarFile src) {
        JarEntry cfg_entry = src.getJarEntry("META-INF/config.ws"); // We store it within the default package
        if(cfg_entry == null) return null;
        try {
            InputStream stream = src.getInputStream(cfg_entry);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            JAVJavaConfig cfg = JAVJavaConfig.decode(reader);
            stream.close();
            return cfg;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
