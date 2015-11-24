package com.hdhelper.agent.event;

public class SkillEvent extends RSEvent {

    /**
     * The first number in the range of ids used for skill events.
     */
    public static final int SKILL_FIRST = 4001;

    /**
     * The last number in the range of ids used for skill events.
     */
    public static final int SKILL_LAST  = 4003;

    /**
     * This event id indicates that the level of a real-skill has changed.
     */
    public static final int REAL_LEVEL_CHANGED = SKILL_FIRST;

    /**
     * This event id indicates that the level of a temp-skill has changed.
     */
    public static final int TEMP_LEVEL_CHANGED = SKILL_FIRST + 1;

    /**
     * This event id indicates that the experience of a skill has changed.
     */
    public static final int EXPERIENCE_CHANGED = SKILL_FIRST + 2;


    int skill;
    int prev;
    int set;

    public SkillEvent(int skill, int prev, int set, int id, int cycle) {
        super(id, cycle);
        this.skill = skill;
        this.prev  = prev;
        this.set   = set;
    }

    public int getSkillId() {
        return skill;
    }

    public int getPreviousValue() {
        return prev;
    }

    public int getSetValue() {
        return set;
    }

    public int getChange() {
        return (prev - set);
    }

    public boolean isGain() {
        return getChange() > 0;
    }

    public boolean isLoss() {
        return getChange() < 0;
    }

}
