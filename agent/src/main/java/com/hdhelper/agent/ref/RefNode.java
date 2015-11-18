package com.hdhelper.agent.ref;

//TODO this is made public only because the client has to define its type,
//we can fix this by just making the access accept plain objects and assert
//its type
public class RefNode {
    // Used by the ref to add more collection nodes
    RefNode next;
    RefNode prev;//Only the client should know these exists

    RefNode() {
    }

    //Remove from from the list of collections to be notified
    void delete() {
        if (this.prev != null) {
            this.prev.next = this.next;
            this.next.prev = this.prev;
            this.next = null;
            this.prev = null;
        }
    }

    boolean isLinked() {
        return this.prev != null;
    }

    // Called by the client to destroy this node.
    // This is to remain in the private/trusted scope.
    void destroy() {
        delete();
    }

}
