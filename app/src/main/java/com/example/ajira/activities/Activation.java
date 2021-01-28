package com.example.ajira.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ajira.R;
import com.example.ajira.model.AllJobsResponse;
import com.example.ajira.model.JobPostResponse;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;
import com.freddygenicho.mpesa.stkpush.api.MpesaApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Activation extends AppCompatActivity {

    Button btn_apply;
    List<JobPostResponse> jobsResponseList = new ArrayList<>();
    String jobTitle, jobSalary, jobLocation, EmployerName, Phone, status, type,token,currentTime;
    int position = 0, jobId;
    TextView job_title, job_location, textViewEmployerName, textViewJobType, textViewPhone, textViewJobPay, textViewJobStatus;
    ImageView image_back;
    ApiService apiService;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);

        apiService = RetrofitBuilder.getAjiraBackendInstance().create(ApiService.class);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        token = sharedpreferences.getString("token", "");

        textViewEmployerName = findViewById(R.id.textViewEmployerName);
        textViewJobType = findViewById(R.id.textViewJobType);
        textViewPhone = findViewById(R.id.textViewPhone);
        job_title = findViewById(R.id.job_title);
        job_location = findViewById(R.id.job_location);
        textViewJobPay = findViewById(R.id.textViewJobPay);
        textViewJobStatus = findViewById(R.id.textViewJobStatus);
        image_back = findViewById(R.id.image_back);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        currentTime = sdf.format(date);

        jobsResponseList = (List<JobPostResponse>) getIntent().getSerializableExtra("data");
        position = getIntent().getIntExtra("pos", 0);

        //getData()
        jobTitle = jobsResponseList.get(position).getJobTitle();
        jobSalary = jobsResponseList.get(position).getJobAmount();
        jobLocation = jobsResponseList.get(position).getJobLocation();
        EmployerName = jobsResponseList.get(position).getEmployerName();
        Phone = jobsResponseList.get(position).getEmployerPhone();
        status = jobsResponseList.get(position).getStatus();
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
        textViewJobStatus.setText(status);

        btn_apply = findViewById(R.id.btn_apply);
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MpesaPayDialog mpesaPayDialog = new MpesaPayDialog();
                mpesaPayDialog.show(getSupportFragmentManager(), "PaymentSheet");
            }
        });
    }
}