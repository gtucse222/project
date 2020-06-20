package com.example.a222latest;

import android.os.Build;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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
