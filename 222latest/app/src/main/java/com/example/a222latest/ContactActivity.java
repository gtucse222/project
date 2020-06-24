package com.example.a222latest;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import java.util.TreeSet;
import java.util.UUID;

/**
 * Class that retrieves data from the database and keeps it in a balanced tree
 * @author zafer
 */
public class ContactActivity extends AppCompatActivity implements ContactAdapter.OnNoteListener {

    private DatabaseReference teacherRef;

    private FirebaseDatabase database;
    private Button button2;
    ArrayList<String> myList = new ArrayList<>();
    //TreeSet<UserC> users;
    private FirebaseUser mAuth;
    RedBlackTree<UserC> users;
    ContactAdapter ca;
    RecyclerView rw2;
    boolean group = false;

    /**
     * The method we take from the database in real-time and add to the balanced tree we created
     * @param savedInstanceState savedInstance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        androidx.appcompat.widget.SearchView searchView = findViewById(R.id.searchButton);

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ca.getFilter().filter(newText);
                return true;
            }
        });
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        //users = new TreeSet<>();
        users = new RedBlackTree<>();
        rw2 = findViewById(R.id.rw2);
        rw2.setLayoutManager(new LinearLayoutManager(this));
        button2=findViewById(R.id.button);
        button2.setEnabled(false);
        teacherRef = FirebaseDatabase.getInstance().getReference().child("Members");
        teacherRef.addListenerForSingleValueEvent(new ValueEventListener() {

            /**
             * The method that runs the changes when there are any changes in the database.
             * I add the information we receive as a reference to a temporary reference and add it to the tree.
             * @param dataSnapshot snapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Member memb = ds.getValue(Member.class);

                    if(!mAuth.getEmail().equals(memb.getMailAddress())){
                        String a = memb.getName() + " " + memb.getSurname();
                        String b = memb.getMailAddress();
                        UserC temp = new UserC();
                        temp.setName(a);
                        temp.seteMail(b);
                        users.add(temp);
                    }

                }
                sendAdapter();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("teacher");
            }
        });

    }

    /**
     * I send it to the adapter class that I created in order to display the tree that I created from the database.
     */
    public void sendAdapter() {
        ca = new ContactAdapter(users, group, this);
        rw2.setAdapter(ca);
    }

    /**
     * When the create group button is clicked, the method that allows people to add to the group instead of sending it for chat when they select the contacts
     * @param view view
     */
    public void addGroup(View view) {
        group = true;
        button2.setEnabled(true);
        ca = new ContactAdapter(users, group, this);
        rw2.setAdapter(ca);
    }

    /**
     * After pressing the group done button, the method that generates a special id for the group and calls fragment to get the name of the group.
     * @param view view
     */
    public void createGroup(View view) {

        button2.setEnabled(false);
        GroupDialogFragment gdf = new GroupDialogFragment();
        gdf.show(getFragmentManager(), "Group Name");
        myList = ca.getGroup();
        gdf.setMails(myList);
        UUID uuid=UUID.randomUUID();
        gdf.setGroupId(String.valueOf(uuid));

    }

    @Override
    public void OnNoteClick(int position) {

    }

    @Override
    public void OnLongClick(int position) {

    }


}


