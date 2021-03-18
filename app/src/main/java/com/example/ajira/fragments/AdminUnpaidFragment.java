package com.example.ajira.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajira.R;
import com.example.ajira.Utils.Utils;
import com.example.ajira.adapter.ApprovedAdapter;
import com.example.ajira.model.JobModelResponse;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminUnpaidFragment extends Fragment {

    RecyclerView rv_approved_jobs;
    ApprovedAdapter approvedAdapter;
    List<JobModelResponse> jobsResponseList = null;
    ProgressDialog progressDialog;
    private ApiService apiService;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_statistics,container,false);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedpreferences.getString("token", "");

        Log.e("Admin DashBoard", "token" + token);

        progressDialog  = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Approved Data");
        progressDialog.show();
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        apiService = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);

        rv_approved_jobs = rootView.findViewById(R.id.rv_approved_jobs);

        Utils.runAsyncTask(this::getPaidJobs);

        return rootView;
    }

    //fetching all displayed jobs
    public void getPaidJobs() {
        Call<List<JobModelResponse>> call = apiService.getPaidJobs("paid");
        call.enqueue(new Callback<List<JobModelResponse>>() {
            @Override
            public void onResponse(Call<List<JobModelResponse>> call, Response<List<JobModelResponse>> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    Log.e("TAG", "Response successful" +response.code() + response.message());

                    jobsResponseList = response.body();

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    rv_approved_jobs.setLayoutManager(layoutManager);

                    approvedAdapter = new ApprovedAdapter(jobsResponseList, getActivity(), AdminUnpaidFragment.this);
                    rv_approved_jobs.setAdapter(approvedAdapter);

                    approvedAdapter.setJobsResponseList(jobsResponseList);

                }else {
                    progressDialog.dismiss();
                    Log.e("TAG", "response unsuccessful " + response.code() + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<JobModelResponse>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("TAG", "Failed " + t.getMessage());
            }
        });


    }
}
