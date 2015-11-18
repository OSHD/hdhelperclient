package com.hdhelper.injector.bs.scripts.collection;

import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSDeque;
import com.hdhelper.agent.services.RSNode;

@ByteScript(name = "Deque")
public class Deque implements RSDeque {

    @BField Node head;
    @BField Node tail;


    @Override
    public RSNode getHead() {
        return head;
    }

    @Override
    public RSNode getTail() {
        return tail;
    }

}
