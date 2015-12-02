package com.hdhelper.loader.net;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Jamie on 10/21/2015.
 */
public class ClientFetcher {

    public static final Logger LOG = Logger.getLogger(ClientFetcher.class.getName());

    private static String getServerAddress(int world) {
        return "http://oldschool" + world + ".runescape.com";
    }

    public static Map<String,String> downloadJar(File destination, int world) throws IOException, InterruptedException {

        if(!destination.getParentFile().exists() && !destination.getParentFile().mkdirs())
            throw new IOException("Failed to create directories:" + destination);

        String codebase = getServerAddress(world);
        Map<String,String> params = ParameterLoader.getParameters(new URL(codebase));
        String archive = params.get("archive");

        if(archive == null)
            throw new InternalError("Bad parameters");

        URL target = new URL(codebase + "/" + archive);

        URLConnection connection = target.openConnection();
        connection.connect();
        int file_size = connection.getContentLength();

        LOG.info("Client size:" + file_size);
        InputStream input = new BufferedInputStream(connection.getInputStream(), 0x2000);

        FileOutputStream out = new FileOutputStream(destination);

        int count;

        byte buffer[] = new byte[0x2000];

        int total = 0;
        int lastlog = 0;

        while ((count = input.read(buffer)) != -1) {
            total += count;
            int progress = (total * 100) / file_size;
            out.write(buffer, 0, count);
            if(progress % 5 == 0 && lastlog != progress) {
                LOG.info(progress + "%" + ' ');
                lastlog = progress;
            }
        }

        out.flush();
        out.close();
        input.close();

        return params;

    }

}
