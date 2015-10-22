package com.hdhelper.peer;


public interface RSNode {
    long getKey();
    RSNode getNext();
    RSNode getPrevious();
}
