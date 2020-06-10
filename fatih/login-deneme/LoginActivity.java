package com.example.sp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button registerButton, loginButton;
    private EditText userMail, userPass;

    private FirebaseAuth mAuth;

    private ProgressDialog loadingBar;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        initializeFields();

        registerButton.setOnClickListener(v -> registerUser());

        loginButton.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "login clicked", Toast.LENGTH_SHORT).show();

            String email = userMail.getText().toString().trim();
            String password = userPass.getText().toString();

            if (TextUtils.isEmpty(email))
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(password))
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (user.isEmailVerified())
                        Toast.makeText(LoginActivity.this, "logged in successfully", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(LoginActivity.this, "email is not verified", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(LoginActivity.this, "wrong email or password", Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void registerUser() {
        Toast.makeText(this, "registerClick", Toast.LENGTH_SHORT).show();

        String email = userMail.getText().toString().trim();
        String password = userPass.getText().toString();

        if (TextUtils.isEmpty(email))
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(password))
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            //TODO: check password length
        else if (!isGtuMail(email))
            Toast.makeText(this, "Please enter your gtu mail", Toast.LENGTH_SHORT).show();
        else {
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait, while we are creating new account for you...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this,
                            "Account created successfully, you can login after you verified " +
                                    "your e-mail by clicking the link", Toast.LENGTH_SHORT).show();
                    registerVerification();
                } else {
                    String message = task.getException().toString();
                    Toast.makeText(LoginActivity.this, "Error: " + message, Toast.LENGTH_LONG).show();
                }
                loadingBar.dismiss();
            });
        }
    }

    private void registerVerification() {
        user.sendEmailVerification().addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(LoginActivity.this,
                        "Verification email sent to " + user.getEmail(),
                        Toast.LENGTH_SHORT).show();
            } else {
                // If verification fails, display a message to the user.
                Log.w("error", "createUserWithEmail:failure", task.getException());
                Toast.makeText(LoginActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isGtuMail(String email) {
        return email.endsWith("@gtu.edu.tr");
    }

    private void initializeFields() {
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        userMail = findViewById(R.id.mail);
        userPass = findViewById(R.id.pass);
        user = mAuth.getCurrentUser();
        loadingBar = new ProgressDialog(this);
    }
}
