package com.hdhelper.agent.bus;

import com.hdhelper.agent.BasicAction;
import com.hdhelper.agent.SharedAgentSecrets;
import com.hdhelper.agent.bus.access.ActionBusAccess;
import com.hdhelper.agent.event.ActionEvent;
import com.hdhelper.agent.event.ActionListener;
import com.hdhelper.agent.services.RSClient;

public class ActionBus extends AbstractBus {

    ActionListener actionListeners;

    ActionBus(RSClient client) {
        super(client);
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    void onAction(BasicAction e) {
        if(actionListeners == null) return; //No listeners
        actionListeners.actionPerformed(
                new ActionEvent(e, ActionEvent.ACTION_PERFORMED, client.getEngineCycle())
        );
    }

    ///////////////////////////////////////////////////////////////////////////////////////


    public void addActionListener(ActionListener l)  {
        actionListeners = RSEventMulticaster.add(actionListeners,l);
    }

    public void removeMessageListener(ActionListener l) {
       actionListeners = RSEventMulticaster.remove(actionListeners, l);
    }


    ///////////////////////////////////////////////////////////////////////////////////////


    static {
        SharedAgentSecrets.setActionBusAccess(new ActionBusAccess() {

            @Override
            public ActionBus mkBus(RSClient client) {
                return new ActionBus(client);
            }

            @Override
            public void onAction(ActionBus bus, BasicAction act) {
                bus.onAction(act);
            }
        });
    }

}
