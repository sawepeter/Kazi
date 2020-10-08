package com.example.ajira;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ajira.activities.DashBoardActivity;
import com.example.ajira.activities.RecruiterDashBoard;

public class MainActivity extends AppCompatActivity {

    Button btn_Start;
    Button btn_Recruiter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Start = findViewById(R.id.btn_Start);
        btn_Recruiter = findViewById(R.id.btn_Recruiter);

        btn_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DashBoardActivity.class));
            }
        });

        btn_Recruiter.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RecruiterDashBoard.class));
        });
    }
}