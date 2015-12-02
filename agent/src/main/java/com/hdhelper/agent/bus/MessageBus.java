package com.hdhelper.agent.bus;

import com.hdhelper.agent.SharedAgentSecrets;
import com.hdhelper.agent.bus.access.MessageBusAccess;
import com.hdhelper.agent.event.MessageEvent;
import com.hdhelper.agent.event.MessageListener;
import com.hdhelper.agent.services.RSClient;
import com.hdhelper.agent.services.RSMessage;

public class MessageBus extends AbstractBus {

    private MessageListener messageListeners;

    MessageBus(RSClient client) {
        super(client);
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    void onMessage(RSMessage msg) {
        if(messageListeners == null) return; //No listeners
        messageListeners.messageReceived(
                new MessageEvent(msg, MessageEvent.MESSAGE_RECEIVED, client.getEngineCycle())
        );
    }

    ///////////////////////////////////////////////////////////////////////////////////////


    public void addMessageListener(MessageListener l)  {
        messageListeners = RSEventMulticaster.add(messageListeners,l);
    }

    public void removeMessageListener(MessageListener l) {
        messageListeners = RSEventMulticaster.remove(messageListeners, l);
    }


    ///////////////////////////////////////////////////////////////////////////////////////


    static {

        SharedAgentSecrets.setMessageBusAccess(new MessageBusAccess() {
            @Override
            public MessageBus mkMessageBus(RSClient client) {
                return new MessageBus(client);
            }

            @Override
            public void dispatchMessageEvent(MessageBus mb, RSMessage msg) {
                mb.onMessage(msg);
            }
        });

    }

}
