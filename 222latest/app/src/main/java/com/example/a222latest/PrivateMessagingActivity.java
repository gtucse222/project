package com.example.a222latest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;

public class PrivateMessagingActivity extends MessagingActivity {

    private String receiverEmail; //the person who will receive the messages
    private String receiverName; //the person who will receive the messages

    @Override
    protected void onStart() {
        super.onStart();
        messagesRef.child(getConversationKey()).addChildEventListener(new ChildEventListener() {
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


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.messaging);
//    }

    @Override
    protected void getFromIntent() {
//        receiverEmail = getIntent().getExtras().get("emails").toString();
        receiverEmail = "receiver@gtu.edu.tr"; //normally this will be get from intent
        receiverName = "Name Surname";
        receiverEmail = emailToId(receiverEmail);
    }

    /**
     * Creates unique conversation key between 2 users
     *
     * @return conversation id
     */
    @Override
    protected String getConversationKey() {
        if (currentUserMail.compareTo(receiverEmail) < 0) {
            return currentUserMail + "-" + receiverEmail;
        }
        return receiverEmail + "-" + currentUserMail;
    }

    @Override
    protected void initalizeMessagesRef() {
        messagesRef = FirebaseDatabase.getInstance().getReference().child("Messages");
    }

    @Override
    protected void saveMessageToDatabase(String conversationKey, String message) {
        String messageKey = messagesRef.push().getKey();
        HashMap<String,Object> messageInfo = buildMessageInfo(message);
        messagesRef.child(conversationKey).child(messageKey).updateChildren(messageInfo);
    }



}
