package com.example.ajira.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajira.R;
import com.example.ajira.model.User;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText user_Name, password;
    Button btn_login;
    String userType, userName, userPassword,day,time;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    ApiService apiService;
    SharedPreferences.Editor editor;
    ProgressDialog dialog;
    TextView link_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        apiService = RetrofitBuilder.getAjiraBackendInstance().create(ApiService.class);


        user_Name = findViewById(R.id.user_Name);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        link_register = findViewById(R.id.link_register);

        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Please Wait ...");

        userType = sharedpreferences.getString("userType", "");
        userType = getIntent().getStringExtra("userType");
        user_Name.setText(sharedpreferences.getString("userName", ""));
        password.setText(sharedpreferences.getString("password", ""));
        Log.e("TAG", "UserType :" + userType);
        Toast.makeText(this, ""+userType, Toast.LENGTH_SHORT).show();

        link_register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            intent.putExtra("userType", userType);
            startActivity(intent);
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                userName = user_Name.getText().toString().trim();
                userPassword = password.getText().toString().trim();
                //HashMap data
                HashMap<String, String> credentials = new HashMap<>();
                credentials.put("username", userName);
                credentials.put("password", userPassword);

                if (userType.equals("employee")) {

                    apiService.LoginUser(credentials).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                dialog.dismiss();
                                String token = response.body().getJwt();
                                String username = response.body().getUsername();

                                //add to sharedpreferences
                                editor.clear();
                                editor.putString("token", token);
                                editor.putString("username", username);
                                editor.apply();

                                Log.e("TAG", "token" + token);

                                startActivity(new Intent(LoginActivity.this, JobSeekerDashBoard.class));

                                Toast.makeText(LoginActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();

                            } else {
                                dialog.dismiss();
                                Log.e("TAG", "Response unsuccessful" + response.errorBody().toString() + response.code() + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            dialog.dismiss();
                            Log.e("TAG", "Failed " + t.getMessage());
                        }
                    });


                } else if (userType.equals("employer")) {

                    apiService.LoginUser(credentials).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                dialog.dismiss();
                                String token = response.body().getJwt();
                                String username = response.body().getUsername();

                                //add to sharedpreferences
                                editor.clear();
                                editor.putString("token", token);
                                editor.putString("username", username);
                                editor.apply();

                                startActivity(new Intent(LoginActivity.this, RecruiterDashBoard.class));

                                Toast.makeText(LoginActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();

                            } else {
                                dialog.dismiss();
                                Log.e("TAG", "Response unsuccessful" + response.errorBody().toString() + response.code() + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            dialog.dismiss();
                            Log.e("TAG", "Failed " + t.getMessage());
                        }
                    });

                } else if (userType.equals("admin")){
                    apiService.LoginUser(credentials).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                dialog.dismiss();
                                String token = response.body().getJwt();
                                String username = response.body().getUsername();

                                //add to sharedpreferences
                                editor.clear();
                                editor.putString("token", token);
                                editor.putString("username", username);
                                editor.apply();

                                startActivity(new Intent(LoginActivity.this, AdminDashBoard.class));

                                Toast.makeText(LoginActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();

                            } else {
                                dialog.dismiss();
                                Log.e("TAG", "Response unsuccessful" + response.errorBody().toString() + response.code() + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            dialog.dismiss();
                            Log.e("TAG", "Failed " + t.getMessage());
                        }
                    });
                }
            }
        });


    }
}