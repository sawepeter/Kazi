package com.example.ajira.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ajira.R;
import com.example.ajira.activities.RatingActivity;
import com.example.ajira.model.JobModelResponse;
import java.util.ArrayList;
import java.util.List;

public class DeliveredJobsAdapter extends RecyclerView.Adapter<DeliveredJobsAdapter.MyViewHolder> {

    List<JobModelResponse> jobsResponseList = new ArrayList<>();
    private Context context;
    JobModelResponse jobs;
    private Fragment fragment;
    SharedPreferences.Editor editor;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";


    public DeliveredJobsAdapter(List<JobModelResponse> jobsResponseList, Context context, Fragment fragment) {
        Log.i("autolog", "PopularJobsAdapter");
        this.jobsResponseList = jobsResponseList;
        this.context = context;
        this.fragment = fragment;
    }

    public void setJobsResponseList(List<JobModelResponse> jobsResponseList) {
        this.jobsResponseList = jobsResponseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_jobs, parent, false);
        context = parent.getContext();
        sharedpreferences = view.getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        return new DeliveredJobsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        jobs = jobsResponseList.get(position);
        holder.textViewJobTitle.setText(jobs.getJobTitle());
        holder.textViewPhone.setText(jobs.getEmployerPhone());
        holder.textViewLocation.setText(jobs.getJobLocation());
        holder.textViewPay.setText(jobs.getJobAmount());
        holder.button_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RatingActivity.class);
                editor.putString("job_id", String.valueOf(jobs.getId()));
                editor.putString("amount", jobs.getJobAmount());
                editor.apply();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i("autolog", "getItemCount");
        return jobsResponseList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewJobTitle, textViewPhone, textViewLocation, textViewPay;
        private Button button_rate;

        public MyViewHolder(@NonNull View view) {
            super(view);
            textViewJobTitle = view.findViewById(R.id.textViewJobTitle);
            textViewPhone = view.findViewById(R.id.textViewPhone);
            textViewLocation = view.findViewById(R.id.textViewLocation);
            textViewPay = view.findViewById(R.id.textViewPay);
            button_rate = view.findViewById(R.id.button_rate);
            //passes data to the next step
           /* view.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION){
                    JobModelResponse clickedData = jobsResponseList.get(pos);
                    Intent intent = new Intent(v.getContext(), Activation.class);
                    intent.putExtra("pos", pos);
                    intent.putExtra("data", (Serializable) jobsResponseList);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                    Log.i("TAG", "item position" +clickedData.getJobTitle());

                }
            });*/
        }
    }


}
