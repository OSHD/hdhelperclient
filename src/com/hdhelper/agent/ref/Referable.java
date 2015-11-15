package com.hdhelper.agent.ref;

public interface Referable {

    int ACTIVE   = 1; // OK
    int ENQUEUED = 2; // Being destroyed
    int INACTIVE = 3; // Dead


    int getState();

    //TODO make this private and establish a access interface,
    //its unsafe.
    void add(RefNode node);



    void destroy(); //tODO remove this

}
