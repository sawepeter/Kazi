package com.example.ajira.adapter;

import android.content.Context;
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
import com.example.ajira.model.JobPostResponse;
import com.example.ajira.model.WorkerProfile;

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
        View view = LayoutInflater.from(context).inflate(R.layout.kazi_item, null);
        context = parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        jobPostResponse = jobPostResponseList.get(position);
        holder.job_title.setText(jobPostResponse.getJobTitle());
        holder.job_description.setText(jobPostResponse.getQualifications());
        holder.job_salary.setText(jobPostResponse.getSalary());
        holder.job_location.setText(jobPostResponse.getJobLocation());

    }

    @Override
    public int getItemCount() {
        return jobPostResponseList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView job_title, job_description, job_salary, job_location,number_of_applicants;

        public MyViewHolder(@NonNull View view) {
            super(view);
            job_title = view.findViewById(R.id.job_title);
            job_description = view.findViewById(R.id.job_description);
            job_location = view.findViewById(R.id.job_location);
            job_salary = view.findViewById(R.id.job_salary);
            number_of_applicants = view.findViewById(R.id.number_of_applicants);

          /*  view.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION){
                    WorkerProfile clickedData = workerProfileList.get(pos);
                    Intent intent = new Intent(v.getContext(), JobDetailsActivity.class);
                    intent.putExtra("pos", pos);
                    intent.putExtra("data", (Serializable) workerProfileList);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                    Log.i("TAG", "item position" +clickedData.getSkillName());

                }
            });*/
        }
    }
}

