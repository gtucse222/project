package com.example.a222latest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsFragment extends Fragment {
    private View groupFragmentView;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> groups = new ArrayList<>();
    ArrayList<String> groupKeys;
    private DatabaseReference groupMessagesRef;

    public GroupsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        groupFragmentView = inflater.inflate(R.layout.fragment_groups, container, false);

        String email = "deneme@gmail.com";
        String password = "123456";

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "logged in", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
        });

        String userId = FirebaseAuth.getInstance().getUid();

        groupMessagesRef = FirebaseDatabase.getInstance().getReference().
                child("Users").child(userId).child("groups");

        initalizeFields();

        retrieveAndDisplayChatHistory();


        return groupFragmentView;
    }

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

    private void updateChats(DataSnapshot dataSnapshot) {
        for (DataSnapshot d : dataSnapshot.getChildren()) {
            String groupId = d.getKey();
            String groupName = (String) d.getValue();
            groups.add(groupName);
            groupKeys.add(groupId);
        }
        arrayAdapter.notifyDataSetChanged();
    }

    private void initalizeFields() {
        groupKeys = new ArrayList<>();
        listView = groupFragmentView.findViewById(R.id.listViewGroups);
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, groups);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String key = groupKeys.get(position);
            Log.e("YOO", key);
            Intent intent = new Intent(getContext(), GroupMessagingActivity.class);
            intent.putExtra("groupId", key);
            startActivity(intent);
        });
    }
}
