package com.hdhelper.agent.event;

import java.util.EventListener;

public interface MessageListener extends EventListener {
    /**
     * Invoked when an message is received by the client from the server.
     */
    void messageReceived(MessageEvent e);

}
