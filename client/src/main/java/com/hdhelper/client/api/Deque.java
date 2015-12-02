package com.hdhelper.client.api;

import com.hdhelper.agent.services.RSDeque;
import com.hdhelper.agent.services.RSNode;

import java.util.ArrayDeque;

public class Deque {

    public static RSNode[] toArray(RSDeque deque) {
        RSNode root = deque.getHead();

        ArrayDeque<RSNode> nodes = new ArrayDeque<RSNode>();

        RSNode cur = root;
        while ((cur=cur.getNext())!=root) {
            nodes.add(cur);
        }

        return nodes.toArray(new RSNode[nodes.size()]);
    }

}
