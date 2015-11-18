package com.hdhelper.agent;

import com.hdhelper.agent.bridge.RenderSwitch;

/**
 * Defined the accessible objects in which defines runtime
 * dependencies of the Client Native Interface.
 */
public interface CNIStub {

    RenderSwitch getRenderSwitch();

}
