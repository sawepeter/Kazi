package com.example.ajira.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.ajira.R;
import com.example.ajira.fragments.DeliveredHomeFragment;
import com.example.ajira.fragments.MyJobsFragment;
import com.example.ajira.fragments.RecruiterHomeFragment;
import com.example.ajira.fragments.RecruiterProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecruiterDashBoard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton postJob;
    BottomNavigationView recruiter_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter);

        postJob = findViewById(R.id.postJob);
        recruiter_navigation = findViewById(R.id.recruiter_navigation);
        recruiter_navigation.setOnNavigationItemSelectedListener(this);
        recruiter_navigation.setSelectedItemId(R.id.nyumbani);

        postJob.setOnClickListener(v -> {
            startActivity(new Intent(RecruiterDashBoard.this, PostJobActivity.class));
        });
    }

    RecruiterProfile recruiterProfile = new RecruiterProfile();
    RecruiterHomeFragment recruiterHomeFragment = new RecruiterHomeFragment();
    MyJobsFragment myJobsFragment = new MyJobsFragment();
    DeliveredHomeFragment deliveredHomeFragment = new DeliveredHomeFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nyumbani:
                postJob.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_recruiter, myJobsFragment).commit();
                return true;

            case R.id.active_applications:
                postJob.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_recruiter, recruiterHomeFragment).commitAllowingStateLoss();
                return true;

            case R.id.delivered_jobs:
                postJob.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_recruiter, deliveredHomeFragment).commit();
                return true;

            case R.id.recruiter_profile:
                postJob.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_recruiter, recruiterProfile).commit();
                return true;
        }
        return false;
    }
}