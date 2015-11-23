package com.hdhelper.agent.event;

public class VariableEvent extends RSEvent {

    /**
     * The first number in the range of ids used for variable change events.
     */
    public static final int VARIABLE_FIRST = 3001;

    /**
     * The last number in the range of ids used for variable events.
     */
    public static final int Variable_LAST = 3001;

    /**
     * This event id indicates that a engine variable/setting has been changed.
     */
    public static final int VARIABLE_CHANGED = VARIABLE_FIRST;

    int var; // The variable that changed
    int prev; // The previous value of the variable
    int set; // The value that the variable was set to

    public VariableEvent(int var, int prev, int set, int id, int cycle) {
        super(id, cycle);
        this.var = var;
        this.prev = prev;
        this.set = set;
    }

    public int getVar() {
        return var;
    }

    public int getPreviousValue() {
        return prev;
    }

    public int getSetValue() {
        return set;
    }

    @Override
    public String toString() {
        return "VarChanged " + var + " (" + prev + " ==> " + set + ")";
    }

}
