package com.example.ajira.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ajira.R;
import com.example.ajira.adapter.PopularJobsAdapter;
import com.example.ajira.model.JobsResponse;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

public class DashBoardActivity extends AppCompatActivity {

    LinearLayout l1;
    BottomNavigationView bottom_navigation;
    private ApiService apiService;
    PopularJobsAdapter popularJobsAdapter;
    RecyclerView rv_popular_jobs;
    LinearLayout linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        apiService = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);

        l1 = findViewById(R.id.l1);
        rv_popular_jobs = findViewById(R.id.rv_popular_jobs);
        bottom_navigation = findViewById(R.id.bottom_navigation);
      /*  linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(HORIZONTAL);
        rv_popular_jobs.setHasFixedSize(true);
        rv_popular_jobs.setLayoutManager(linearLayoutManager);*/
        bottom_navigation.setSelectedItemId(R.id.Home);
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, JobDetailsActivity.class));
            }
        });
    }


    //fetching data for counter values
    public void getPopularJobs() {
        apiService.getJobPopular().enqueue(new Callback<JobsResponse>() {
            @Override
            public void onResponse(Call<JobsResponse> call, Response<JobsResponse> response) {
                if (response.isSuccessful()){
                    List<JobsResponse> jobsResponses = (List<JobsResponse>) response.body();


                }else {
                    Log.e("TAG", "Response Failed !!! " +response.code() +response.message());
                }
            }

            @Override
            public void onFailure(Call<JobsResponse> call, Throwable t) {
                Log.e("TAG", "Error " + t.getMessage());
            }
        });

    }
}