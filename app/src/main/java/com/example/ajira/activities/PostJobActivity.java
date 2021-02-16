package com.example.ajira.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ajira.R;
import com.example.ajira.fragments.MyJobsFragment;
import com.example.ajira.model.JobPostRequest;
import com.example.ajira.model.JobPostResponse;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;
import com.google.gson.Gson;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostJobActivity extends AppCompatActivity {

    ImageView image_back;
    ApiService apiService;
    Button btn_Post;
    EditText job_title, job_EmployerName, employerPhone, Job_location, job_salary;
    String jobTitle, EmployerName, EmployerPhone, JobLocation, Job_salary, token;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        apiService = RetrofitBuilder.getAjiraBackendInstance().create(ApiService.class);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedpreferences.getString("token", "");

        dialog = new ProgressDialog(PostJobActivity.this);
        dialog.setMessage("Posting Job ...");

        image_back = findViewById(R.id.image_back);
        btn_Post = findViewById(R.id.btn_Post);
        job_title = findViewById(R.id.job_title);
        job_EmployerName = findViewById(R.id.job_EmployerName);
        employerPhone = findViewById(R.id.employerPhone);
        Job_location = findViewById(R.id.Job_location);
        job_salary = findViewById(R.id.job_salary);
        image_back.setOnClickListener(v -> {
            finish();
        });

        btn_Post.setOnClickListener(v -> {

            dialog.show();

            jobTitle = job_title.getText().toString().trim();
           // EmployerName = job_EmployerName.getText().toString().trim();
            EmployerPhone = employerPhone.getText().toString().trim();
            JobLocation = Job_location.getText().toString().trim();
            Job_salary = job_salary.getText().toString().trim();

            String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

            JobPostResponse jobPostResponse = new JobPostResponse();
            jobPostResponse.setJobTitle(jobTitle);
           // jobPostResponse.setEmployerName(EmployerName);
            jobPostResponse.setEmployerPhone(EmployerPhone);
            jobPostResponse.setJobAmount(Job_salary);
            jobPostResponse.setJobLocation(JobLocation);
            jobPostResponse.setJobDeadline(currentDateTimeString);
            jobPostResponse.setJobType("Temporary");

            Log.e("TAG", "Request is: " + new Gson().toJson(jobPostResponse));

            apiService.createJobList("Bearer " + token, jobPostResponse).enqueue(new Callback<JobPostResponse>() {
                @Override
                public void onResponse(Call<JobPostResponse> call, Response<JobPostResponse> response) {
                    if (response.isSuccessful()) {
                        dialog.dismiss();
                        String jobTitle = response.body().getJobTitle();
                        Toast.makeText(PostJobActivity.this, "Job posted successfully!!!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        dialog.dismiss();
                        Log.e("TAG", "Response Unsuccessful" + response.code() + response.message());
                    }
                }

                @Override
                public void onFailure(Call<JobPostResponse> call, Throwable t) {
                    Log.e("TAG", "Failed " + t.getMessage());
                    dialog.dismiss();
                }
            });


        });
    }
}