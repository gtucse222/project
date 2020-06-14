package com.example.a222latest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ShowPostActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //actionBar = getSupportActionBar();
        //actionBar.setTitle("Profile");
        logIn();
        firebaseAuth = FirebaseAuth.getInstance();

        //actionBar.setTitle("Home");
                getSupportFragmentManager().beginTransaction()
                .add(R.id.content,new HomeFragment()).commit();
    }

    private void logIn() {
        String email = "deneme@gmail.com";
        String password = "123456";


        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this,"Loggedin", Toast.LENGTH_SHORT).show();
            }
            else
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        });
    }
    private void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
           // mProfileTv.setText(user.getEmail());
        }else{
            startActivity(new Intent(ShowPostActivity.this, MainActivity.class));
            finish();
        }
    }
}