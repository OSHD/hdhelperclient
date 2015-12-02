package com.hdhelper.client.api.action;

import com.hdhelper.agent.event.ActionEvent;
import com.hdhelper.agent.event.ActionListener;
import com.hdhelper.client.api.action.tree.Action;

//Converts been lower-level and high-level action objects
public abstract class ActionAdapter implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        actionPerformed(Action.valueOf(e.getAction()));
    }

    public abstract void actionPerformed(Action act);

}
