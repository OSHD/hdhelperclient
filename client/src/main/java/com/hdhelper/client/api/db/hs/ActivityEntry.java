package com.hdhelper.client.api.db.hs;

public class ActivityEntry {

    protected final Activity activity;
    protected int rank;
    protected int score;

    ActivityEntry(Activity activity) {
        this.activity = activity;
    }

    ActivityEntry(Activity activity, int rank, int score) {
        this.activity = activity;
        this.rank = rank;
        this.score = score;
    }

    public Activity getActivity() {
        return activity;
    }

    public int getRank() {
        return rank;
    }

    public int getScore() {
        return score;
    }

    public boolean isRankUnranked() {
        return rank == -1;
    }

    public boolean isScoreUnranked() {
        return score == -1;
    }


    protected void setRank(int rank) {
        this.rank = rank;
    }

    protected void setScore(int score) {
        this.score = score;
    }

    protected void update(int rank, int score) {
        this.rank = rank;
        this.score = score;
    }

}
