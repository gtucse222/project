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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import java.util.TreeSet;

public class ContactActivity extends AppCompatActivity implements ContactAdapter.OnNoteListener {

    private DatabaseReference teacherRef;
    private DatabaseReference studentRef;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    ArrayList<String> myList = new ArrayList<>();
    TreeSet<UserC> users;
    ContactAdapter ca;
    RecyclerView rw2;
    boolean group = false;

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

        users = new TreeSet<>();
        rw2 = findViewById(R.id.rw2);
        rw2.setLayoutManager(new LinearLayoutManager(this));
        studentRef = FirebaseDatabase.getInstance().getReference().child("Members");
        teacherRef = FirebaseDatabase.getInstance().getReference().child("Members");
        teacherRef.child("teacher").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Member memb = ds.getValue(Member.class);

                    String a = memb.getName() + " " + memb.getSurname();
                    String b = memb.getMailAddress();

                    UserC temp = new UserC();
                    temp.setName(a);
                    temp.seteMail(b);
                    users.add(temp);

                }
                studentRef.child("student").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshott) {


                        for (DataSnapshot dss : dataSnapshott.getChildren()) {

                            Member memb = dss.getValue(Member.class);

                            String a = memb.getName() + " " + memb.getSurname();
                            String b = memb.getMailAddress();
                            UserC temp2 = new UserC();
                            temp2 = new UserC();
                            temp2.setName(a);
                            temp2.seteMail(b);
                            users.add(temp2);
                        }
                        sendAdapter();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseErrorr) {
                        System.out.println("student");
                    }
                });


                //----------------------------------------------


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("teacher");
            }
        });

    }

    public void sendAdapter() {
        ca = new ContactAdapter(users, group, this);
        rw2.setAdapter(ca);
    }

    public void addGroup(View view) {
        group = true;
        ca = new ContactAdapter(users, group, this);
        rw2.setAdapter(ca);
    }

    public void createGroup(View view) {
        String grpId = "group-Id-deneme";
        GroupDialogFragment gdf = new GroupDialogFragment();
        gdf.show(getFragmentManager(), "Group Name");
        myList = ca.getGroup();
        gdf.setMails(myList);
        gdf.setGroupId(grpId);

        /*Intent intent=new Intent(this,Message.class);
        intent.putExtra("groupId",grpId);
        intent.putStringArrayListExtra("emails",myList);
        startActivity(intent); */


    }

    @Override
    public void OnNoteClick(int position) {

    }

    @Override
    public void OnLongClick(int position) {

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.example_menu, menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) item.getActionView();
//        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                ca.getFilter().filter(newText);
//                return true;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }
}


