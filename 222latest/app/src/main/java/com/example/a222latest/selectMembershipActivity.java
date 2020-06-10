package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class selectMembershipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_membership);
    }

    public void signStudent(View view) {
        Intent intent = new Intent(this,signActivity.class);
        intent.putExtra("membership", "student");
        startActivity(intent);
    }

    public void signTeacher(View view) {
        Intent intent = new Intent(this,signActivity.class);
        intent.putExtra("membership", "teacher");
        startActivity(intent);
    }
}
