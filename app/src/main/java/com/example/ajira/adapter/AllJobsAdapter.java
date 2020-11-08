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
import com.example.ajira.model.AllJobsResponse;
import com.example.ajira.model.JobsResponse;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllJobsAdapter extends RecyclerView.Adapter<AllJobsAdapter.MyViewHolder> {

    List<AllJobsResponse> jobsResponseList = new ArrayList<>();
    private Context context;
    AllJobsResponse jobs;

    public AllJobsAdapter(List<AllJobsResponse> jobsResponseList, Context context) {
        Log.i("autolog", "PopularJobsAdapter");
        this.jobsResponseList = jobsResponseList;
        this.context = context;
    }

    public void setJobsResponseList(List<AllJobsResponse> jobsResponseList) {
        this.jobsResponseList = jobsResponseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_all_jobs, parent, false);
        context = parent.getContext();
        return new AllJobsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        jobs = jobsResponseList.get(position);
        holder.textViewJobTitle.setText(jobs.getJobTitle());
        holder.textViewPhone.setText(jobs.getEmployerPhone());
        holder.textViewLocation.setText(jobs.getJobLocation());
        holder.textViewPay.setText(jobs.getJobAmount());
    }

    @Override
    public int getItemCount() {
        Log.i("autolog", "getItemCount");
        return jobsResponseList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewJobTitle, textViewPhone, textViewLocation, textViewPay;

        public MyViewHolder(@NonNull View view) {
            super(view);
            textViewJobTitle = view.findViewById(R.id.textViewJobTitle);
            textViewPhone = view.findViewById(R.id.textViewPhone);
            textViewLocation = view.findViewById(R.id.textViewLocation);
            textViewPay = view.findViewById(R.id.textViewPay);
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