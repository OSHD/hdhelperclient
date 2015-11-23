package com.hdhelper.agent.bus;

import com.hdhelper.agent.SharedAgentSecrets;
import com.hdhelper.agent.bus.access.VariableBusAccess;
import com.hdhelper.agent.event.VariableEvent;
import com.hdhelper.agent.event.VariableListener;
import com.hdhelper.agent.services.RSClient;

public class VariableBus extends AbstractBus {

    VariableListener listeners;

    VariableBus(RSClient client) {
        super(client);
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    void variableChanged(int var, int old, int set) {
        if(listeners == null) return; //Not interested
        listeners.variableChanged(
                new VariableEvent(var,old,set,VariableEvent.VARIABLE_CHANGED,client.getEngineCycle())
        );
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public void addListener(VariableListener l) {
        listeners = RSEventMulticaster.add(listeners,l);
    }

    public void removeListener(VariableListener l) {
        listeners = RSEventMulticaster.remove(listeners,l);
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    static {
        SharedAgentSecrets.setVariableBusAccess(new VariableBusAccess() {
            @Override
            public VariableBus mkBus(RSClient client) {
                return new VariableBus(client);
            }

            @Override
            public void onVarChange(VariableBus bus, int var, int old, int set) {
                bus.variableChanged(var, old, set);
            }
        });
    }


}
