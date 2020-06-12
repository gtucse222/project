package com.example.a222latest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class GroupMessagingActivity extends MessagingActivity {


    private String groupName;
    private String groupID;

    @Override
    protected void onStart() {
        super.onStart();
        messagesRef.addChildEventListener(new ChildEventListener() {
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
        getSupportActionBar().setTitle(groupName);
    }


    @Override
    protected void getFromIntent() {
        this.groupID = "this_is_group_id";
        this.groupName = "Avengers";
    }

    @Override
    protected String getConversationKey() {
        return this.groupID;
    }

    protected void initalizeMessagesRef() {
        messagesRef = FirebaseDatabase.getInstance().getReference().child("Groups")
                .child(groupID).child("Messages");
    }

    @Override
    protected void saveMessageToDatabase(String conversationKey, String message) {
        String messageKey = messagesRef.push().getKey();
        HashMap<String,Object> messageInfo = buildMessageInfo(message);
        messagesRef.child(messageKey).updateChildren(messageInfo);
    }

}
