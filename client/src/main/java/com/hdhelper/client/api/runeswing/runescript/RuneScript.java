package com.hdhelper.client.api.runeswing.runescript;

import com.hdhelper.client.Client;

public class RuneScript {

    public static void run(int id, Object... args) {
        Object[] args0 = new Object[1 + args.length];
        args0[0] = id;
        System.arraycopy(args, 0, args0, 1, args.length);
        Client.get().runScript(args0);
    }

}
