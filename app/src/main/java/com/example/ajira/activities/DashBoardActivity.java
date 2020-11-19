package com.example.ajira.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.ajira.R;
import com.example.ajira.fragments.ApplicationsFragment;
import com.example.ajira.fragments.HomeFragment;
import com.example.ajira.fragments.ProfileFragment;
import com.example.ajira.fragments.StatisticsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashBoardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    LinearLayout l1;
    BottomNavigationView bottom_navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(this);
        bottom_navigation.setSelectedItemId(R.id.Home);
    }

    HomeFragment homeFragment = new HomeFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    ApplicationsFragment applicationsFragment = new ApplicationsFragment();
    StatisticsFragment statisticsFragment = new StatisticsFragment();



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commitAllowingStateLoss();
                return true;

            case R.id.Applications:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, applicationsFragment).commit();
                return true;

            case R.id.Profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
                return true;
        }
        return false;
    }
}