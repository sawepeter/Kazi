package com.example.ajira.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajira.R;
import com.example.ajira.model.User;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText user_Name, password;
    Button btn_register;
    String UserType, userName, userPassword, name, token;
    ApiService apiService;
    ProgressDialog dialog;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences.Editor editor;
    TextView link_register;
    RelativeLayout layout_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        apiService = RetrofitBuilder.getAjiraBackendInstance().create(ApiService.class);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        user_Name = findViewById(R.id.user_Name);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);
        link_register = findViewById(R.id.link_register);
        layout_nav = findViewById(R.id.layout_nav);
        layout_nav.setOnClickListener(v -> {
            finish();
        });

        UserType = getIntent().getStringExtra("userType");
        editor = sharedpreferences.edit();
        Log.e("TAG", "User Type :" + UserType);
        dialog = new ProgressDialog(RegisterActivity.this);
        dialog.setMessage("Register here ...");

        link_register.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            editor.putString("userType", UserType);
            editor.apply();
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = user_Name.getText().toString().trim();
                userPassword = password.getText().toString().trim();

                if (UserType.equals("employee")) {
                    if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPassword)) {
                        dialog.show();
                        apiService.RegisterUser(userName, userPassword, UserType).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {
                                    dialog.dismiss();
                                    name = response.body().getUsername();
                                    token = response.body().getJwt();
                                    editor.putString("userType", UserType);
                                    editor.putString("userName", userName);
                                    editor.putString("password", userPassword);
                                    editor.apply();
                                    Toast.makeText(RegisterActivity.this, "Registration successful !!!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    Log.e("TAG", "token" + name);
                                } else {
                                    dialog.dismiss();
                                    Log.e("TAG", "Response unsuccessful" + response.errorBody().toString() + response.code() + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Log.e("TAG", "Failure " + t.getMessage());
                                dialog.dismiss();
                            }
                        });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Please Enter details", Toast.LENGTH_SHORT).show();
                    }

                } else if (UserType.equals("employer")) {
                    if (!TextUtils.isEmpty(userPassword) && !TextUtils.isEmpty(userName)) {
                        apiService.RegisterUser(userName, userPassword, UserType).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {
                                    dialog.dismiss();
                                    name = response.body().getUsername();
                                    token = response.body().getJwt();
                                    editor.putString("userType", UserType);
                                    editor.putString("userName", userName);
                                    editor.putString("password", userPassword);
                                    editor.commit();
                                    Toast.makeText(RegisterActivity.this, "Registration successful !!!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                } else {
                                    dialog.dismiss();
                                    Log.e("TAG", "Response unsuccessful" + response.errorBody().toString() + response.code() + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Log.e("TAG", "Failure " + t.getMessage());
                                dialog.dismiss();
                            }
                        });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Please Enter details", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


}