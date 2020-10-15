package com.example.ajira.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        apiService = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);

        user_Name = findViewById(R.id.user_Name);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);
        UserType = getIntent().getStringExtra("userType");
        Log.e("TAG", "User Type :" + UserType);
        dialog = new ProgressDialog(RegisterActivity.this);
        dialog.setMessage("Register here ...");

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
                                } else {
                                    Log.e("TAG", "Response unsuccessful" + response.code() + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Log.e("TAG", "Failure " + t.getMessage());
                                dialog.dismiss();
                            }
                        });
                    }

                } else if (UserType.equals("employer")) {
                    apiService.RegisterUser(userName, userPassword, UserType).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                dialog.dismiss();
                                name = response.body().getUsername();
                                token = response.body().getJwt();
                            } else {
                                dialog.dismiss();
                                Log.e("TAG", "Response unsuccessful" + response.code() + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.e("TAG", "Failure " + t.getMessage());
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

    }


}