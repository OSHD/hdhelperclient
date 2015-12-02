package com.hdhelper.injector;

public class Callback {

    static boolean chk = false;

    static  int e= 0;
    public static Object ok(Object o) {
        return (e++)%2 == 0 ? o : null;
    }

    public static void msg(int a, String b, String c, String d) {
        System.out.println("MSG:" + a + "," + b + "," + c + "," + d);
    }
    public static void event(Object[] args) {
        int id = (Integer) args[0];
        Object[] args0 = new Object[args.length - 1];
        System.arraycopy(args, 1, args0, 0, args0.length);
       /* System.out.println(id + ":" + Arrays.toString(args0));*/

        /*if(!chk) {
            System.out.println("Checking...");
            for(int i = 0; i < 1500; i++) {
                RSRuneScript script = Main.client.getRuneScript(i);
                if(script == null) continue;
                for(String op : script.getStrOperands()) {
                    if(op == null) continue;
                    if(op.toLowerCase().contains("chat filtering")) {
                        System.out.println(op + "@" + i);

                        Dumper.dump(script);
                    }
                }

                System.out.println("------------------------");

                Dumper.dump(Main.client.getRuneScript(193));

            }
            chk = true;
            System.out.println("Done...");

        }*/


    }

    /*public static void dump(int index) {
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
    }*/

   /* public static void reshape(int[] raster, Image img) {
        ClientCanvas.reshape(raster,img);
    }
*/
}
