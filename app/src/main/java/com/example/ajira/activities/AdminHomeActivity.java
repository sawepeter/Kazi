package com.example.ajira.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ajira.R;
import com.example.ajira.Utils.Utils;
import com.example.ajira.adapter.PendingAdapter;
import com.example.ajira.model.AllJobsResponse;
import com.example.ajira.model.JobUpdateResponse;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHomeActivity extends AppCompatActivity {

    RecyclerView rv_pending_jobs;
    PendingAdapter pendingAdapter;
    List<AllJobsResponse> jobsResponseList = null;
    ProgressDialog progressDialog;
    private ApiService apiService;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedpreferences.getString("token", "");

        Log.e("Admin DashBoard", "token" + token);

        progressDialog  = new ProgressDialog(AdminHomeActivity.this);
        progressDialog.setMessage("Loading Pending Jobs");
        progressDialog.show();

        apiService = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);

       // jobsResponseList = new ArrayList<>();

        rv_pending_jobs = findViewById(R.id.rv_pending_jobs);
/*
        rv_pending_jobs.setLayoutManager(new LinearLayoutManager(AdminHome.this, RecyclerView.VERTICAL, false));
        pendingAdapter = new PendingAdapter(jobsResponseList, getApplicationContext());*/

      //  rv_pending_jobs.setAdapter(pendingAdapter);

        Utils.runAsyncTask(this::getPendingJobs);
    }

    //fetching all displayed jobs
    public void getPendingJobs() {
        Call<List<AllJobsResponse>> call = apiService.getPendingJobs("Bearer " +token);
        call.enqueue(new Callback<List<AllJobsResponse>>() {
            @Override
            public void onResponse(Call<List<AllJobsResponse>> call, Response<List<AllJobsResponse>> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    Log.e("TAG", "Response successful" +response.code() + response.message());

                    jobsResponseList = response.body();

                    LinearLayoutManager layoutManager = new LinearLayoutManager(AdminHomeActivity.this);
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    rv_pending_jobs.setLayoutManager(layoutManager);

                    pendingAdapter = new PendingAdapter(jobsResponseList, getApplicationContext(),AdminHomeActivity.this);
                    rv_pending_jobs.setAdapter(pendingAdapter);

                    pendingAdapter.setJobsResponseList(jobsResponseList);

                }else {
                    progressDialog.dismiss();
                    Log.e("TAG", "response unsuccessful " + response.code() + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<AllJobsResponse>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("TAG", "Failed " + t.getMessage());
            }
        });


    }

    //approving jobs to make the visible
    public void approveJob(long id){
        apiService.approveJob("Bearer " +token, id).enqueue(new Callback<JobUpdateResponse>() {
            @Override
            public void onResponse(Call<JobUpdateResponse> call, Response<JobUpdateResponse> response) {
                if (response.isSuccessful()){
                    Log.e("TAG", "Status changed !!!" +response.body().getState());
                    Toast.makeText(AdminHomeActivity.this, " "+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("TAG", "response unsuccessful " + response.code() + response.message());
                }
            }

            @Override
            public void onFailure(Call<JobUpdateResponse> call, Throwable t) {
                Log.e("TAG", "Failed " + t.getMessage());
            }
        });
    }
}