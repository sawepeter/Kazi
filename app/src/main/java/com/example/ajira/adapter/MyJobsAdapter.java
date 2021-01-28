package com.example.ajira.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajira.R;
import com.example.ajira.activities.Activation;
import com.example.ajira.model.JobPostResponse;
import com.example.ajira.model.WorkerProfile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyJobsAdapter extends RecyclerView.Adapter<MyJobsAdapter.MyViewHolder> {

    private Context context;
    private List<JobPostResponse> jobPostResponseList;
    JobPostResponse jobPostResponse;

    public MyJobsAdapter(Context context, List<JobPostResponse> jobPostResponseList) {
        this.context = context;
        this.jobPostResponseList = jobPostResponseList;
    }

    public void setJobPostResponseList(List<JobPostResponse> jobPostResponseList) {
        this.jobPostResponseList = jobPostResponseList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kazi_item, parent, false);
        context = parent.getContext();
        return new MyJobsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        jobPostResponse = jobPostResponseList.get(position);
        holder.textViewJobTitle.setText(jobPostResponse.getJobTitle());
        holder.textViewPhone.setText(jobPostResponse.getEmployerPhone());
        holder.textViewPay.setText(jobPostResponse.getJobAmount());
        holder.textViewLocation.setText(jobPostResponse.getJobLocation());
    }

    @Override
    public int getItemCount() {
        return jobPostResponseList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewJobTitle, textViewPhone, textViewPay, textViewLocation;

        public MyViewHolder(@NonNull View view) {
            super(view);
            textViewJobTitle = view.findViewById(R.id.textViewJobTitle);
            textViewPhone = view.findViewById(R.id.textViewPhone);
            textViewPay = view.findViewById(R.id.textViewPay);
            textViewLocation = view.findViewById(R.id.textViewLocation);

            view.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION){
                    JobPostResponse clickedData = jobPostResponseList.get(pos);
                    Intent intent = new Intent(v.getContext(), Activation.class);
                    intent.putExtra("pos", pos);
                    intent.putExtra("data", (Serializable) jobPostResponseList);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                    Log.i("TAG", "item position" +clickedData.getJobTitle());

                }
            });
        }
    }
}

