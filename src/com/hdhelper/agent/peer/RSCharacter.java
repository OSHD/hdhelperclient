package com.hdhelper.agent.peer;

public interface RSCharacter extends RSEntity {

    int getStrictX();
    int getStrictY();
    int getTargetIndex();
    String getOverheadText();
    int getAnimation();
    int getOrientation();
    int getHitpoints();
    int getMaxHitpoints();
    int getHealthBarCycle();

}
