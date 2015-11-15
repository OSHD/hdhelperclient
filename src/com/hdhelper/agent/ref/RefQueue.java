package com.hdhelper.agent.ref;

import java.util.Iterator;

//We do not guaranteed that only one of a added referent exists
//within this collection. It's not recommended to add multiple
//references to a single queue, as it's redundant.
public class RefQueue<R extends Referable> implements Iterable<R>  {

    final CollectionNode root = new CollectionNode();

    public RefQueue() {
        this.root.dualNext = this.root;
        this.root.dualPrev = this.root;
    }

    public Ref<R> add(R referent) {
        Ref<R> existing = lookup(referent);
        if(existing != null) return existing;
        return addUnsafe(referent);
    }

    Ref<R> addUnsafe(R referent) {
        Ref<R> ref = new Ref<R>(referent);
        add0(ref);
        referent.add(ref); //Bind ref to the referent
        return ref;
    }

    private void add0(CollectionNode node) {
        node.dualPrev = this.root.dualPrev;
        node.dualNext = this.root;
        node.dualPrev.dualNext = node;
        node.dualNext.dualPrev = node;
    }




    // Assumes that only one of Ref object has a referent
    // equal to the arrgument.
    Ref<R> lookup(R referent) {
        CollectionNode root = this.root;
        CollectionNode next = root;
        while ((next = next.dualNext) != root) {
            if( ((Ref<R>)next).referent == referent ) {
                return (Ref<R>) next;
            }
        }
        return null;
    }

    public void accept(RefVisitor<R> visitor) {
        CollectionNode root = this.root;
        CollectionNode next = root.dualNext;
        while (next != root) {
            CollectionNode next0 = next.dualNext;
            if(!visitor.visit((Ref<R>)next)) {
                return;
            }
            next = next0;
        }
    }

    @Override
    public Iterator<R> iterator() {
        return new RefQueueIterator();
    }


    public void clear() {
        CollectionNode root = this.root;
        while (root.dualNext != root) {
            root.dualNext.deleteHard();
        }
    }

    private final class RefQueueIterator implements Iterator<R> {

        CollectionNode next;
        CollectionNode current = null;

        RefQueueIterator() {
            this.next = root.dualNext;
            this.current = null;
        }

        @Override
        public R next() {
            CollectionNode var1 = this.next;
            if (var1 == root) {
                var1 = null;
                this.next = null;
            } else {
                this.next = var1.dualNext;
            }

            this.current = var1;
            return (R) var1;
        }

        @Override
        public void remove() {
            if (this.current == null) {
                throw new IllegalStateException();
            } else {
                this.current.deleteHard();
                this.current = null;
            }
        }

        @Override
        public boolean hasNext() {
            return this.next != root;
        }

    }

}
