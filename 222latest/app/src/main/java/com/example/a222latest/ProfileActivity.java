package com.example.a222latest;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Shows profile infos
 */
public class ProfileActivity extends AppCompatActivity {

    DatabaseReference memberRef;

    /**
     * takes the user information from data base and sends it to layout to viev
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilelayout);

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String id = MessagingActivity.emailToId(email);
        TextView mailTV;
        TextView nameTV;
        TextView surnameTV;
        TextView memberTV;

        memberRef = FirebaseDatabase.getInstance().getReference().child("Members").child(id);

        mailTV = findViewById(R.id.mail_text);
        memberTV = findViewById(R.id.membership_text);
        surnameTV = findViewById(R.id.surname_text);
        nameTV = findViewById(R.id.name_text);

        memberRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String mail = (String) dataSnapshot.child("mailAddress").getValue();
                String name = (String) dataSnapshot.child("name").getValue();
                String surname = (String) dataSnapshot.child("surname").getValue();
                String membership = (String) dataSnapshot.child("membership").getValue();

                mailTV.setText(mail);
                memberTV.setText(membership);
                surnameTV.setText(surname);
                nameTV.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
