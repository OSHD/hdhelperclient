package com.hdhelper.client.api;

import com.hdhelper.client.Client;

public class Game {

    public static boolean isLoggedIn() {
        return Client.get().getConnectionState() == 30;
    }

    public static boolean isLoaded() {
        return Client.get().getBootState() == 140;
    }

}
