package com.hdhelper.agent.bus.access;

import com.hdhelper.agent.bus.SkillBus;
import com.hdhelper.agent.services.RSClient;

public interface SkillBusAccess {
    SkillBus mkSkillBus(RSClient client);
    void dispatchRealLevelChangeEvent(SkillBus bus, int skill, int prev, int set);
    void dispatchTempLevelChangeEvent(SkillBus bus, int skill, int prev, int set);
    void dispatchExpChangeEvent(SkillBus bus, int skill, int prev, int set);
}
