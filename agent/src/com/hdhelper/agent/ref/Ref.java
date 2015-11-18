package com.hdhelper.agent.ref;

public final class Ref<T extends Referable> extends CollectionNode {

    T referent;

    private Ref() {
    }

    Ref(T referent) {
        if(referent == null)
            throw new IllegalArgumentException("ref == null");
        this.referent = referent;
    }

    // If the referent is null then the object it
    // was referring to is inactive
    public T get() {
        return referent;
    }

    //Remove us from the collection were within, and remove us
    //from the clients finalizer list.
    public void unQueue() {
        super.deleteHard();
    }

    @Override
    void destroy() {
        super.destroy();
        referent = null;
    }

}
