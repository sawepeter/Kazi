package com.example.ajira.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ajira.R;
import com.example.ajira.Utils.Utils;
import com.example.ajira.adapter.AllJobsAdapter;
import com.example.ajira.adapter.PendingAdapter;
import com.example.ajira.model.AllJobsResponse;
import com.example.ajira.model.JobUpdateResponse;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHomeFragment extends Fragment {

    RecyclerView rv_pending_jobs;
    PendingAdapter pendingAdapter;
    List<AllJobsResponse> jobsResponseList = null;
    ProgressDialog progressDialog;
    private ApiService apiService;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_home,container,false);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedpreferences.getString("token", "");

        Log.e("Admin DashBoard", "token" + token);

        progressDialog  = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Pending Data");
        progressDialog.show();
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        apiService = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);

        rv_pending_jobs = rootView.findViewById(R.id.rv_pending_jobs);

        Utils.runAsyncTask(this::getPendingJobs);

        return rootView;
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

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    rv_pending_jobs.setLayoutManager(layoutManager);

                    pendingAdapter = new PendingAdapter(jobsResponseList, getActivity(), AdminHomeFragment.this);
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
                    Toast.makeText(getActivity(), " "+response.body().getMsg(), Toast.LENGTH_SHORT).show();
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