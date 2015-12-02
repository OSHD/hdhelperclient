package com.hdhelper.agent.services;

public interface RSBoundaryDecoration {

    RSEntity getEntityA();
    int getOrientationA();

    RSEntity getEntityB();
    int getOrientationB();

    int getStrictX();
    int getStrictY();
    int getHeight();

    int getInsetX();
    int getInsetY();

    int getConfig();
    int getUid();

}
