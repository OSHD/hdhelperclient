package com.hdhelper.client.api.action;

public interface ActionFilter {
    boolean accepts(int opcode, int arg0, int arg1, int arg2);
}
