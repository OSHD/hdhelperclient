package com.hdhelper.agent.peer;


public interface RSNode {
    long getKey();
    RSNode getNext();
    RSNode getPrevious();
}
