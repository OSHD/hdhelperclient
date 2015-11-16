package com.hdhelper.agent.ref;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;

//We do not guaranteed that only one of a added referent exists
//within this collection. It's not recommended to add multiple
//references to a single queue, as it's redundant. This allows
//for a faster alternative over a RefSet for it does not lookup
//if a referent already exists before adding.
public class RefQueue<R extends Referable> extends AbstractRefCollection<R>   {

    public RefQueue() {
        super();
    }

    @Override
    public Ref<R> add(R referent) {
        return addUnsafe(referent);
    }

    @Override
    public boolean contains(R referent) {
        CollectionNode root = this.root;
        CollectionNode next = root;
        while ((next = next.colNext) != root) {
            if( ((Ref<R>)next).referent == referent ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(R referent) {
        CollectionNode root = this.root;
        CollectionNode next = root;
        while ((next = next.colNext) != root) {
            if( ((Ref<R>)next).referent == referent ) {
                next.deleteHard();
            }
        }
        return false;
    }

    // Find all refs that are for the referent.
    // When there is more then one, it's redundant,
    // and slows down finalization if the client has
    // to processes more then is required.
    public Deque<Ref<R>> lookup(R referent) {
        CollectionNode root = this.root;
        CollectionNode next = root;
        ArrayDeque<Ref<R>> refs = new ArrayDeque<Ref<R>>();
        while ((next = next.colNext) != root) {
            if( ((Ref<R>)next).referent == referent ) {
                refs.add((Ref<R>)next);
            }
        }
        return refs;
    }

    // Clear duplicate/redundant eateries
    public Deque<R> purge() {
        CollectionNode root = this.root;
        CollectionNode next = root;
        HashSet<R> existing = new HashSet<R>(); // We shall use this to tell if we hit an existing entry
        ArrayDeque<R> dups  = new ArrayDeque<R>();
        R ref;
        while ((next = next.colNext) != root) {
            ref = ((Ref<R>)next).referent;
            if(!existing.add(ref)) { //Returns false if the entry exists already
                dups.add(ref);
                next.deleteHard();
            }
        }
        return dups;
    }

}
