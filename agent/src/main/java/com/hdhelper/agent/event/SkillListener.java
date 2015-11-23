package com.hdhelper.agent.event;

import java.util.EventListener;

public interface SkillListener extends EventListener {

    /**
     * Indicates that the real level of a skill has changed.
     */
    void realLevelChanged(SkillEvent e);

    /**
     * Indicates that the temporary level of a skill has changed.
     */
    void tempLevelChanged(SkillEvent e);

    /**
     * Indicates that the experience of skill has changed.
     */
    void experienceChanged(SkillEvent e);

}
