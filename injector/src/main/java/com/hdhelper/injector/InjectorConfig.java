package com.hdhelper.injector;

public class InjectorConfig { // We should not have any references to user provided objects

    private String clientCanvasImplementName;

    public InjectorConfig() {
    }

    void verify() {
        if(clientCanvasImplementName == null || clientCanvasImplementName.isEmpty()) {
            throw new Error("invalid type:" + clientCanvasImplementName);
        }
    }

    public String getClientCanvasImplementName() {
        return clientCanvasImplementName;
    }


    public InjectorConfig setClientCanvasClass(String clazz) {
        clientCanvasImplementName = clazz;
        return this;
    }

}