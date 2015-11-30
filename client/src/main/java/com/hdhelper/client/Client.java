package com.hdhelper.client;

import com.hdhelper.agent.CNI;
import com.hdhelper.agent.services.RSClient;

public final class Client {

    private static final CNI cni;

    static {
        //Force the CNI to be initialized before calling any other code.
        //This enforces the guarantee of RuneTime accessibility of all game services.
        cni = ClientNative.get();
    }

    private Client() {
    }

    public static RSClient get() {
        return cni.get();
    }

    public static String getProperty(String name) {
        return null;
    }


}
