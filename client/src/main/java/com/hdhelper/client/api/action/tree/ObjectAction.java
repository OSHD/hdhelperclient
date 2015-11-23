package com.hdhelper.client.api.action.tree;


import com.hdhelper.agent.services.RSObjectDefinition;
import com.hdhelper.client.api.UID;
import com.hdhelper.client.api.action.ActionTypes;

public class ObjectAction extends EntityAction {

    public ObjectAction(int opcode,
            int UID, int local_x, int local_y) {
        super(opcode, UID, local_x, local_y);
    }

    @Override
    public int getActionIndex()  {
        return opcode - ActionTypes.OBJECT_ACTION_0;
    }

    @Override
    public int getEntityId() { //Arg0 is the UID, not the direct id of the entity
        return getObjectID();
    }

    public int getUID() {
        return arg0;
    }

    public int getObjectID() {
        return UID.getEntityID(getUID());
    }

    public RSObjectDefinition getDef0() {
        return client.getObjectDef(getObjectID());
    }

    public String getName() {
        RSObjectDefinition def = getDef0();
        if(def == null) return "null";
        return def.getName();
    }

    public String getActionName() {
        return null; //TODO
    }

    public String toString() {
        return "Object Action [object-name(id=" + getEntityId() + ")=" + getName () + ",action-name(index=" + getActionIndex() + ")=" + getActionName() + ")<" + getWorldX() + "," + getWorldY() + "> on object " /*+ getObject()*/;
    }
}
