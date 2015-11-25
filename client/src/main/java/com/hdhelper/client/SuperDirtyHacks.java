package com.hdhelper.client;

import com.hdhelper.agent.services.RSClient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Created by Jamie on 11/24/2015.
 */

//Remove this
public class SuperDirtyHacks {

    public static void dump(int index) {
        RSClient client = Main.client;
        int[] key = client.getKeys()[index];
        int chunkId = client.getChunkIds()[index];
        int rx = chunkId >> 8;
        int ry = chunkId & 255;
        System.out.println(index + ": <" + rx + "_" + ry + "> => " + Arrays.toString(key));
        dumpXTEA(chunkId, key);
    }

    private static void dumpXTEA(int chunkId, int[] key) {
        File root = new File(Environment.XTEAS, String.valueOf(Environment.CLIENT_REVISION));
        File target = new File(root, chunkId + ".txt");
        if (!root.exists() && !root.mkdirs())
            throw new RuntimeException("Unable to create file:" + root);
        try {
            FileWriter fw = new FileWriter(target);
            PrintWriter pw = new PrintWriter(fw);
            for(int i = 0; i < key.length; i++) {
                pw.print(key[i]);
                if(i!=key.length-1) {
                    pw.println();
                }
            }
            fw.close();
        } catch (IOException e) {
            throw new Error(e);
        }
    }


}
