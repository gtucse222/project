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
 * Shows groups chat in chats menu
 */
public class GroupsFragment extends Fragment {
    private View groupFragmentView;
    private ListView listView;
    private ArrayAdapter<Chat> arrayAdapter;
    private ArrayList<Chat> groups = new ArrayList<>();
    private DatabaseReference groupMessagesRef;

    public GroupsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        groupFragmentView = inflater.inflate(R.layout.fragment_groups, container, false);

        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String userId = MessagingActivity.emailToId(userEmail);

        groupMessagesRef = FirebaseDatabase.getInstance().getReference().
                child("Members").child(userId).child("group");

        initalizeFields();

        retrieveAndDisplayChatHistory();

        return groupFragmentView;
    }

    /**
     * calls update chats when data changes (new message detected) in database
     */
    private void retrieveAndDisplayChatHistory() {
        groupMessagesRef.addValueEventListener(new ValueEventListener() {
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
     * Shows posts from database
     *
     * @param dataSnapshot datanapshot retrieved from database
     */
    private void updateChats(DataSnapshot dataSnapshot) {
        for (DataSnapshot d : dataSnapshot.getChildren()) {
            String groupId = d.getKey();
            String groupName = (String) d.getValue();
            FirebaseDatabase.getInstance().getReference().child("Groups").child(groupId).child("lastMessageTime").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Long lastMessageTime = (Long) dataSnapshot.getValue();
                    Chat c = new Chat(groupName, groupId, lastMessageTime);
                    groups.add(c);
                    Collections.sort(groups);
                    arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        arrayAdapter.notifyDataSetChanged();
    }

    /**
     * initilaze fields by finding them from layouts
     */
    private void initalizeFields() {
        listView = groupFragmentView.findViewById(R.id.listViewGroups);
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, groups);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String key = groups.get(position).id;
            String name = groups.get(position).name;
            Intent intent = new Intent(getContext(), GroupMessagingActivity.class);
            intent.putExtra("groupId", key);
            intent.putExtra("groupName", name);
            startActivity(intent);
        });
    }
}
