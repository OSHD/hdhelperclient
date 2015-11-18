package com.hdhelper.injector.bs.scripts.collection;

import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSNode;

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
