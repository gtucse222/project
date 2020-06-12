package com.example.a222latest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button postTest = findViewById(R.id.postTest);
        Button messagingTest = findViewById(R.id.messagingTest);
        Button loginTest = findViewById(R.id.loginTest);
        Button groupMessagingTest = findViewById(R.id.groupMessagingTest);
        Button profile = findViewById(R.id.profileTest);

        messagingTest.setOnClickListener(v -> startActivity(new Intent(this, PrivateMessagingActivity.class)));
        postTest.setOnClickListener(v -> startActivity(new Intent(this, AddPostActivity.class)));
        loginTest.setOnClickListener(v -> startActivity(new Intent(this, LoginMainActivity.class)));
        groupMessagingTest.setOnClickListener(v -> startActivity(new Intent(this, GroupMessagingActivity.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
    }
}
