package com.hdhelper.agent.services;

public interface RSMessage {
    int getCycle();
    int getIndex();
    String getMessage();
    String getChannel();
    String getSender();
    int getType();


    void setType(int type);
    void setSender(String sender);
    void setMessage(String message);
    void setChannel(String channel);
    void setVisible(boolean visible);

    void lock(); //Prevent any further modifications
    void consume(); //Permanently set invisible


}
