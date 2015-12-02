package com.hdhelper.agent.event;

import java.util.EventListener;

public interface VariableListener extends EventListener {
    void variableChanged(VariableEvent e);
}
