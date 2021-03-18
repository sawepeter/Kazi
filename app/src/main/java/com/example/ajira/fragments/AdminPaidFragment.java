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
import com.example.ajira.adapter.PendingAdapter;
import com.example.ajira.model.JobModelResponse;
import com.example.ajira.model.JobUpdateResponse;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPaidFragment extends Fragment {

    RecyclerView rv_pending_jobs;
    PendingAdapter pendingAdapter;
    List<JobModelResponse> jobsResponseList = null;
    ProgressDialog progressDialog;
    private ApiService apiService;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String token,paymentStatus,JobStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_home,container,false);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedpreferences.getString("token", "");

        paymentStatus = "unpaid";
        JobStatus = "Available";

        Log.e("Admin DashBoard", "token" + token);

        progressDialog  = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Pending Data");
        progressDialog.show();
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        apiService = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);

        rv_pending_jobs = rootView.findViewById(R.id.rv_pending_jobs);

        Utils.runAsyncTask(this::getUnPaidJobs);

        return rootView;
    }


    //fetching all displayed jobs
    public void getUnPaidJobs() {
        Call<List<JobModelResponse>> call = apiService.getStatusJobs("Bearer " +token,paymentStatus,JobStatus);
        call.enqueue(new Callback<List<JobModelResponse>>() {
            @Override
            public void onResponse(Call<List<JobModelResponse>> call, Response<List<JobModelResponse>> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    Log.e("TAG", "Response successful" +response.code() + response.message());

                    jobsResponseList = response.body();

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    rv_pending_jobs.setLayoutManager(layoutManager);

                    pendingAdapter = new PendingAdapter(jobsResponseList, getActivity(), AdminPaidFragment.this);
                    rv_pending_jobs.setAdapter(pendingAdapter);

                    pendingAdapter.setJobsResponseList(jobsResponseList);

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

    //approving jobs to make the visible
    public void approveJob(long id){
        apiService.adminApproveJob("Bearer " +token, id).enqueue(new Callback<JobUpdateResponse>() {
            @Override
            public void onResponse(Call<JobUpdateResponse> call, Response<JobUpdateResponse> response) {
                if (response.isSuccessful()){
                    Log.e("TAG", "Status changed !!!" +response.body().getState() + response.body().getMsg());
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