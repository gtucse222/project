package com.example.a222latest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        Intent intent1 = new Intent(this, loginActivity.class);
        startActivity(intent1);
    }

    public void sample(View view) {
        //guest
        Intent intent = new Intent(this, ShowPostActivity.class);
        intent.putExtra("guest", true);
        startActivity(intent);
    }

    public void select(View view) {
        Intent intent = new Intent(this, selectMembershipActivity.class);
        startActivity(intent);
    }
}
