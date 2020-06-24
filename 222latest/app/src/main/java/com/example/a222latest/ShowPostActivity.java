package com.example.a222latest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * calls the fragment of home to show psots
 */
public class ShowPostActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FirebaseAuth firebaseAuth;
    Boolean guest;

    /**
     * get insances of user and shows the posts
     * if the user is guest it doesnt shown in home screen of guest
     * @param savedInstanceState to send and get other activities
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);


        guest = getIntent().getExtras().getBoolean("guest");

        firebaseAuth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar_drawer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true); //hamburger
        drawerToggle.syncState();

        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        if (!guest)
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content2, new HomeFragment()).commit();


        if (guest) {
            findViewById(R.id.guestSeePost).setVisibility(View.VISIBLE);
            navigationView = (NavigationView) findViewById(R.id.navigationView);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.chat).setVisible(false);
            nav_Menu.findItem(R.id.profile).setVisible(false);
            nav_Menu.findItem(R.id.posts).setVisible(false);
            nav_Menu.findItem(R.id.newPost).setVisible(false);
            nav_Menu.findItem(R.id.contact).setVisible(false);
        }

    }

    /**
     * to show other options on drawer
     * @param item menu item selected
     * @return returnsfalse or true according to operation
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.posts:
//                drawerLayout.closeDrawers();
                Intent intent = new Intent(this, ShowPostActivity.class);
                intent.putExtra("guest", guest);
                startActivity(intent);
                break;
            case R.id.newPost:
                startActivity(new Intent(this, AddPostActivity.class));
                break;
            case R.id.chat:
                startActivity(new Intent(this, ChatHistoryActivity.class));
                break;
            case R.id.contact:
                startActivity(new Intent(this, ContactActivity.class));
                break;
            case R.id.seminar:
                startActivity(new Intent(this, ShowSeminarActivity.class));
                break;
            case R.id.profile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.map:
                startActivity(new Intent(this, MapActivity.class));
                break;
            case R.id.foodlist:
                startActivity(new Intent(this, ListFoodActivity.class));
                break;
        }
        return false;
    }

    /**
     * first create calls
     * @param menu menu layout
     * @return retuens super class to create menu options
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!guest)
            getMenuInflater().inflate(R.menu.menu_post, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * when chosed an item in menu
     * @param item item of menu
     * @return returns the super class
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.menu.menu_post)
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }
}
