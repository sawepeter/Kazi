package com.example.ajira.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.ajira.R;
import com.example.ajira.fragments.EarningsFragment;
import com.example.ajira.fragments.HomeFragment;
import com.example.ajira.fragments.AdminUnpaidFragment;
import com.example.ajira.fragments.WorkerProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class JobSeekerDashBoard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    LinearLayout l1;
    BottomNavigationView bottom_navigation;
    FloatingActionButton updateWorkerProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(this);
        bottom_navigation.setSelectedItemId(R.id.Home);
        updateWorkerProfile = findViewById(R.id.updateWorkerProfile);

        updateWorkerProfile.setOnClickListener(v -> {
            startActivity(new Intent(JobSeekerDashBoard.this, AddNewWorkerProfile.class));
        });
    }

    HomeFragment homeFragment = new HomeFragment();
    EarningsFragment earningsFragment = new EarningsFragment();
    WorkerProfileFragment workerProfileFragment = new WorkerProfileFragment();



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commitAllowingStateLoss();
                return true;

            case R.id.Applications:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, earningsFragment).commit();
                return true;

            case R.id.Profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, workerProfileFragment).commit();
                return true;
        }
        return false;
    }
}