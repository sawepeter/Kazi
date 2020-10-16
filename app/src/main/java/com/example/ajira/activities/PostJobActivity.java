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
import com.example.ajira.model.JobPostRequest;
import com.example.ajira.model.JobPostResponse;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostJobActivity extends AppCompatActivity {

    ImageView image_back;
    ApiService apiService;
    Button btn_Post;
    EditText job_title, job_companyName, Job_description, Job_location, job_age;
    String jobTitle, companyName, JobDescription, JobLocation, jobAge, token;
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
        dialog.setMessage("Logging ...");
        dialog.show();

        image_back = findViewById(R.id.image_back);
        btn_Post = findViewById(R.id.btn_Post);
        job_title = findViewById(R.id.job_title);
        job_companyName = findViewById(R.id.job_companyName);
        Job_description = findViewById(R.id.Job_description);
        Job_location = findViewById(R.id.Job_location);
        job_age = findViewById(R.id.job_age);
        image_back.setOnClickListener(v -> {
            finish();
        });

        btn_Post.setOnClickListener(v -> {

            dialog.dismiss();

            jobTitle = job_title.getText().toString().trim();
            companyName = job_companyName.getText().toString().trim();
            JobDescription = Job_description.getText().toString().trim();
            JobLocation = Job_location.getText().toString().trim();
            jobAge = job_age.getText().toString().trim();

            JobPostResponse jobPostResponse = new JobPostResponse();
            jobPostResponse.setJobTitle(jobTitle);
            jobPostResponse.setCompanyName(companyName);
            jobPostResponse.setQualifications(JobDescription);
            jobPostResponse.setJobLocation(JobLocation);
            jobPostResponse.setSalary(jobAge);
            jobPostResponse.setCompanyInfo("casual labourers");
            jobPostResponse.setJobLevel("Junior");

            Log.e("TAG", "Request is: " + new Gson().toJson(jobPostResponse));

            apiService.createJobList("Bearer " + token, jobPostResponse).enqueue(new Callback<JobPostResponse>() {
                @Override
                public void onResponse(Call<JobPostResponse> call, Response<JobPostResponse> response) {
                    if (response.isSuccessful()) {
                        dialog.dismiss();
                        String jobTitle = response.body().getJobTitle();
                        Toast.makeText(PostJobActivity.this, "Job posted successfully!!!" + jobTitle, Toast.LENGTH_SHORT).show();
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