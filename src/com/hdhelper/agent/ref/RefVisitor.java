package com.hdhelper.agent.ref;

public interface RefVisitor<T> {
    boolean visit(Ref<T> ref);
}
