package com.hdhelper.agent.ref;

//Ensures only one of a referent exists within the collection
public class RefSet<R extends Referable> extends AbstractRefCollection<R> {

    @Override
    public Ref<R> add(R referent) {
        Ref<R> existing = lookup(referent);
        if(existing != null) return existing;
        return addUnsafe(referent);
    }

    @Override
    public boolean contains(R referent) {
        return lookup(referent) != null;
    }

    @Override
    public boolean remove(R referent) {
        Ref<R> ref = lookup(referent);
        if(ref == null) return false;
        ref.deleteHard();
        return true;
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
