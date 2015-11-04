package com.hdhelper.agent.bs.impl.scripts.collection;

import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.peer.RSDeque;
import com.hdhelper.peer.RSNode;

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
