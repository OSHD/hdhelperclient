package com.hdhelper.agent.bus;

import com.hdhelper.agent.services.RSClient;
import com.hdhelper.agent.services.RSService;

public abstract class SourceBus<S extends RSService> extends AbstractBus {

    final S src;

    SourceBus(S src, RSClient client) {
        super(client);
        this.src = src;
    }

}
