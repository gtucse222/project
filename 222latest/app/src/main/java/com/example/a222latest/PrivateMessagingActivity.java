package com.example.a222latest;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class PrivateMessagingActivity extends MessagingActivity {

    private String receiverEmail; //the person who will receive the messages
    private String receiverName; //the person who will receive the messages
    private String conversationKey;

    @Override
    protected void onStart() {
        super.onStart();
        messagesRef.child(getConversationKey()).child("messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    displayMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    displayMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void initalizeFields() {
        super.initalizeFields();
        getSupportActionBar().setTitle(receiverName);
    }

    @Override
    protected void getFromIntent() {
//        receiverEmail = getIntent().getExtras().get("emails").toString();
//        receiverEmail = "receiver@gtu.edu.tr"; //normally this will be get from intent
//        receiverName = "Name Surname";
//        receiverEmail = emailToId(receiverEmail);
//        String messagingKey = "deneme-receiver";
        String messagingKey = getIntent().getStringExtra("messagingKey");
        if (messagingKey.contains("@")) {
            receiverEmail = emailToId(messagingKey);
        } else {
            receiverEmail = messagingKey;
        }
        setConversationKey();
        receiverName = getIntent().getStringExtra("receiverName");
    }

    /**
     * Creates unique conversation key between 2 users
     *
     * @return conversation id
     */
    @Override
    protected String getConversationKey() {
        return this.conversationKey;
    }

    protected void setConversationKey() {
        currentUserMail = emailToId(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        String sender = currentUserMail;
        String receiver = receiverEmail;
        if (sender.compareTo(receiver) < 0)
            this.conversationKey = sender + "-" + receiver;
        else
            this.conversationKey = receiver + "-" + sender;
    }

//    protected void setConversationKey(String key) {
//        this.conversationKey = key;
//    }

    @Override
    protected void initalizeMessagesRef() {
        messagesRef = FirebaseDatabase.getInstance().getReference().child("Messages");
        if (conversationKey == null)
            setConversationKey();
    }

    @Override
    protected void saveMessageToDatabase(String conversationKey, String message) {
        String messageKey = messagesRef.push().getKey();
        HashMap<String, Object> messageInfo = buildMessageInfo(message);
        messagesRef.child(conversationKey).child("messages").child(messageKey).updateChildren(messageInfo);
        messagesRef.child(conversationKey).child("lastMessageTime").setValue(Calendar.getInstance().getTimeInMillis());
        FirebaseDatabase.getInstance().getReference().child("Members").
                child(currentUserMail).child("privateMessages").child(getConversationKey()).setValue(receiverEmail);
        FirebaseDatabase.getInstance().getReference().child("Members").
                child(receiverEmail).child("privateMessages").child(getConversationKey()).setValue(currentUserMail);
    }


}
