package com.example.ajira.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ajira.R;
import com.example.ajira.model.EarningModel;
import com.example.ajira.model.JobPostResponse;
import com.example.ajira.model.JobUpdateResponse;
import com.example.ajira.model.WorkerRequest;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;
import com.google.gson.Gson;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewWorkerProfile extends AppCompatActivity {

    ImageView image_back;
    ApiService apiService;
    Button btn_Post;
    EditText location, age, skill_name, phoneNumber;
    String Location, Age, Skill_name, PhoneNumber,token;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    ProgressDialog dialog;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_worker_profile);

        apiService = RetrofitBuilder.getAjiraBackendInstance().create(ApiService.class);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        token = sharedpreferences.getString("token", "");

        dialog = new ProgressDialog(AddNewWorkerProfile.this);
        dialog.setMessage("Posting Job ...");

        image_back = findViewById(R.id.image_back);
        btn_Post = findViewById(R.id.btn_Post);
        location = findViewById(R.id.location);
        age = findViewById(R.id.age);
        skill_name = findViewById(R.id.skill_name);
        phoneNumber = findViewById(R.id.phoneNumber);
        image_back.setOnClickListener(v -> {
            finish();
        });

        btn_Post.setOnClickListener(v -> {

            dialog.show();

            Location = location.getText().toString().trim();
            // EmployerName = job_EmployerName.getText().toString().trim();
            Age = age.getText().toString().trim();
            Skill_name = skill_name.getText().toString().trim();
            PhoneNumber = phoneNumber.getText().toString().trim();

            String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

            WorkerRequest workerRequest = new WorkerRequest();
            workerRequest.setLocation(Location);
            workerRequest.setAge(Age);
            workerRequest.setSkillName(Skill_name);
            workerRequest.setPhoneNumber(PhoneNumber);

            //add profile to shared perferences
            editor.putString("Location", Location);
            editor.putString("Age", Age);
            editor.putString("Skill_name", Skill_name);
            editor.putString("PhoneNumber", PhoneNumber);
            editor.apply();



            Log.e("TAG", "Request is: " + new Gson().toJson(workerRequest));

            apiService.createWorkerProfile("Bearer " + token, workerRequest).enqueue(new Callback<JobUpdateResponse>() {
                @Override
                public void onResponse(Call<JobUpdateResponse> call, Response<JobUpdateResponse> response) {
                    if (response.isSuccessful()) {
                        dialog.dismiss();
                        String jobTitle = response.body().getMsg();
                        Log.e("TAG", "Response successful" + response.code() + jobTitle);
                        Toast.makeText(AddNewWorkerProfile.this, "Worker profile updated!!!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        dialog.dismiss();
                        Log.e("TAG", "Response Unsuccessful" + response.code() + response.message());
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<JobUpdateResponse> call, Throwable t) {
                    Log.e("TAG", "Failed " + t.getMessage());
                    dialog.dismiss();
                    finish();
                }
            });


        });
    }
}