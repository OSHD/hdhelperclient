package com.hdhelper.client.api;

import com.hdhelper.client.Main;

public class Game {

    public static boolean isLoggedIn() {
        return Main.client.getConnectionState() == 30;
    }

    public static boolean isLoaded() {
        return Main.client.getBootState() == 140;
    }

}
