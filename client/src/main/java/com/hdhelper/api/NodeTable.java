package com.hdhelper.api;

import com.hdhelper.agent.services.RSNode;
import com.hdhelper.agent.services.RSNodeTable;

/**
 * Created by Jamie on 11/13/2015.
 */
public class NodeTable {

    public static<T extends RSNode> T get(long key, RSNodeTable table) {
        int capacity = table.getCapacity();
        RSNode[] buckets = table.getBuckets();
        RSNode last = buckets[(int) (key & (long) (capacity - 1))];
        RSNode current;
        for (current = last.getNext(); current != last; current = current.getNext()) {
            if (current.getKey() == key) {
                return (T) current;
            }
        }
        return null;
    }
}
