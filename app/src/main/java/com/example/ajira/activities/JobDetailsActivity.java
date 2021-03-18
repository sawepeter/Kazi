package com.example.ajira.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajira.R;
import com.example.ajira.model.AllJobsResponse;
import com.example.ajira.model.ApplicationModel;
import com.example.ajira.model.JobApplicationResponse;
import com.example.ajira.model.JobModelResponse;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailsActivity extends AppCompatActivity {

    List<JobModelResponse> jobsResponseList = new ArrayList<>();
    String jobTitle, jobSalary, jobLocation, EmployerName, Phone, deadline, type,token,currentTime;
    int position = 0, jobId;
    TextView job_title, job_location, textViewEmployerName, textViewJobType, textViewPhone, textViewJobPay, textViewJobDeadline;
    ImageView image_back;
    Button btn_apply;
    ApiService apiService;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    ProgressDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        apiService = RetrofitBuilder.getAjiraBackendInstance().create(ApiService.class);
        progressDialog = new ProgressDialog(JobDetailsActivity.this);
        progressDialog.setMessage("Applying job");

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedpreferences.getString("token", "");

        textViewEmployerName = findViewById(R.id.textViewEmployerName);
        textViewJobType = findViewById(R.id.textViewJobType);
        textViewPhone = findViewById(R.id.textViewPhone);
        job_title = findViewById(R.id.job_title);
        job_location = findViewById(R.id.job_location);
        textViewJobPay = findViewById(R.id.textViewJobPay);
        textViewJobDeadline = findViewById(R.id.textViewJobDeadline);
        image_back = findViewById(R.id.image_back);
        btn_apply = findViewById(R.id.btn_apply);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        currentTime = sdf.format(date);

        jobsResponseList = (List<JobModelResponse>) getIntent().getSerializableExtra("data");
        position = getIntent().getIntExtra("pos", 0);

        //getData()
        jobTitle = jobsResponseList.get(position).getJobTitle();
        jobSalary = jobsResponseList.get(position).getJobAmount();
        jobLocation = jobsResponseList.get(position).getJobLocation();
        EmployerName = jobsResponseList.get(position).getEmployerName();
        Phone = jobsResponseList.get(position).getEmployerPhone();
        deadline = jobsResponseList.get(position).getJobDeadline();
        type = jobsResponseList.get(position).getJobType();
        //this is the job id
        jobId = jobsResponseList.get(position).getId();

        image_back.setOnClickListener(v -> {
            finish();
        });

        job_title.setText(jobTitle);
        job_location.setText(jobLocation);
        textViewEmployerName.setText(EmployerName);
        textViewJobType.setText(type);
        textViewPhone.setText(Phone);
        textViewJobPay.setText(jobSalary);
        textViewJobDeadline.setText(deadline);

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                //load data into the model
                ApplicationModel applicationModel = new ApplicationModel();
                String id_job = Integer.toString(jobId);
                applicationModel.setJobId(id_job);

                applicationModel.setApplyDate(currentTime);

                apiService.applyJobs("Bearer " +token, applicationModel).enqueue(new Callback<JobApplicationResponse>() {
                    @Override
                    public void onResponse(Call<JobApplicationResponse> call, Response<JobApplicationResponse> response) {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();
                            Log.e("TAG", "Response successful" + response.body().getMsg());
                            Toast.makeText(JobDetailsActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                           // startActivity(new Intent(JobDetailsActivity.this,));

                        }else {
                            progressDialog.dismiss();
                            Log.e("TAG", "Response unsuccessful" + response.code() + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<JobApplicationResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.e("TAG", "Failed api call" + t.getMessage());
                    }
                });

            }
        });

    }

    public void printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
    }
}