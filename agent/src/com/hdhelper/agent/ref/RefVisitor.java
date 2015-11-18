package com.hdhelper.agent.ref;

public interface RefVisitor<T extends Referable> {
    //We pass the ref wrapper in the case you want to delete it
    boolean visit(Ref<T> ref);
}
