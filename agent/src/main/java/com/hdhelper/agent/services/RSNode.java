package com.hdhelper.agent.services;


public interface RSNode {
    long getKey();
    RSNode getNext();
    RSNode getPrevious();
}
