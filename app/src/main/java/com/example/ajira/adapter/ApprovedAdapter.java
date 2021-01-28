package com.example.ajira.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajira.R;
import com.example.ajira.activities.JobDetailsActivity;
import com.example.ajira.fragments.StatisticsFragment;
import com.example.ajira.model.AllJobsResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApprovedAdapter extends RecyclerView.Adapter<ApprovedAdapter.MyViewHolder>{

    List<AllJobsResponse> jobsResponseList = new ArrayList<>();
    private Context context;
    private Fragment fragment;
    AllJobsResponse jobs;
    StatisticsFragment statisticsFragment;

    public ApprovedAdapter(List<AllJobsResponse> jobsResponseList, Context context, Fragment fragment) {
        Log.i("autolog", "PendingAdapter");
        this.jobsResponseList = jobsResponseList;
        this.context = context;
        this.fragment = fragment;
    }

    public void setJobsResponseList(List<AllJobsResponse> jobsResponseList) {
        this.jobsResponseList = jobsResponseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ApprovedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pending_job_item, parent, false);
        context = parent.getContext();
        return new ApprovedAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovedAdapter.MyViewHolder holder, int position) {
        statisticsFragment = (StatisticsFragment) fragment;
        jobs = jobsResponseList.get(position);
        holder.textViewJobTitle.setText(jobs.getJobTitle());
        holder.textViewPhone.setText(jobs.getEmployerPhone());
        holder.textViewLocation.setText(jobs.getJobLocation());
        holder.textViewPay.setText(jobs.getJobAmount());
        holder.job_pending.setChecked(true);
        holder.job_pending.setText("Approved");
        holder.job_pending.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        Log.i("autolog", "getItemCount" +jobsResponseList.size());
        return jobsResponseList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewJobTitle, textViewPhone, textViewLocation, textViewPay;
        private CheckBox job_pending;

        public MyViewHolder(@NonNull View view) {
            super(view);
            textViewJobTitle = view.findViewById(R.id.textViewJobTitle);
            textViewPhone = view.findViewById(R.id.textViewPhone);
            textViewLocation = view.findViewById(R.id.textViewLocation);
            textViewPay = view.findViewById(R.id.textViewPay);
            job_pending = view.findViewById(R.id.job_pending);
            //passes data to the next step
            view.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION){
                    AllJobsResponse clickedData = jobsResponseList.get(pos);
                    Intent intent = new Intent(v.getContext(), JobDetailsActivity.class);
                    intent.putExtra("pos", pos);
                    intent.putExtra("data", (Serializable) jobsResponseList);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                    Log.i("TAG", "item position" +clickedData.getJobTitle());

                }
            });
        }
    }
}
