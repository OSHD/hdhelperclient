package com.hdhelper.agent.event;

import com.hdhelper.agent.BasicAction;

public class ActionEvent extends RSEvent { //TODO capture key modifiers when the action for performed

    /**
     * The first number in the range of ids used for action events.
     */
    public static final int ACTION_FIRST = 2001;

    /**
     * The last number in the range of ids used for action events.
     */
    public static final int ACTION_LAST  = 2001;

    /**
     * This event id indicates that a action has been performed
     */
    public static final int ACTION_PERFORMED = ACTION_FIRST;


    BasicAction action;

    public ActionEvent(BasicAction action, int id, int cycle) {
        super(id, cycle);
        this.action = action;
    }

    public BasicAction getAction() {
        return action;
    }

}
