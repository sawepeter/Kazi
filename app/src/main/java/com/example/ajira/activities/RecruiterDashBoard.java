package com.example.ajira.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ajira.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecruiterDashBoard extends AppCompatActivity {

    FloatingActionButton postJob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter);

        postJob = findViewById(R.id.postJob);
        postJob.setOnClickListener(v -> {
            startActivity(new Intent(RecruiterDashBoard.this, PostJobActivity.class));
        });
    }
}