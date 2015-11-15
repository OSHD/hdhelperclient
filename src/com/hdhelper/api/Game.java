package com.hdhelper.api;

import com.hdhelper.Main;

public class Game {

    public static boolean isLoggedIn() {
        return Main.client.getConnectionState() == 30;
    }

}
