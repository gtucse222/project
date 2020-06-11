package com.example.a222latest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MessagingActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String currentUserMail;
    private String receiverEmail; //the person who will receive the messages
    private Button sendMessageButton;
    private EditText editMessage;
    private DatabaseReference messagesRef;
    private final List<Message> messageList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    private RecyclerView recyclerView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging);

        // TEST
        logIn();
        receiverEmail = "receiver@gtu.edu.tr"; //normally this will get from intent
        receiverEmail = emailToId(receiverEmail);
//        receiverEmail = getIntent().getExtras().get("emails").toString();

        messageAdapter = new MessageAdapter(messageList);
        recyclerView = findViewById(R.id.messageList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageAdapter);

        messagesRef = FirebaseDatabase.getInstance().getReference().child("Messages");

        currentUserMail = mAuth.getCurrentUser().getEmail();
        currentUserMail = emailToId(currentUserMail);
        sendMessageButton = findViewById(R.id.sendMessageButton);
        editMessage = findViewById(R.id.editMessage);

        sendMessageButton.setOnClickListener(v -> {
            sendMessage(editMessage.getText().toString());
        });

    }

    private void sendMessage(String message) {
        if (message.isEmpty()) return;
        saveMessageToDatabase(getConversationKey(), message);
        editMessage.setText("");
    }

    private void saveMessageToDatabase(String conversationKey, String message) {
        String messageKey = messagesRef.push().getKey();

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        String currentDate = currentDateFormat.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("HH:mm");
        String currentTime = currentTimeFormat.format(calForTime.getTime());

        HashMap<String, Object> messageInfo = new HashMap<>();
        messageInfo.put("sender", currentUserMail);
        messageInfo.put("message", message);
        messageInfo.put("date", currentDate);
        messageInfo.put("time", currentTime);

        messagesRef.child(conversationKey).child(messageKey).updateChildren(messageInfo);
    }

    /**
     * Creates unique conversation key between 2 users
     *
     * @return conversation id
     */
    private String getConversationKey() {
        if (currentUserMail.compareTo(receiverEmail) < 0) {
            return currentUserMail + "-" + receiverEmail;
        }
        return receiverEmail + "-" + currentUserMail;
    }


    private void logIn() {
        String email = "deneme@gmail.com";
        String password = "123456";

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "logged in", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        });
    }

    private void displayMessages(DataSnapshot dataSnapshot) {
        Iterator iterator = dataSnapshot.getChildren().iterator();

        while (iterator.hasNext()) {
//             order is important (same as on database)
            String date = (String) ((DataSnapshot) iterator.next()).getValue();
            String text = (String) ((DataSnapshot) iterator.next()).getValue();
            String sender = (String) ((DataSnapshot) iterator.next()).getValue();
            String time = (String) ((DataSnapshot) iterator.next()).getValue();

            Message message = new Message(sender, text, date, time);
            messageList.add(message);
            messageAdapter.notifyDataSetChanged();

            // scroll to bottom
            recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
        }


    }

    public static String emailToId(String email) {
        //firebase doesn't allow @ and . characters
        return email.substring(0, email.indexOf("@")).replace(".", "");
    }

}