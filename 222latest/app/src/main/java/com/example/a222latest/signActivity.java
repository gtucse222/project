package com.example.a222latest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class signActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText name;
    EditText surname;
    EditText mailAddress;
    EditText password;

    Button sign;
    private FirebaseUser user;
    DatabaseReference signRef;
    SkipList<String> skipList = new SkipList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        mailAddress = findViewById(R.id.mailAddress);
        password = findViewById(R.id.password);
        user = mAuth.getCurrentUser();
        sign = (Button) findViewById(R.id.sign);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String userName = name.getText().toString();
                final String userSurname = surname.getText().toString();
                final String userMail = mailAddress.getText().toString();
                final String userPass = password.getText().toString();
                final String membership = getIntent().getStringExtra("membership");

                if (!isGtuMail(userMail)) {
                    Toast.makeText(signActivity.this, "Please enter gtu mail", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(signActivity.this, signActivity.class));

                } else {

                    mAuth.createUserWithEmailAndPassword(userMail, userPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            user.sendEmailVerification();
                            Member member = new Member();
                            member.setMailAddress(userMail);
                            member.setName(userName);
                            member.setPassword(userPass);
                            member.setSurname(userSurname);
                            member.setMembership(membership);
                            if (membership.equals("student")) {
                                signRef = FirebaseDatabase.getInstance().getReference("Members");
                                signRef.child("Members");
                                String tempMail = MessagingActivity.emailToId(userMail);
                                signRef.child(tempMail).setValue(member);
                            } else if (membership.equals("teacher")) {
                                readTeachers();
                                if (skipList.find(userMail) == null) {
                                    Toast.makeText(signActivity.this, "You are not teacher, Please choose student membership", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(signActivity.this, selectMembershipActivity.class));
                                }
                                if (skipList.find(userMail) != null) {
                                    signRef = FirebaseDatabase.getInstance().getReference("Members");
                                    signRef.child("Members");
                                    String tempMail = MessagingActivity.emailToId(userMail);
                                    signRef.child(tempMail).setValue(member);
                                }
                            } else
                                Toast.makeText(signActivity.this, "You sign up Succesfully", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(signActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(signActivity.this, signActivity.class));
                        }
                    });
                    startActivity(new Intent(signActivity.this, loginActivity.class));
                }
            }
        });
    }

    private void readTeachers() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("TeacherMails")));
            String st;
            while ((st = reader.readLine()) != null) {
                System.out.println(st);
                skipList.add(st);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isGtuMail(String userMail) {
        return userMail.endsWith("@gtu.edu.tr");
    }
}
