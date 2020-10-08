package com.example.ajira.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajira.R;
import com.example.ajira.activities.JobDetailsActivity;
import com.example.ajira.model.JobsResponse;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NearbyJobsAdapter extends  RecyclerView.Adapter<NearbyJobsAdapter.MyViewHolder> {

    List<JobsResponse> jobsResponseList = new ArrayList<>();
    private Context context;
    JobsResponse jobs;

    public NearbyJobsAdapter(List<JobsResponse> jobsResponseList, Context context) {
        this.jobsResponseList = jobsResponseList;
        this.context = context;
    }

    public void setJobsResponseList(List<JobsResponse> jobsResponseList) {
        this.jobsResponseList = jobsResponseList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_item,parent,false);
        context = parent.getContext();
        return new NearbyJobsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        jobs = jobsResponseList.get(position);
        holder.jobName.setText(jobs.getJob());
        holder.companyName.setText(jobs.getCompany());
        holder.job_salary.setText(jobs.getSalary());
        holder.job_location.setText(jobs.getLocation());
        Picasso.get().load(jobs.getLogo()).placeholder(R.drawable.placeholder).fit().centerCrop().into(holder.logo_image);

    }

    @Override
    public int getItemCount() {
        return jobsResponseList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView companyName, jobName,job_salary,job_location;
        private ImageView logo_image;

        public MyViewHolder(@NonNull View view) {
            super(view);
            logo_image = view.findViewById(R.id.logo_image);
            companyName = view.findViewById(R.id.companyName);
            jobName = view.findViewById(R.id.jobName);
            job_salary = view.findViewById(R.id.job_salary);
            job_location = view.findViewById(R.id.job_location);
            view.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION){
                    JobsResponse clickedData = jobsResponseList.get(pos);
                    Intent intent = new Intent(v.getContext(), JobDetailsActivity.class);
                    intent.putExtra("pos", pos);
                    intent.putExtra("data", (Serializable) jobsResponseList);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                    Log.i("TAG", "item position" +clickedData.getJob());

                }
            });
        }
    }
}
