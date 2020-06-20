package com.example.a222latest;

public class Chat implements Comparable<Chat> {
    String name;
    String id;
    Long lastMessageTime;

    public Chat(String name, String id, Long lastMessageTime) {
        this.name = name;
        this.id = id;
        this.lastMessageTime = lastMessageTime;
    }

    @Override
    public int compareTo(Chat o) {
        if (o.lastMessageTime != null && this.lastMessageTime != null)
            return (int) (o.lastMessageTime - this.lastMessageTime);
        return 0;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
