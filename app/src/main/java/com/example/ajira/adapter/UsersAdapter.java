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

    private Context context;
    private List<WorkerProfile> workerProfileList;
    WorkerProfile workerProfile;

    public UsersAdapter(Context context, List<WorkerProfile> workerProfileList) {
        this.context = context;
        this.workerProfileList = workerProfileList;
    }

    public void setWorkerProfileList(List<WorkerProfile> workerProfileList) {
        this.workerProfileList = workerProfileList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile_item, null);
        context = parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.i("autolog ", "onBindViewHolder");
        holder.skill_name.setText(workerProfileList.get(position).getSkillName());
        //holder.rating_count.setText(workerProfileList.get(position).getUserId());
        holder.worker_age.setText(workerProfileList.get(position).getAge());
        holder.worker_location.setText(workerProfileList.get(position).getLocation());
        /*Picasso.get().load(workerProfile.getLogo()).placeholder(R.drawable.placeholder).fit().centerCrop().into(holder.logo_image);*/
      /*  holder.btn_hire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Under Development!!!", Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    @Override
    public int getItemCount() {
        Log.i("autolog", "getItemCount");
        return workerProfileList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

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
            worker_location = view.findViewById(R.id.worker_location);

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

