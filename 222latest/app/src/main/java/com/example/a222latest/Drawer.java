package com.example.a222latest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class Drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.login:
                startActivity(new Intent(this, LoginMainActivity.class));
                break;
            case R.id.posts:
                startActivity(new Intent(this, ShowPostActivity.class));
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
        }
        return false;
    }
}
