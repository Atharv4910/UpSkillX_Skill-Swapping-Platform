package com.upskillx.model;

public class Message {

    private String sender;
    private String message;
    private long timeStamp;

    public Message() {

    }

    public Message(String sender, String message, long timeStamp) {
        this.sender = sender;
        this.message = message;
        this.timeStamp = timeStamp;
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    
    
}
