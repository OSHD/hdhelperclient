package com.hdhelper.client.api;

import com.hdhelper.client.Client;

public class Config {

    public static int get(int id) {
        return Client.get().getConfig()[id];
    }
}
