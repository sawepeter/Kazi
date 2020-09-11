package com.example.ajira.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajira.R;
import com.example.ajira.model.JobsResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PopularJobsAdapter extends RecyclerView.Adapter<PopularJobsAdapter.MyViewHolder>{

    List<JobsResponse> jobsResponseList = new ArrayList<>();
    private Context context;
    JobsResponse jobs;

    public PopularJobsAdapter(List<JobsResponse> jobsResponseList, Context context) {
        this.jobsResponseList = jobsResponseList;
        this.context = context;
    }

    public void setCartModelList(List<JobsResponse> jobsResponseList) {
        this.jobsResponseList = jobsResponseList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_item,parent,false);
        context = parent.getContext();
        return new PopularJobsAdapter.MyViewHolder(view);
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
        }
    }


    }
