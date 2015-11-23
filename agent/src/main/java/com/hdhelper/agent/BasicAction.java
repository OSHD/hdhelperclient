package com.hdhelper.agent;


// Data object for passing Action data.
// The API implement would usally have a more detailed spec of this data.
public class BasicAction {

    int opcode;
    int arg0;
    int arg1;
    int arg2;

    String option;
    String action;
    int x;
    int y;

    public BasicAction(int arg1, int arg2, int opcode, int arg0, String option, String action, int x, int y) {
        this.opcode = opcode;
        this.arg0 = arg0;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.x = x;
        this.y = y;
        this.option = option;
        this.action = action;
    }

    public int getOpcode() {
        return opcode;
    }

    public int getArg0() {
        return arg0;
    }

    public int getArg1() {
        return arg1;
    }

    public int getArg2() {
        return arg2;
    }

}
