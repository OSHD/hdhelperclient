package com.hdhelper.agent.bus;

import com.hdhelper.agent.services.RSClient;
import com.hdhelper.agent.services.RSService;

//Buses that have a logical source (EX. NpcBus would have a reference of the npc as the source)
public abstract class SourceBus<S extends RSService> extends AbstractBus {

    protected final S src;

    SourceBus(S src, RSClient client) {
        super(client);
        this.src = src;
    }

    public final S getSource() {
        return src;
    }

}
