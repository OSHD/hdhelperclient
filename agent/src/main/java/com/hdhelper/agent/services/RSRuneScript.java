package com.hdhelper.agent.services;

public interface RSRuneScript {

    int getIntArgCount();
    int getStrArgCount();

    int getIntLocalCount();
    int getStrLocalCount();

    int[] getIntOperands();
    String[] getStrOperands();

    int[] getOpcodes();


}
