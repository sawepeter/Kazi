package com.example.ajira.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.ajira.R;

public class LoginActivity extends AppCompatActivity {

    EditText user_Name,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*password = findViewById(password)*/
    }
}