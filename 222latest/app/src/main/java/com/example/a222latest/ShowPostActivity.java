package com.example.a222latest;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
        //actionBar.setTitle("Profile");
        logIn();
        firebaseAuth = FirebaseAuth.getInstance();


        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, new HomeFragment()).commit();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_post);
        setSupportActionBar(myToolbar);
        //actionBar.setTitle("Home");

    }

    private void logIn() {
        String email = "deneme@gmail.com";
        String password = "123456";


        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Loggedin", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        });
    }

    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // mProfileTv.setText(user.getEmail());
        } else {
            startActivity(new Intent(ShowPostActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_post, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.menu.menu_post)
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }
}