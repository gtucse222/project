package com.example.a222latest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button postTest = findViewById(R.id.postTest);
        Button loginTest = findViewById(R.id.loginTest);
        Button profile = findViewById(R.id.profileTest);
        Button chatTest = findViewById(R.id.chatTest);
        Button showPostTest = findViewById(R.id.show_post);
        Button contactTest = findViewById(R.id.contact);
        Button seminarTest = findViewById(R.id.seminar);

        postTest.setOnClickListener(v -> startActivity(new Intent(this, AddPostActivity.class)));
        loginTest.setOnClickListener(v -> startActivity(new Intent(this, LoginMainActivity.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        chatTest.setOnClickListener(v -> startActivity(new Intent(this, ChatHistoryActivity.class)));
        showPostTest.setOnClickListener(v -> startActivity(new Intent(this, ShowPostActivity.class)));
        contactTest.setOnClickListener(v -> startActivity(new Intent(this, ContactActivity.class)));
        seminarTest.setOnClickListener(v -> startActivity(new Intent(this, ShowSeminarActivity.class)));

    }
}
