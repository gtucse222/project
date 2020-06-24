package com.example.a222latest;

/**
 * Message class, mainly used by message adapter
 */
public class Message {
    private String senderId, text, date, time, senderName;

    public Message() {
        // don' delete this constructor, message adapter need this
    }

    public Message(String senderId, String senderName, String text, String date, String time) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.text = text;
        this.date = date;
        this.time = time;
    }


    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
