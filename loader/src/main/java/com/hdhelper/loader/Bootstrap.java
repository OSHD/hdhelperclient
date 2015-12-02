package com.hdhelper.loader;

import com.hdhelper.agent.ClientLoader;
import com.hdhelper.client.Wormhole;
import com.hdhelper.injector.AbstractInjector;
import com.hdhelper.injector.Injector;
import com.hdhelper.injector.InjectorConfig;
import com.hdhelper.loader.net.JAVConfig;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.jar.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

//Unify all the modules
public class Bootstrap {

    private static boolean useCaches = true;

    public static void main(String[] args) throws Exception {

     //   Policy.setPolicy(new BarrierPolicy());
      //  System.setSecurityManager(new BarrierSecurityManager());

/*
        Manifest A = getLiveManifest(getLiveOSRSConfig(1).getJarURLSpec());
        Manifest B = getLiveManifest(getLiveOSRSConfig(2).getJarURLSpec());

        System.out.println(compareSHAs(A,B));
*/

        System.out.println("Start Bootstrap");



        System.out.println("Loading game-client...");

        int world = 1;
        JarFile client = null;

        // Acquire the latest gamepack for us to inject the CNI into:

        // We'll uses caches, because why not?

        if(Environment.CLIENT.exists()) {
            // See if we can use the existing gamepack instead of downloading it again:
            try {
                JarFile existing = new JarFile(Environment.CLIENT, false); //TODO setting this to true throws verify errors for some people (on higher JREs 7/8)
                // Can/should we use this existing gamepack?
                if(verifyLocalGamepack(existing)) {
                    // The local gamepack is OK to be re-used...
                    client = existing;
                } else { // The local gamepack corrupt or outdated
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
        config.setUseCaches(useCaches);

        Map<String,byte[]> injected = inject(client,config);

        ClassLoader cni_loader = new ClientLoader(injected);

        System.gc(); // Cleanup the bootstrap...
        //TODO we need a better way of disposing of bootstrap resources as they
        //should be visible to the client, and serve no further use...


        // Start the client:

        System.out.println("Starting client...");

        Wormhole.setLoader(cni_loader);
        Wormhole.runClient();

    }

    //TODO Should the injector be within out class-path or dynamically loaded?
    //TODO We want to destroy any resources the injector used once its done.
    private static Map<String,byte[]> inject(JarFile client, InjectorConfig cfg) {
        try {
            // Re-Inject:
            AbstractInjector injector = new Injector(cfg);
            Map<String,byte[]> injected = injector.inject(client);
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

            System.out.println("Downloading applet...");

            // Download the config for us to use:
            byte[] config_bytes = getLiveOSRSConfigBytes(world);
            if(config_bytes == null) {
                return null; // Unable to get latest config, unable to download.
            }

            // Decode the config so we can find the spec for the target jar
            JAVConfig cfg = JAVConfig.decode(config_bytes); //Assuming read-only
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

            return new JarFile(dest_loc, true, ZipFile.OPEN_READ); // Read only TODO maintain RSA/Manifest?

        } catch (Throwable e) { // Any and all
            e.printStackTrace();
        }

        return null;

    }

    // Fetch the a live config file.
    static JAVConfig getLiveOSRSConfig(int world) {
        try {
            String cfg_url_str = getOSRSConfigURLSpec(world);
            URL cfg_url = new URL(cfg_url_str);
            InputStream stream = cfg_url.openStream();
            JAVConfig cfg = JAVConfig.decode(stream);
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
            Manifest local_manifest = local_gamepack.getManifest();
            if(local_manifest == null)
                throw new Exception("manifest is missing from local gamepack");

            JAVConfig cfg = getStashedConfig(local_gamepack);
            if(cfg == null)
                throw new Exception("stashed config is missing or corrupt");

            Manifest live_manifest = getLiveManifest(cfg.getJarURLSpec());
            if(live_manifest == null)
                throw new Error("Unable to acquire live-client hash"); //TODO try to use sockets/major-version as a fall-back?

            boolean shas_are_equal = compareSHAs(local_manifest, live_manifest);

            return shas_are_equal;

        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return false;
    }

    private static Manifest getLiveManifest(String spec) {
        try {
            URL jar_url = new URL( spec);
            JarInputStream jin = new JarInputStream(jar_url.openStream());
            Manifest live_man = jin.getManifest();
            jin.close();
            return live_man;
            //TODO verify streamed jarfile security
        } catch (MalformedURLException ignored) {
        } catch (IOException ignored) {
        }
        return null;
    }

    // See if the SHA1 hashes are equal
    private static boolean compareSHAs(Manifest A, Manifest B) {

        Map<String,Attributes> A_att = A.getEntries();
        Map<String,Attributes> B_att = B.getEntries();

        for(Map.Entry<String,Attributes> a : A_att.entrySet()) {

            String entry_name = a.getKey();
            Attributes local_att = a.getValue();
            if(!entry_name.endsWith(".class")) continue;

            Attributes other_att = B_att.get(entry_name);

            if(other_att == null) return false;

            String A_SHA = local_att.getValue("SHA1-Digest");
            String B_SHA = other_att.getValue("SHA1-Digest");

            if(A_SHA == null || B_SHA == null) return false; //TODO this should not happen? Throw an error?

            if(!A_SHA.equals(B_SHA)) {
                System.out.println("Mismatch@" + entry_name + "(" + A_SHA + "," + B_SHA + ")");
                return false;
            }

        }

        return true;

    }

    // Extracts the stashed config used to configure the gamepack.
    // We use the standard config provided by jagex to define meta.
    // We save the respecting config with the gamepack when we download it.
    static JAVConfig getStashedConfig(JarFile src) {
        JarEntry cfg_entry = src.getJarEntry("META-INF/config.ws"); // We store it within the default package
        if(cfg_entry == null) return null;
        try {
            InputStream stream = src.getInputStream(cfg_entry);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            JAVConfig cfg = JAVConfig.decode(reader);
            stream.close();
            return cfg;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
