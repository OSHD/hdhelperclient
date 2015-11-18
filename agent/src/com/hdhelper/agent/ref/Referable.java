package com.hdhelper.agent.ref;

public interface Referable {

    int ACTIVE   = 1;
    int PENDING  = 2;
    int INACTIVE = 3;


    int getState();

    boolean exists();

    //TODO make this private and establish a access interface,
    //its unsafe.
    void add(RefNode node);



    void destroy(); //tODO remove this

}
