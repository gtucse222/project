package com.example.a222latest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract class of messaging that includes base methods.
 */
public abstract class MessagingActivity extends AppCompatActivity {
    /**
     * users mail, for some methods it used as first part of email (before @)
     */
    protected String currentUserMail;
    /**
     * username of the user to identify (mail)
     */
    protected String currentUserName;
    private Button sendMessageButton;
    private EditText editMessage;
    /**
     * Firebase database reference to save and retrieve messages
     */
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

        getFromIntent();
        initalizeFields();
        initalizeMessagesRef();
    }

    protected abstract void getFromIntent();

    abstract protected void initalizeMessagesRef();

    /**
     * finds view from layouts and initialize fields
     */
    protected void initalizeFields() {
        messageAdapter = new MessageAdapter(messageList);
        recyclerView = findViewById(R.id.messageList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageAdapter);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        currentUserMail = emailToId(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        currentUserName = currentUserMail;
        sendMessageButton = findViewById(R.id.sendMessageButton);
        editMessage = findViewById(R.id.editMessage);
        sendMessageButton.setOnClickListener(v -> {
            sendMessage(editMessage.getText().toString());
        });
    }


    /**
     * saves message to database and removes the text from text field
     *
     * @param message message from text view
     */
    private void sendMessage(String message) {
        if (message.isEmpty()) return;
        saveMessageToDatabase(getConversationKey(), message);
        editMessage.setText("");
    }

    protected abstract void saveMessageToDatabase(String conversationKey, String message);

    /**
     * Builds message information with sender id, sender name, message, date and time.
     *
     * @param message entered message
     * @return HashMap to push database
     */
    protected HashMap<String, Object> buildMessageInfo(String message) {
        HashMap<String, Object> messageInfo = new HashMap<>();
        messageInfo.put("senderId", currentUserMail);
        messageInfo.put("senderName", currentUserName);
        messageInfo.put("message", message);
        messageInfo.put("date", getCurrentDate());
        messageInfo.put("time", getCurrentTime());
        return messageInfo;
    }

    /**
     * Gets current time
     *
     * @return current time (hour : minute)
     */
    private String getCurrentTime() {
        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("HH:mm");
        return currentTimeFormat.format(calForTime.getTime());
    }

    /**
     * Gets current date
     *
     * @return current date (Month(first 3 letters) day, year)
     */
    private String getCurrentDate() {
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        return currentDateFormat.format(calForDate.getTime());
    }

    abstract protected String getConversationKey();

    /**
     * Shows message from database by iterating through every message and putting into adapter
     *
     * @param dataSnapshot datasnapshot retrieved from database
     */
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

    /**
     * Returns an id by getting the part before @ from emails, and removed . characters.
     * This process is required by firebase database, since it doesn't allow such characters as keys.
     *
     * @param email email
     * @return id
     */
    public static String emailToId(String email) {
        //firebase doesn't allow @ and . characters
        return email.substring(0, email.indexOf("@")).replace(".", "");
    }

}