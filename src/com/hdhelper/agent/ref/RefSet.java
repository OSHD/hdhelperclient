package com.hdhelper.agent.ref;

//Ensures only one of a referent exists within the collection
public class RefSet<R extends Referable> extends AbstractRefCollection<R> {

    @Override
    public Ref<R> add(R referent) {
        Ref<R> existing = lookup(referent);
        if(existing != null) return existing;
        return addUnsafe(referent);
    }

    // Assumes that only one of Ref object has a referent
    // equal to the argument. Which is guaranteed by the
    // literal definition of this type.
    public Ref<R> lookup(R referent) {
        CollectionNode root = this.root;
        CollectionNode next = root;
        while ((next = next.colNext) != root) {
            if( ((Ref<R>)next).referent == referent ) {
                return (Ref<R>) next;
            }
        }
        return null;
    }

}
