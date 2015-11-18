package com.hdhelper.injector.bs.scripts.entity;

import com.hdhelper.agent.bs.scripts.collection.DualNode;
import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSEntity;
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

    //TODO add constructor merge support for ByteScripts
    private int state = Referable.ACTIVE;

    @Override
    public int getState() {
        return state;
    }

    //TODO make this private and establish a access interface,
    //its unsafe.
    public void add(RefNode node) {
        /*if(state == INACTIVE)
            throw new IllegalStateException("Dead Object");*/
        if(root == null) {
            root = Access.createRoot();
        }
        Access.add(root,node);
    }

    //TODO remove this from public scope
    public void destroy() {
        if(root == null) { // No collections to notify
            state = INACTIVE;
            return;
        }
        state = Referable.PENDING;
        Access.destroy(root); //Notify interested collections that were being destroyed
        state = Referable.INACTIVE;
    }

    @Override
    public boolean exists() {
        return state == ACTIVE;
    }

    private RefNode root;

}
