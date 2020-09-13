package com.example.ajira.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajira.R;
import com.example.ajira.Utils.Utils;
import com.example.ajira.activities.DashBoardActivity;
import com.example.ajira.adapter.PopularJobsAdapter;
import com.example.ajira.model.JobsResponse;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

public class HomeFragment extends Fragment {

    private ApiService apiService;
    PopularJobsAdapter popularJobsAdapter;
    RecyclerView rv_popular_jobs;
    List<JobsResponse> jobsResponseList = null;
    LinearLayout l1;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);

        progressDialog  = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading jobs Data");
        progressDialog.show();

        jobsResponseList = new ArrayList<>();

        apiService = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);

        l1 = rootView.findViewById(R.id.l1);
        rv_popular_jobs = rootView.findViewById(R.id.rv_popular_jobs);
        rv_popular_jobs.setLayoutManager(new LinearLayoutManager(getActivity(), HORIZONTAL, false));

        popularJobsAdapter = new PopularJobsAdapter(jobsResponseList, getActivity());
        rv_popular_jobs.setAdapter(popularJobsAdapter);

        Utils.runAsyncTask(this::getPopularJobs);

        return rootView;


    }

    //fetching data for counter values
    public void getPopularJobs() {
        Call<List<JobsResponse>> call = apiService.getJobPopular();
        call.enqueue(new Callback<List<JobsResponse>>() {
            @Override
            public void onResponse(Call<List<JobsResponse>> call, Response<List<JobsResponse>> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    Log.e("TAG", "Response successful" +response.code() + response.message());

                    jobsResponseList = response.body();
                    popularJobsAdapter.setCartModelList(jobsResponseList);

                }else {
                    progressDialog.dismiss();
                    Log.e("TAG", "response unsuccessful" + response.code() + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<JobsResponse>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("TAG", "Failed " + t.getMessage());
            }
        });


    }
}
