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
import com.example.ajira.model.JobUpdateResponse;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NearbyJobsAdapter extends  RecyclerView.Adapter<NearbyJobsAdapter.MyViewHolder> {

    List<JobUpdateResponse> jobUpdateResponseList = new ArrayList<>();
    private Context context;
    JobUpdateResponse jobs;

    public NearbyJobsAdapter(List<JobUpdateResponse> jobUpdateResponseList, Context context) {
        this.jobUpdateResponseList = jobUpdateResponseList;
        this.context = context;
    }

    public void setJobUpdateResponseList(List<JobUpdateResponse> jobUpdateResponseList) {
        this.jobUpdateResponseList = jobUpdateResponseList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pending_job_item,parent,false);
        context = parent.getContext();
        return new NearbyJobsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        jobs = jobUpdateResponseList.get(position);
        holder.jobName.setText(jobs.getState());

    }

    @Override
    public int getItemCount() {
        return jobUpdateResponseList.size();
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
                    JobUpdateResponse clickedData = jobUpdateResponseList.get(pos);
                    Intent intent = new Intent(v.getContext(), JobDetailsActivity.class);
                    intent.putExtra("pos", pos);
                    intent.putExtra("data", (Serializable) jobUpdateResponseList);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                    Log.i("TAG", "item position" +clickedData.getMsg());

                }
            });
        }
    }
}
