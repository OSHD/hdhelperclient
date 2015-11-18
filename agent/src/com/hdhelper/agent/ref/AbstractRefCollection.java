package com.hdhelper.agent.ref;

import java.util.Iterator;

public abstract class AbstractRefCollection<R extends Referable> implements Iterable<R> {

    final CollectionNode root = new CollectionNode();

    public AbstractRefCollection() {
        this.root.colNext = this.root;
        this.root.colPrev = this.root;
    }

    public abstract Ref<R> add(R referent);

    public abstract boolean contains(R referent);

    public abstract boolean remove(R referent);


    protected Ref<R> addUnsafe(R referent) {
        Ref<R> ref = new Ref<R>(referent);
        add0(ref);
        referent.add(ref); //Bind ref to the referent
        return ref;
    }

    private void add0(CollectionNode node) {
        node.colPrev = this.root.colPrev;
        node.colNext = this.root;
        node.colPrev.colNext = node;
        node.colNext.colPrev = node;
    }

    public void accept(RefVisitor<R> visitor) {
        CollectionNode root = this.root;
        CollectionNode next = root.colNext;
        while (next != root) {
            CollectionNode next0 = next.colNext;
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



    public boolean isEmpty() {
        return root.colNext == root;
    }

    public void clear() {
        CollectionNode root = this.root;
        while (root.colNext != root) {
            root.colNext.deleteHard();
        }
    }




    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private final class RefQueueIterator implements Iterator<R> {

        CollectionNode next;
        CollectionNode current = null;

        RefQueueIterator() {
            this.next = root.colNext;
            this.current = null;
        }

        @Override
        public R next() {
            CollectionNode var1 = this.next;
            if (var1 == root) {
                var1 = null;
                this.next = null;
            } else {
                this.next = var1.colNext;
            }

            this.current = var1;
            return ((Ref<R>)var1).referent;
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
