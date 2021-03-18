package com.example.ajira.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ajira.R;
import com.example.ajira.fragments.AdminPaidFragment;
import com.example.ajira.fragments.AdminDashBoardFragment;
import com.example.ajira.fragments.AdminUnpaidFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminDashBoard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);

        bottom_navigation = findViewById(R.id.bottom_navigation_admin);
        bottom_navigation.setOnNavigationItemSelectedListener(this);
        bottom_navigation.setSelectedItemId(R.id.Profile);

    }

    AdminPaidFragment homeFragment = new AdminPaidFragment();
    AdminDashBoardFragment adminDashBoardFragment = new AdminDashBoardFragment();
    AdminUnpaidFragment adminUnpaidFragment = new AdminUnpaidFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, homeFragment).commitAllowingStateLoss();
                return true;

            case R.id.Applications:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, adminUnpaidFragment).commit();
                return true;

            case R.id.Profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, adminDashBoardFragment).commit();
                return true;
        }
        return false;
    }
}