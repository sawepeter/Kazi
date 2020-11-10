package com.example.ajira;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ajira.activities.DashBoardActivity;
import com.example.ajira.activities.LoginActivity;
import com.example.ajira.activities.RecruiterDashBoard;
import com.example.ajira.activities.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    Button btn_Start;
    Button btn_Recruiter;
    TextView admin_login;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        btn_Start = findViewById(R.id.btn_Start);
        btn_Recruiter = findViewById(R.id.btn_Recruiter);
        admin_login = findViewById(R.id.admin_login);

        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("userType", "admin");
                startActivity(intent);
            }
        });

        btn_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                intent.putExtra("userType", "employee");
                startActivity(intent);
            }
        });

        btn_Recruiter.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            intent.putExtra("userType", "employer");
            startActivity(intent);
        });
    }


}