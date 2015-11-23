package com.hdhelper.agent.bus;

import com.hdhelper.agent.event.*;

import java.util.EventListener;

public class RSEventMulticaster extends Multicaster
    implements
        MessageListener,
        ActionListener,
        VariableListener

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

    @Override
    public void variableChanged(VariableEvent e) {
        ((VariableListener)a).variableChanged(e);
        ((VariableListener)b).variableChanged(e);
    }






    public static MessageListener add(MessageListener a, MessageListener b) {
        return (MessageListener)addInternal(a,b);
    }

    public static ActionListener add(ActionListener a, ActionListener b) {
        return (ActionListener)addInternal(a, b);
    }

    public static VariableListener add(VariableListener a, VariableListener b) {
        return (VariableListener)addInternal(a,b);
    }







    public static MessageListener remove(MessageListener l, MessageListener oldl) {
        return (MessageListener) removeInternal(l, oldl);
    }

    public static ActionListener remove(ActionListener l, ActionListener oldl) {
        return (ActionListener) removeInternal(l, oldl);
    }

    public static VariableListener remove(VariableListener l, VariableListener oldl) {
        return (VariableListener) removeInternal(l,oldl);
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
