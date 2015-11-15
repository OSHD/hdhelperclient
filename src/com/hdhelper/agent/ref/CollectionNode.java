package com.hdhelper.agent.ref;

class CollectionNode extends RefNode {

    CollectionNode colNext;
    CollectionNode colPrev;

    CollectionNode() {
    }

    //Remove from the collection
    private void deleteDual() {
        if (this.colPrev != null) {
            this.colPrev.colNext = this.colNext;
            this.colNext.colPrev = this.colPrev;
            this.colNext = null;
            this.colPrev = null;
        }
    }

    void deleteHard() {
        super.delete();
        deleteDual();
    }

    void destroy() {
        deleteHard();
    }


}
