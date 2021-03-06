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
import com.example.ajira.adapter.MyJobsAdapter;
import com.example.ajira.model.JobModelResponse;
import com.example.ajira.model.JobPostResponse;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyJobsFragment extends Fragment {

    RecyclerView rv_my_jobs;
    ApiService apiService;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String token;
    private List<JobModelResponse> jobPostResponseList;
    MyJobsAdapter myJobsAdapter;
    private LinearLayoutManager linearLayoutManager;
    ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_jobs, container, false);

        apiService = RetrofitBuilder.getAjiraBackendInstance().create(ApiService.class);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedpreferences.getString("token", "");
        Log.e("TAG", "token" + token);

        //jobPostResponseList = new ArrayList<>();
        rv_my_jobs = rootView.findViewById(R.id.rv_my_jobs);


        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading Data ...");
        dialog.show();

        Utils.runAsyncTask(this::getMyJobs);

        return rootView;
    }

    private void getMyJobs() {
        Call<List<JobModelResponse>> call = apiService.getMyJobs("Bearer " + token);
        call.enqueue(new Callback<List<JobModelResponse>>() {
            @Override
            public void onResponse(Call<List<JobModelResponse>> call, Response<List<JobModelResponse>> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();

                    jobPostResponseList = response.body();
                    Log.e("TAG", "Response successful" + response.code() + response.message());

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    rv_my_jobs.setLayoutManager(layoutManager);

                    myJobsAdapter = new MyJobsAdapter(getActivity(),jobPostResponseList);
                    rv_my_jobs.setAdapter(myJobsAdapter);

                } else {
                    dialog.dismiss();
                    Log.e("TAG", "Response unsuccessful" + response.code() + response.message());
                }

            }
            @Override
            public void onFailure(Call<List<JobModelResponse>> call, Throwable t) {
                dialog.dismiss();
                Log.e("TAG", "Failed " + t.getMessage());
            }
        });
    }
}
