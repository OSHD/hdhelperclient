package com.hdhelper.loader.net;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.logging.Logger;

public class ClientLoader {

    private static final Logger LOG = Logger.getLogger(ClientLoader.class.getName());

    public ClientLoader(File client) {

    }


    private static String getServerAddress(int world) {
        return "http://oldschool" + world + ".runescape.com";
    }

    public static void loadClient(int world, File client) throws IOException, InterruptedException {

        int live_version = RSVersionFetcher.getVersion("",world,10000,100);
        LOG.info("live_version:" + live_version);
        if(live_version == -1)
            throw new RuntimeException("unable to resolve current version");
        if(!client.exists()) {
            LOG.info("client not found @ " + client);
            ClientFetcher.downloadJar(client,world);
        } else {
            try {
                JarFile jar = new JarFile(client);
               int local_version = -1;// getLocalVersion(jar);
                if (local_version == live_version) {
                    LOG.info("Using existing client.");
                    return;
                }
                LOG.info("Outdated client (current=" + local_version + ",live=" + live_version + ")");
                ClientFetcher.downloadJar(client, world);
            } catch (IOException e) {
                LOG.info("Corrupt client, re-downloading.");
                if (!client.delete())
                    throw new IllegalStateException("Unable to delete client@" + client);
                ClientFetcher.downloadJar(client, world);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Local Loading:


   /* private static boolean slaveLocalVersion(File meta, Version v) {
        return saveLocalVersion(meta,v.version,v.crc);
    }

    //Save the local client version into the target file.
    private static boolean saveLocalVersion(File meta, int client_version, int crc) {
        try(FileOutputStream out = new FileOutputStream(meta)) {
            DataOutputStream dos = new DataOutputStream(out);
            dos.writeInt(client_version);
            dos.writeInt(crc);
            return true;
        } catch (FileNotFoundException e) {
            LOG.log(Level.WARNING, "client-version meta not found @ " + meta.getPath(),e);
        } catch (IOException e) {
            LOG.log(Level.WARNING, "client-version meta is currupt @ " + meta.getPath(),e);
        }
        return false;
    }

    //Parses the meta file where we store the local client version.
    //The version is stored as a signed integer.
    private static Version getLocalVersion(File meta) {
        try(FileInputStream fin = new FileInputStream(meta)) {
            DataInputStream dis = new DataInputStream(fin);
            int version = dis.readInt();
            int crc = dis.readInt();
            return new Version(version,crc);
        } catch (FileNotFoundException e) {
            LOG.log(Level.WARNING, "client-version meta not found @ " + meta.getPath(),e);
        } catch (IOException e) {
            LOG.log(Level.WARNING, "client-version meta is currupt @ " + meta.getPath(),e);
        }
        return null;
    }*/




    static class Version {

        public final int version;
        public final int crc;

        public Version(int v, int c) {
            this.version = v;
            this.crc = c;
        }
    }

}
