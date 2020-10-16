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
import com.example.ajira.activities.JobDetailsActivity;
import com.example.ajira.model.JobPostResponse;
import com.example.ajira.model.JobsResponse;
import com.example.ajira.model.WorkerProfile;
import com.example.ajira.model.WorkerRequest;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {

    List<WorkerProfile> workerProfileList = new ArrayList<>();
    private Context context;
    WorkerProfile workerProfile;

    public UsersAdapter(List<WorkerProfile> workerProfileList, Context context) {
        this.workerProfileList = workerProfileList;
        this.context = context;
    }

    public void setWorkerProfileList(List<WorkerProfile> workerProfileList) {
        this.workerProfileList = workerProfileList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workers_item, parent, false);
        context = parent.getContext();
        return new UsersAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        workerProfile = workerProfileList.get(position);
        holder.skill_name.setText(workerProfile.getSkillName());
        holder.rating_count.setText(workerProfile.getUserId());
        holder.worker_age.setText(workerProfile.getAge());
        holder.worker_location.setText(workerProfile.getLocation());
        /*Picasso.get().load(workerProfile.getLogo()).placeholder(R.drawable.placeholder).fit().centerCrop().into(holder.logo_image);*/
        holder.btn_hire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Under Development!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return workerProfileList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView skill_name, rating_count, worker_age, worker_location;
        private ImageView logo_image;
        private Button btn_hire;

        public MyViewHolder(@NonNull View view) {
            super(view);
            logo_image = view.findViewById(R.id.logo_image);
            skill_name = view.findViewById(R.id.skill_name);
            rating_count = view.findViewById(R.id.rating_count);
            worker_age = view.findViewById(R.id.worker_age);
            btn_hire = view.findViewById(R.id.btn_hire);

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

