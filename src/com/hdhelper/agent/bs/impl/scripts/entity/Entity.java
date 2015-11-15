package com.hdhelper.agent.bs.impl.scripts.entity;

import com.hdhelper.agent.bs.impl.scripts.collection.DualNode;
import com.hdhelper.agent.bs.lang.BField;
import com.hdhelper.agent.bs.lang.ByteScript;
import com.hdhelper.agent.peer.RSEntity;
import com.hdhelper.agent.ref.Access;
import com.hdhelper.agent.ref.RefNode;
import com.hdhelper.agent.ref.Referable;

@ByteScript(name = "Entity")
public abstract class Entity extends DualNode implements RSEntity {

    @BField int modelHeight;


    @Override
    public int getHeight() {
        return modelHeight;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    private int state = Referable.ACTIVE;

    @Override
    public int getState() {
        return state;
    }

    //TODO make this private and establish a access interface,
    //its unsafe.
    public void add(RefNode node) {
     /*   if(state != ACTIVE)
            throw new IllegalStateException("Dead Object");*/
        if(root == null) {
            root = Access.createRoot();
        }
        Access.add(root,node);
    }

    public void destroy() {
        if(root == null) {
            state = INACTIVE;
            return;
        }
        state = Referable.ENQUEUED;
        Access.destroy(root);
        state = Referable.INACTIVE;
    }

    private RefNode root;

}
