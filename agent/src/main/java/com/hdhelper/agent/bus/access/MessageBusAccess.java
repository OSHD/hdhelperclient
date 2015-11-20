package com.hdhelper.agent.bus.access;

import com.hdhelper.agent.bus.MessageBus;
import com.hdhelper.agent.services.RSClient;
import com.hdhelper.agent.services.RSMessage;

public interface MessageBusAccess {
    MessageBus mkBus(RSClient client);
    void onMessage(MessageBus mb, RSMessage msg);
}
