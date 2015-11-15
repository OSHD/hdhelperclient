package com.hdhelper.agent.ref;

public class Ref<T extends Referable> extends CollectionNode {

    private T payload;

    public Ref(T payload) {
        this.payload = payload;
    }

    public T get() {
        return payload;
    }

}
