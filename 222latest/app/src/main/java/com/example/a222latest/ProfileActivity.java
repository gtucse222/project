package com.example.a222latest;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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


public class ProfileActivity extends AppCompatActivity {

    private String memberMail;
    public String someString;
    DatabaseReference memberRef;
    String currentUserMail = "aaa@gtu.edu.tr";
    String legacymemberShip;
    String legacyname;
    String legacysurname;
    int foundMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilelayout);

        foundMember = 0;

        memberRef = FirebaseDatabase.getInstance().getReference().child("Members").child("student");
        memberRef.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    long size = dataSnapshot.getChildrenCount();
                    String data;
                    Iterator iterator = dataSnapshot.getChildren().iterator();

                    while (iterator.hasNext()) {
                        Iterator iterator2 = ((DataSnapshot) iterator.next()).getChildren().iterator();
                        while(iterator2.hasNext()){
                            String mailAdress = (String) ((DataSnapshot) iterator2.next()).getValue();
                            String memberShip = (String) ((DataSnapshot) iterator2.next()).getValue();
                            String name = (String) ((DataSnapshot) iterator2.next()).getValue();
                            String password = (String) ((DataSnapshot) iterator2.next()).getValue();
                            String surname = (String) ((DataSnapshot) iterator2.next()).getValue();
                            if(mailAdress.equals(currentUserMail)){
                                System.out.println("Mail adress is " + mailAdress + " and compared to " + currentUserMail);
                                foundMember = 1;
                                legacyname = name;
                                legacysurname = surname;
                                legacymemberShip = memberShip;
                            }
                        }
                    }
                    System.out.println("Found member is " + foundMember);
                    if(foundMember == 1){
                        TextView t = (TextView)findViewById(R.id.name_text);
                        t.setText(legacyname);
                        TextView t1 = (TextView)findViewById(R.id.surname_text);
                        t1.setText(legacysurname);
                        TextView t2 = (TextView)findViewById(R.id.membership_text);
                        t2.setText(legacymemberShip);
                        TextView t3 = (TextView)findViewById(R.id.mail_text);
                        t3.setText(currentUserMail);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //handle databaseError
                }
            });
            foundMember = 0;
            memberRef = FirebaseDatabase.getInstance().getReference().child("Members").child("teacher");
            memberRef.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long size = dataSnapshot.getChildrenCount();
                String data;
                Iterator iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext()) {
                    Iterator iterator2 = ((DataSnapshot) iterator.next()).getChildren().iterator();
                    while(iterator2.hasNext()){
                        String mailAdress = (String) ((DataSnapshot) iterator2.next()).getValue();
                        String memberShip = (String) ((DataSnapshot) iterator2.next()).getValue();
                        String name = (String) ((DataSnapshot) iterator2.next()).getValue();
                        String password = (String) ((DataSnapshot) iterator2.next()).getValue();
                        String surname = (String) ((DataSnapshot) iterator2.next()).getValue();
                        if(mailAdress.equals(currentUserMail)){
                            foundMember = 1;
                            legacyname = name;
                            legacysurname = surname;
                            legacymemberShip = memberShip;
                        }
                    }
                    System.out.println("Found member is " + foundMember);
                    if(foundMember == 1){
                        TextView t = (TextView)findViewById(R.id.name_text);
                        t.setText(legacyname);
                        TextView t1 = (TextView)findViewById(R.id.surname_text);
                        t1.setText(legacysurname);
                        TextView t2 = (TextView)findViewById(R.id.membership_text);
                        t2.setText(legacymemberShip);
                        TextView t3 = (TextView)findViewById(R.id.mail_text);
                        t3.setText(currentUserMail);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //handle databaseError
            }
        });
    }
}
