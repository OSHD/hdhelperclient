package com.hdhelper.client.api;

import com.hdhelper.agent.event.SkillEvent;
import com.hdhelper.agent.event.SkillListener;

public abstract class SkillEventAdapter implements SkillListener {

    @Override
    public void realLevelChanged(SkillEvent e) {

    }

    @Override
    public void tempLevelChanged(SkillEvent e) {

    }

    @Override
    public void experienceChanged(SkillEvent e) {

    }


    public void realLevelGained() {

    }
}
