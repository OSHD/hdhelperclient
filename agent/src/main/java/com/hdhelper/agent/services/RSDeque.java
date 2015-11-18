package com.hdhelper.agent.services;

public interface RSDeque {
    RSNode getHead();
    RSNode getTail();

   /* default RSNode[] toArray() {

        RSNode root = getHead();

        ArrayDeque<RSNode> nodes = new ArrayDeque<>();

        RSNode cur = root;
        while ((cur=cur.getNext())!=root) {
            nodes.add(cur);
        }

        return nodes.toArray(new RSNode[nodes.size()]);

    }*/
}
