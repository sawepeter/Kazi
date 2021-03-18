package com.example.ajira.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajira.R;
import com.example.ajira.activities.RatingActivity;
import com.example.ajira.model.ApplicationModel;

import java.util.ArrayList;
import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.MyViewHolder> {

    List<ApplicationModel> applicationResponseList = new ArrayList<>();
    private Context context;
    ApplicationModel applications;

    public ApplicationAdapter(List<ApplicationModel> applicationResponseList, Context context) {
        this.applicationResponseList = applicationResponseList;
        this.context = context;
    }

    public void setApplicationResponseList(List<ApplicationModel> applicationResponseList) {
        this.applicationResponseList = applicationResponseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.active_jobs, parent, false);
        context = parent.getContext();
        return new ApplicationAdapter.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        applications = applicationResponseList.get(position);
        holder.order_no.setText("Order No: " + applications.getJobId());
        holder.start_date.setText(applications.getApplyDate());
        holder.btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "Wip!!!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, RatingActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return applicationResponseList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView order_no, order_status, start_date, end_date;
        private Button btn_complete;

        public MyViewHolder(@NonNull View view) {
            super(view);
            order_no = view.findViewById(R.id.order_no);
            order_status = view.findViewById(R.id.order_status);
            start_date = view.findViewById(R.id.start_date);
            end_date = view.findViewById(R.id.end_date);
            btn_complete = view.findViewById(R.id.btn_complete);
        }
    }
}
