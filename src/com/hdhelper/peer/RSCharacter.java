package com.hdhelper.peer;

public interface RSCharacter extends RSRenderable {
    int getStrictX();
    int getStrictY();
    int getTargetIndex();
    String getOverheadText();
    int getAnimation();

    default int getRegionX() {
        return getStrictX() >> 7;
    }

    default int getRegionY() {
        return getStrictY() >> 7;
    }

}
