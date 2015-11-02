package com.hdhelper.agent.bs.impl.scripts.collection;

import com.hdhelper.agent.bs.impl.scripts.collection.Node;
import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.peer.RSNode;
import com.hdhelper.peer.RSNodeTable;

@ByteScript(name = "NodeTable")
public class NodeTable implements RSNodeTable {

    @BField Node[] buckets;
    @BField int size;
    @BField int index;



    @Override
    public RSNode[] getBuckets() {
        return buckets;
    }

    @Override
    public int getCapacity() {
        return index; //TODO its swapped in the updater
    }
}
