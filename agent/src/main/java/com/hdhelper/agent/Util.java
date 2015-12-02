package com.hdhelper.agent;

public class Util {

    public static boolean isClientNative(Object o) {
        return (o instanceof RTClass);
    }

}
