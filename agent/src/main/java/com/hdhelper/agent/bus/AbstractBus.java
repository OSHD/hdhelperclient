package com.hdhelper.agent.bus;

import com.hdhelper.agent.services.RSClient;

public abstract class AbstractBus { //Package-Private

    protected final RSClient client; // Access to the client-object

    AbstractBus (RSClient client) {
        this.client = client;
    }

}
