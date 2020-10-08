package com.example.ajira.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ajira.R;
import com.example.ajira.model.JobsResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class JobDetailsActivity extends AppCompatActivity {

    List<JobsResponse> jobsResponseList = new ArrayList<>();
    String jobName,jobSalary,jobLocation,companyName;
    int position = 0;
    ImageView image_logo,image_share,image_back;
    TextView job_title,job_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        image_logo = findViewById(R.id.image_logo);
        image_share = findViewById(R.id.image_share);
        image_back = findViewById(R.id.image_back);
        job_title = findViewById(R.id.job_title);
        job_location = findViewById(R.id.job_location);

        jobsResponseList = (List<JobsResponse>) getIntent().getSerializableExtra("data");
        position = getIntent().getIntExtra("pos", 0);

        //getData()
        jobName = jobsResponseList.get(position).getJob();
        jobSalary = jobsResponseList.get(position).getSalary();
        jobLocation = jobsResponseList.get(position).getLocation();
        companyName = jobsResponseList.get(position).getCompany();

        image_back.setOnClickListener(v -> {
            finish();
        });

        image_share.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = jobName;
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, companyName);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        });

        job_title.setText(jobName);
        job_location.setText(jobLocation);
        Picasso.get().load(jobsResponseList.get(position).getLogo()).fit().centerCrop().placeholder(R.drawable.placeholder).into(image_logo);


    }
}