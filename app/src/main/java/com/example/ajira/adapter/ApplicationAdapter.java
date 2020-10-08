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
import com.example.ajira.model.ApplicationResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.MyViewHolder> {

    List<ApplicationResponse> applicationResponseList = new ArrayList<>();
    private Context context;
    ApplicationResponse applications;

    public ApplicationAdapter(List<ApplicationResponse> applicationResponseList, Context context) {
        this.applicationResponseList = applicationResponseList;
        this.context = context;
    }

    public void setApplicationResponseList(List<ApplicationResponse> applicationResponseList) {
        this.applicationResponseList = applicationResponseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.application_item,parent,false);
        context = parent.getContext();
        return new ApplicationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        applications = applicationResponseList.get(position);
        holder.jobName.setText(applications.getJob());
        holder.companyName.setText(applications.getCompany());
        holder.job_salary.setText(applications.getSalary());
        holder.job_status.setText(applications.getStatus());
        Picasso.get().load(applications.getLogo()).placeholder(R.drawable.placeholder).fit().centerCrop().into(holder.logoImage);

    }

    @Override
    public int getItemCount() {
        return applicationResponseList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView companyName, jobName,job_salary,job_status;
        private ImageView logoImage;

        public MyViewHolder(@NonNull View view) {
            super(view);
            logoImage = view.findViewById(R.id.logoImage);
            companyName = view.findViewById(R.id.companyName);
            jobName = view.findViewById(R.id.jobName);
            job_salary = view.findViewById(R.id.job_salary);
            job_status = view.findViewById(R.id.job_status);
            /*view.setOnClickListener(v -> {
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
            });*/
        }
    }
}
