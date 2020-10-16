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
import android.widget.Toast;

import com.example.ajira.R;
import com.example.ajira.model.User;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText user_Name, password;
    Button btn_login;
    String userType, userName, userPassword;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    ApiService apiService;
    SharedPreferences.Editor editor;
    RelativeLayout layout_nav;
    ProgressDialog dialog;

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
        layout_nav = findViewById(R.id.layout_nav);

        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Logging ...");

        userType = sharedpreferences.getString("userType", "");
        user_Name.setText(sharedpreferences.getString("userName", ""));
        password.setText(sharedpreferences.getString("password", ""));
        Log.e("TAG", "UserType :" + userType);

        layout_nav.setOnClickListener(v -> {
            finish();
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                String token = response.body().getJwt();
                                String username = response.body().getUsername();

                                //add to sharedpreferences
                                editor.clear();
                                editor.putString("token", token);
                                editor.putString("username", username);
                                editor.apply();

                                Log.e("TAG", "token" +token);

                                startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));

                                Toast.makeText(LoginActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();

                            } else {
                                Log.e("TAG", "Response unsuccessful" + response.errorBody().toString() + response.code() + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.e("TAG", "Failed " + t.getMessage());
                        }
                    });


                } else if (userType.equals("employer")) {

                    apiService.LoginUser(credentials).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
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
                                Log.e("TAG", "Response unsuccessful" + response.errorBody().toString() + response.code() + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.e("TAG", "Failed " + t.getMessage());
                        }
                    });

                }
            }
        });


    }
}