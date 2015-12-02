package com.hdhelper.agent.bus.access;

import com.hdhelper.agent.BasicAction;
import com.hdhelper.agent.bus.ActionBus;
import com.hdhelper.agent.services.RSClient;

public interface ActionBusAccess {
    ActionBus mkActionBus(RSClient client);
    void dispatchActionEvent(ActionBus bus, BasicAction act);
}
