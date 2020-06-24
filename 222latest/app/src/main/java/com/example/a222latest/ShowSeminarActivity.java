package com.example.a222latest;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class ShowSeminarActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seminar);
        //actionBar.setTitle("Profile");
//        logIn();
        firebaseAuth = FirebaseAuth.getInstance();


        getSupportFragmentManager().beginTransaction()
                .add(R.id.contt,new GuestSeminarFragment()).commit();
        //Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_post);
        //setSupportActionBar(myToolbar);
        //actionBar.setTitle("Home");

    }

//    private void logIn() {
//        String email = "deneme@gmail.com";
//        String password = "123456";
//
//
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                Toast.makeText(this,"Loggedin", Toast.LENGTH_SHORT).show();
//            }
//            else
//                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
//        });
//    }
}
