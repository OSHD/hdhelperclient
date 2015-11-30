package com.hdhelper.client.api.db.hs;

import com.hdhelper.client.api.Skill;

public class Hiscore {

    public static final int STATE_UPDATED  = 3;  // The highscore is in a 'updated' state.
    public static final int STATE_OUTDATED = 2;  // We have a highscore, but its not up-to-date
    public static final int STATE_UNKNOWN  = 1;  // No highscore has not been figured yet
    public static final int STATE_ERROR    = 0;  // Error fetching acquiring any highscore for this person

    protected final String player;
    protected final Mode mode;

    protected int state;

    protected int overallRank;
    protected int overallLevel;
    protected int overallXp;

    SkillEntry[] skillEntries;
    ActivityEntry[] activityEntries;

    public enum Mode {
        NORMAL,
        IRONMAN,
        ULTIMATE_IRONMAN,
        DEADMAN
    }

    Hiscore(String player, Mode mode) {
        this.player = player;
        this.mode = mode;
        initEntries();
    }

    private void initEntries() {

        Skill[] skills = Skill.values();
        skillEntries = new SkillEntry[skills.length];
        for(int i = 0; i < skillEntries.length; i++) {
            skillEntries[i] = new SkillEntry(skills[i]);
        }

        Activity[] activities = Activity.values();
        activityEntries = new ActivityEntry[activities.length];
        for(int i = 0; i < activities.length; i++) {
            activityEntries[i] = new ActivityEntry(activities[i]);
        }

    }


    public String getPlayer() {
        return player;
    }

    public Mode getMode() {
        return mode;
    }


    public int getOverallRank() {
        return overallRank;
    }

    public int getOverallLevel() {
        return overallLevel;
    }

    public int getOverallXp() {
        return overallXp;
    }


    public SkillEntry getSkillEntry(Skill skill) {
        return skillEntries[skill.getId()];
    }

    public ActivityEntry getActivityEntry(Activity activity) {
        return activityEntries[activity.getId()];
    }

    protected void updateOverall(int rank, int level, int xp) {
        this.overallRank = rank;
        this.overallLevel = level;
        this.overallXp = xp;
    }


    // We have the best data to work with
    public boolean isUpdated() {
        return getState() == STATE_UPDATED;
    }

    // We have a hiscore, but it's stale
    public boolean isOutdated() {
        return getState() == STATE_OUTDATED;
    }

    // No hiscore has been determined
    public boolean isUnknown() {
        return getState() == STATE_UNKNOWN;
    }

    // No value can or will ever be determined
    public boolean isError() {
        return getState() == STATE_ERROR;
    }

    //We have some value to work with...
    public boolean isReady() {
        return getState() >= 2;
    }

    protected int getState() {
        return state;
    }

}
