package com.hdhelper.client.api.db.hs;

import com.hdhelper.client.api.Skill;

public class SkillEntry {

    protected final Skill skill;
    protected int rank;
    protected int level;
    protected int xp;

    SkillEntry(Skill skill) {
        this.skill = skill;
    }

    SkillEntry(Skill skill, int rank, int level, int xp) {
        this.skill = skill;
        this.rank = rank;
        this.level = level;
        this.xp = xp;
    }

    public Skill getSkill() {
        return skill;
    }

    public int getRank() {
        return rank;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public boolean isSkillUnranked() {
        return rank == -1;
    }

    public boolean isXpUnranked() {
        return xp == -1;
    }



    protected void setRank(int rank) {
        this.rank = rank;
    }

    protected void setLevel(int level) {
        this.level = level;
    }

    protected void setXp(int xp) {
        this.xp = xp;
    }

    protected void update(int rank, int level, int xp) {
        this.rank = rank;
        this.level = level;
        this.xp = xp;
    }

}
