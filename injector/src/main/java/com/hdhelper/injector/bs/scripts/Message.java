package com.hdhelper.injector.bs.scripts;

import com.bytescript.lang.BField;
import com.bytescript.lang.ByteScript;
import com.hdhelper.agent.services.RSMessage;
import com.hdhelper.injector.bs.scripts.collection.DualNode;

@ByteScript(name = "Message")
public class Message extends DualNode implements RSMessage {

    @BField int cycle;
    @BField int index;
    @BField String message;
    @BField String channel;
    @BField String sender;
    @BField int type;


    private boolean consumed = false;
    private boolean lock = false;
    public boolean invisible = false;


    @Override
    public int getCycle() {
        return cycle;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getChannel() {
        return channel;
    }

    @Override
    public String getSender() {
        return sender;
    }

    @Override
    public int getType() {
        return type;
    }



    @Override
    public void setType(int type) {
        chkAccess();
        this.type = type;
    }

    @Override
    public void setSender(String sender) {
        chkAccess();
        this.sender = sender;
    }

    @Override
    public void setMessage(String msg) {
        chkAccess();
        this.message = msg;
    }

    @Override
    public void setChannel(String channel) {
        chkAccess();
        this.channel = channel;
    }

    @Override
    public void lock() {
        lock = true;
    }

    @Override
    public void consume() {
        if(lock)
            throw new IllegalStateException("Locked:" + toString());
        consumed = true;
        invisible = false;
    }

    @Override
    public void setVisible(boolean visible) {
        chkAccess();
        this.invisible = !visible;
    }

    public void chkAccess() {
        if(lock)
            throw new IllegalStateException("Message Locked:" + toString());
        if(consumed)
            throw new IllegalStateException("Message Consumed:" + toString());
    }

    @Override
    public String toString() {
        return "Message: (index=" + index + ",type=" + type + ",msg=" + message + ",channel=" + channel + ",sender=" + sender + ")";
    }

}
