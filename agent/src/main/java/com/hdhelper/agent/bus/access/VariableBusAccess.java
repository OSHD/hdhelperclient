package com.hdhelper.agent.bus.access;

import com.hdhelper.agent.bus.VariableBus;
import com.hdhelper.agent.services.RSClient;

public interface VariableBusAccess {
    VariableBus mkVariableBus(RSClient client);
    void dispatchVarEvent(VariableBus bus, int var, int old, int set);
}
