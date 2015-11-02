package com.hdhelper.agent.bs.impl.scripts.collection;

import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.peer.RSNode;

@ByteScript(name = "Node")
public class Node implements RSNode {

    @BField Node next;
    @BField(name = "previous") Node prev;
    @BField long key;

    @Override
    public long getKey() {
        return key;
    }

    @Override
    public RSNode getNext() {
        return next;
    }

    @Override
    public RSNode getPrevious() {
        return prev;
    }

}
