package com.example.a222latest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Activity to adding new post
 */
public class AddPostActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference userDbRef;

    String emailF,emailS;


    ActionBar actionbar;
    //views
    EditText titleEt,descriptionEt;
    Button uploadBtn;

    //userINfo
    String name, email, uid, dp,surname,membership;

    //progressbar
    ProgressDialog progressDialog;

    /**
     * gets the related user and takes the information and uploads it to database
     * @param savedInstanceState bundle data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        //Intent intent = getIntent();
        //String post = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        //TextView textView = findViewById(R.id.pUploadBtn);
        //textView.setText(post);



        //Test
        //logIn();
        Toast.makeText(this, "here", Toast.LENGTH_SHORT).show();
        //ctionbar = getSupportActionBar();
        //actionbar.setTitle("Add New Post");

        //enable back button in action bar
        //actionbar.setDisplayShowHomeEnabled(true);
        //actionbar.setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        checkUserStatus();

        //actionbar.setSubtitle(email);
        //get some info of user to include post
        uid =email.substring(0, email.indexOf("@")).replace(".", "");
        userDbRef = FirebaseDatabase.getInstance().getReference("Members");
        Query query = userDbRef.orderByChild("mailAddress").equalTo(email);
        String x = uid.length()+"burda";;
        Toast.makeText(AddPostActivity.this,x, Toast.LENGTH_LONG).show();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    name = ""+ds.child("name").getValue();
                    surname=""+ds.child("surname").getValue();
                    email =""+ds.child("mailAddress").getValue();
                    membership =""+ds.child("membership").getValue();
                    // dp = ""+ds.child("image").getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        //init views
        titleEt = findViewById(R.id.pTitleEt);
        descriptionEt = findViewById(R.id.pDescriptionEt);
        uploadBtn = findViewById(R.id.pUploadBtn);



        Toast.makeText(AddPostActivity.this,name, Toast.LENGTH_LONG).show();
        //upload button click listener
        uploadBtn.setOnClickListener( new View.OnClickListener() {
            //get data(Title DECC)
            @Override
            public void onClick(View v) {
                //get data(Title Description)
                String title = titleEt.getText().toString().trim();
                String description = descriptionEt.getText().toString().trim();

                if (TextUtils.isEmpty(title)){
                    Toast.makeText(AddPostActivity.this,"Enter title...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(description)){
                    Toast.makeText(AddPostActivity.this,"Enter description...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (membership.equalsIgnoreCase("student") && title.contains("seminar")) {
                    Toast.makeText(AddPostActivity.this, "You are not allowed to share posts as a seminar", Toast.LENGTH_SHORT).show();
                    return;
                }
                //post
                uploadData(title,description);
            }
        });
    }

    /**
     * uploading the data firs store in hash map and sends it to db
     * @param title post title or tag
     * @param description post description
     */
    private void uploadData(String title, final String description){
        progressDialog.setMessage("Publishing post...");
        progressDialog.show();

        //for post-image name, post id , post publish time
        String time = String.valueOf(System.currentTimeMillis());

        String filePath = "Posts/"+"post " + time;


        //post

        HashMap<Object,String>  hashMap = new HashMap<>();

        hashMap.put("uid",/*email.substring(0,email.indexOf("@"))*/uid);
        hashMap.put("uName",name+" "+surname);
        hashMap.put("uEmail",email);
        //hashMap.put("uDp",dp);
        hashMap.put("pId",time);
        hashMap.put("pTitle",title);
        hashMap.put("pDescr",description);
        hashMap.put("pTime",time);
        hashMap.put("pLikes","0");
        hashMap.put("membership",membership);

        //path to store post data
        DatabaseReference ref =FirebaseDatabase.getInstance().getReference("Posts");
        //put data in this ref
        ref.child(time).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //added in database
                progressDialog.dismiss();
                Toast.makeText(AddPostActivity.this,"Post published",Toast.LENGTH_SHORT).show();
                //reset views
                titleEt.setText("");
                descriptionEt.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //failed when uploading to database
                progressDialog.dismiss();
                Toast.makeText(AddPostActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //failed
                progressDialog.dismiss();
                Toast.makeText(AddPostActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * on start action in add post
     */
    @Override
    protected void onStart() {
        super.onStart();
        checkUserStatus();
    }

    /**
     * android resume method
     */
    @Override
    protected void onResume() {
        super.onResume();
        checkUserStatus();
    }

    /**
     * checks the user status by using firebase methods
     */
    public void checkUserStatus(){
        //get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            //user is signed in stay here
            email = user.getEmail();
            uid = user.getUid();

        }else{
            //user not signed in go to main activity
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }

    /**
     * it decides to back pressed option
     * @return
     */
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();//go to previous activity
        return super.onSupportNavigateUp();
    }

    /**
     * creates menu
     * @param menu menu
     * @return returns the super method
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main,menu);

        //menu.findItem(R.id.action_add_post).setVisible(false);
        //menu.findItem(R.id.action_search).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * to show menu options
     * @param item menu item
     * @return returns the super class
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        // if(id == R.id.action_logout) {
        //   firebaseAuth.signOut();
        //   checkUserStatus();
        //}

        return super.onOptionsItemSelected(item);
    }
}
