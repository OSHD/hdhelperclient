package com.hdhelper.agent.ref;

import java.util.ArrayDeque;
import java.util.Deque;

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

}
