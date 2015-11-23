package com.hdhelper.agent.bus;

import com.hdhelper.agent.event.ActionEvent;
import com.hdhelper.agent.event.ActionListener;
import com.hdhelper.agent.event.MessageEvent;
import com.hdhelper.agent.event.MessageListener;

import java.util.EventListener;

public class RSEventMulticaster extends Multicaster
    implements
        MessageListener,
        ActionListener

{



    protected RSEventMulticaster(EventListener a, EventListener b) {
        super(a, b);
    }






    @Override
    public void actionPerformed(ActionEvent e) {
        ((ActionListener)a).actionPerformed(e);
        ((ActionListener)b).actionPerformed(e);
    }

    @Override
    public void messageReceived(MessageEvent e) {
        ((MessageListener)a).messageReceived(e);
        ((MessageListener)b).messageReceived(e);
    }







    public static MessageListener add(MessageListener a, MessageListener b) {
        return (MessageListener)addInternal(a,b);
    }

    public static ActionListener add(ActionListener a, ActionListener b) {
        return (ActionListener)addInternal(a, b);
    }








    public static MessageListener remove(MessageListener l, MessageListener oldl) {
        return (MessageListener) removeInternal(l,oldl);
    }

    public static ActionListener remove(ActionListener l, ActionListener oldl) {
        return (ActionListener) removeInternal(l, oldl);
    }












    protected static EventListener addInternal(EventListener a, EventListener b) {
        if (a == null)  return b;
        if (b == null)  return a;
        return new RSEventMulticaster(a, b);
    }


    protected static EventListener removeInternal(EventListener l, EventListener oldl) {
        if (l == oldl || l == null) {
            return null;
        } else if (l instanceof RSEventMulticaster) {
            return ((RSEventMulticaster)l).remove(oldl);
        } else {
            return l;		// it's not here
        }
    }



}
