package com.hdhelper.agent.peer;

public interface RSItemPile {

    int getUid();

    int getStrictX();
    int getStrictY();
    int getHeight();
    int getCounterHeight();

    RSEntity getTop();
    RSEntity getMiddle();
    RSEntity getBottom();

}
