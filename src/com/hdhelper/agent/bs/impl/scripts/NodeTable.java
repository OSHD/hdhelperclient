package com.hdhelper.agent.bs.impl.scripts;

import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.peer.RSNode;
import com.hdhelper.peer.RSNodeTable;

@ByteScript(name = "NodeTable")
public class NodeTable implements RSNodeTable {

    @BField Node[] buckets;
    @BField(name = "index") int size;



    @Override
    public RSNode[] getBuckets() {
        return buckets;
    }

    @Override
    public int getCapacity() {
        return size;
    }
}
