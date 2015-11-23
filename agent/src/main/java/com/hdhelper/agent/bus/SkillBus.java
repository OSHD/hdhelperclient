package com.hdhelper.agent.bus;

import com.hdhelper.agent.SharedAgentSecrets;
import com.hdhelper.agent.bus.access.SkillBusAccess;
import com.hdhelper.agent.event.SkillEvent;
import com.hdhelper.agent.event.SkillListener;
import com.hdhelper.agent.services.RSClient;

public class SkillBus extends AbstractBus {

    SkillListener listeners;

    SkillBus(RSClient client) {
        super(client);
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    void realLevelChanged(int skill, int prev_real_level, int set_real_level) {
        listeners.realLevelChanged(
                new SkillEvent(skill,prev_real_level,set_real_level,SkillEvent.REAL_LEVEL_CHANGED,client.getEngineCycle())
        );
    }

    void tempLevelChanged(int skill, int prev_temp_level, int set_temp_level) {
        listeners.tempLevelChanged(
                new SkillEvent(skill, prev_temp_level, set_temp_level, SkillEvent.TEMP_LEVEL_CHANGED, client.getEngineCycle())
        );
    }

    void expChanged(int skill, int prev_exp, int set_exp) {
        listeners.experienceChanged(
                new SkillEvent(skill, prev_exp, set_exp, SkillEvent.EXPERIENCE_CHANGED, client.getEngineCycle())
        );
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    public void addListener(SkillListener l) {
        listeners = RSEventMulticaster.add(listeners,l);
    }

    public void removeListener(SkillListener l) {
        if(listeners == null) return;
        listeners = RSEventMulticaster.remove(listeners,l);
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    static {
        SharedAgentSecrets.setSkillBusAccess(new SkillBusAccess() {
            @Override
            public SkillBus mkSkillBus(RSClient client) {
                return new SkillBus(client);
            }

            @Override
            public void dispatchRealLevelChangeEvent(SkillBus bus, int level, int prev, int set) {
                bus.realLevelChanged(level,prev,set);
            }

            @Override
            public void dispatchTempLevelChangeEvent(SkillBus bus, int level, int prev, int set) {
                bus.tempLevelChanged(level,prev,set);
            }

            @Override
            public void dispatchExpChangeEvent(SkillBus bus, int level, int prev, int set) {
                bus.expChanged(level,prev,set);
            }
        });
    }

}
