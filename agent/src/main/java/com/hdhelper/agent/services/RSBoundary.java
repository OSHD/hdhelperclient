package com.hdhelper.agent.services;

public interface RSBoundary {

    RSEntity getEntityA();
    RSEntity getEntityB();
    int getOrientationA();
    int getOrientationB();

    int getStrictX();
    int getStrictY();
    int getHeight();

    int getConfig();
    int getUid();

}
