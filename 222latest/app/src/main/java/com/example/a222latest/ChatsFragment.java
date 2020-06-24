package com.example.a222latest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {
    private View chatsFragmentView;
    private ListView listView;
    private ArrayAdapter<Chat> arrayAdapter;
    private ArrayList<Chat> chats;
    private DatabaseReference privateMessagesRef;

    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        chatsFragmentView = inflater.inflate(R.layout.fragment_chats, container, false);

        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String userId = MessagingActivity.emailToId(userEmail);
        Toast.makeText(getContext(), userId, Toast.LENGTH_SHORT).show();

        privateMessagesRef = FirebaseDatabase.getInstance().getReference().
                child("Members").child(userId).child("privateMessages");
        initalizeFields();

        retrieveAndDisplayChatHistory();

        return chatsFragmentView;
    }


    /**
     * calls update method when any change detected in database reference
     */
    private void retrieveAndDisplayChatHistory() {
        privateMessagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateChats(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Updates message history entries and sorts by last message time
     *
     * @param dataSnapshot
     */
    private void updateChats(DataSnapshot dataSnapshot) {
        for (DataSnapshot d : dataSnapshot.getChildren()) {
            String chatName = (String) d.getValue();
            String chatKey = d.getKey();
            FirebaseDatabase.getInstance().getReference().child("Messages").child(chatKey).child("lastMessageTime").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Long lastMessageTime = (Long) dataSnapshot.getValue();
                    Chat c = new Chat(chatName, chatKey, lastMessageTime);
                    chats.add(c);
                    Collections.sort(chats);
                    arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        arrayAdapter.notifyDataSetChanged();
    }

    private void initalizeFields() {
        chats = new ArrayList<>();
        listView = chatsFragmentView.findViewById(R.id.listViewChats);
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, chats);
        String currentUserMail = MessagingActivity.emailToId(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String key = chats.get(position).id;
            String name = chats.get(position).name;
            Intent intent = new Intent(getContext(), PrivateMessagingActivity.class);
            intent.putExtra("receiverName", name);
            intent.putExtra("messagingKey", key.replace(currentUserMail, "").replace("-", ""));
            startActivity(intent);
        });
    }

}
