package com.example.a222latest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Group messaging activity extends from abstract messaging activity
 */
public class GroupMessagingActivity extends MessagingActivity {

    private String groupName;
    private String groupID;

    /**
     * calls super on start and adds value event listener to database reference
     */
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


    /**
     * gets group name and id from intent
     */
    @Override
    protected void getFromIntent() {
        this.groupID = getIntent().getStringExtra("groupId");
        this.groupName = getIntent().getStringExtra("groupName");
    }

    /**
     * Conversation key (group id for groups)
     *
     * @return unique conversation key
     */
    @Override
    protected String getConversationKey() {
        return this.groupID;
    }

    protected void initalizeMessagesRef() {
        messagesRef = FirebaseDatabase.getInstance().getReference().child("Groups")
                .child(groupID).child("Messages");
    }

    /**
     * Saves message to database and updates last message time
     *
     * @param conversationKey group id
     * @param message         message text
     */
    @Override
    protected void saveMessageToDatabase(String conversationKey, String message) {
        String messageKey = messagesRef.push().getKey();
        HashMap<String, Object> messageInfo = buildMessageInfo(message);
        messagesRef.child(messageKey).updateChildren(messageInfo);

        FirebaseDatabase.getInstance().getReference().child("Groups").
                child(getConversationKey()).child("lastMessageTime").setValue(Calendar.getInstance().getTimeInMillis());
    }

}
