package com.hdhelper.agent.bus.access;

import com.hdhelper.agent.bus.MessageBus;
import com.hdhelper.agent.services.RSClient;
import com.hdhelper.agent.services.RSMessage;

public interface MessageBusAccess {
    MessageBus mkMessageBus(RSClient client);
    void dispatchMessageEvent(MessageBus mb, RSMessage msg);
}
