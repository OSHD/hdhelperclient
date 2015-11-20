package com.hdhelper.client.api;

import com.hdhelper.agent.event.MessageListener;
import com.hdhelper.client.Main;

public class Messages {

    private Messages() {
    }

    public static void addMessageListener(MessageListener l) {
        Main.client.addMessageListener(l);
    }

    public static void removeMessageListener(MessageListener l) {
        Main.client.removeMessageListener(l);
    }

}
