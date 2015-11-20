package com.hdhelper.agent.bus;

import com.hdhelper.agent.SharedAgentSecrets;
import com.hdhelper.agent.bus.access.MessageBusAccess;
import com.hdhelper.agent.event.MessageEvent;
import com.hdhelper.agent.event.MessageListener;
import com.hdhelper.agent.services.RSClient;
import com.hdhelper.agent.services.RSMessage;

import java.util.EventListener;

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
        messageListeners = MessageMulticaster.add(messageListeners,l);
    }

    public void removeMessageListener(MessageListener l) {
        messageListeners = MessageMulticaster.remove(messageListeners, l);
    }


    ///////////////////////////////////////////////////////////////////////////////////////


    static {

        SharedAgentSecrets.setMessageBusAccess(new MessageBusAccess() {
            @Override
            public MessageBus mkBus(RSClient client) {
                return new MessageBus(client);
            }

            @Override
            public void onMessage(MessageBus mb, RSMessage msg) {
                mb.onMessage(msg);
            }
        });

    }

}

class MessageMulticaster extends Multicaster implements MessageListener {

    protected MessageMulticaster(EventListener a, EventListener b) {
        super(a, b);
    }

    @Override
    public void messageReceived(MessageEvent m) {
        ((MessageListener)a).messageReceived(m);
        ((MessageListener)b).messageReceived(m);
    }

    public static MessageListener add(MessageListener a,MessageListener b) {
        return (MessageListener)addInternal(a, b);
    }

    public static MessageListener remove(MessageListener l,MessageListener oldl) {
        return (MessageListener)removeInternal(l, oldl);
    }

    protected static MessageListener addInternal(MessageListener a, MessageListener b) {
        if (a == null)  return b;
        if (b == null)  return a;
        return new MessageMulticaster(a, b);
    }

}
