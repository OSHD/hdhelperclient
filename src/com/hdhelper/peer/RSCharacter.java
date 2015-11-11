package com.hdhelper.peer;

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

    default int getRegionX() {
        return getStrictX() >> 7;
    }

    default int getRegionY() {
        return getStrictY() >> 7;
    }

    default boolean isHpBarShowing(int engine_cycle){
        return getHealthBarCycle() > engine_cycle;
    }

}
