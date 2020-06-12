package com.example.a222latest;

//TODO: add top bar: shows group name or receiver name

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public abstract class MessagingActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    protected String currentUserMail;
    protected String currentUserName;
    private Button sendMessageButton;
    private EditText editMessage;
    protected DatabaseReference messagesRef;
    private final List<Message> messageList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    private RecyclerView recyclerView;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging);

        // TEST
        logIn();

        getFromIntent();
        initalizeFields();
        initalizeMessagesRef();
    }

    protected abstract void getFromIntent();
    abstract protected void initalizeMessagesRef();

    protected void initalizeFields() {
        messageAdapter = new MessageAdapter(messageList);
        recyclerView = findViewById(R.id.messageList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageAdapter);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        currentUserMail = emailToId(mAuth.getCurrentUser().getEmail());
//        currentUserName = mAuth.getCurrentUser().getDisplayName();
        currentUserName = "name surname";//FIXME
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

    protected abstract void saveMessageToDatabase(String conversationKey, String message);

    protected HashMap<String, Object> buildMessageInfo(String message) {
        HashMap<String, Object> messageInfo = new HashMap<>();
        messageInfo.put("senderId", currentUserMail);
        messageInfo.put("senderName", currentUserName);
        messageInfo.put("message", message);
        messageInfo.put("date", getCurrentDate());
        messageInfo.put("time", getCurrentTime());
        return messageInfo;
    }

    private String getCurrentTime() {
        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("HH:mm");
        return currentTimeFormat.format(calForTime.getTime());
    }

    private String getCurrentDate() {
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        return currentDateFormat.format(calForDate.getTime());
    }


    abstract protected String getConversationKey();

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

    protected void displayMessages(DataSnapshot dataSnapshot) {
        Iterator iterator = dataSnapshot.getChildren().iterator();

        while (iterator.hasNext()) {
//             order is important (same as on database)
            String date = (String) ((DataSnapshot) iterator.next()).getValue();
            String text = (String) ((DataSnapshot) iterator.next()).getValue();
            String senderId = (String) ((DataSnapshot) iterator.next()).getValue();
            String senderName = (String) ((DataSnapshot) iterator.next()).getValue();
            String time = (String) ((DataSnapshot) iterator.next()).getValue();

            Message message = new Message(senderId, senderName, text, date, time);
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