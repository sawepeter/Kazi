package com.example.ajira.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajira.R;
import com.example.ajira.Utils.Utils;
import com.example.ajira.activities.SearchActivity;
import com.example.ajira.adapter.NearbyJobsAdapter;
import com.example.ajira.adapter.AllJobsAdapter;
import com.example.ajira.model.AllJobsResponse;
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
    AllJobsAdapter allJobsAdapter;
    NearbyJobsAdapter nearbyJobsAdapter;
    RecyclerView rv_popular_jobs,rv_nearby;
    List<AllJobsResponse> jobsResponseList = null;
    List<JobsResponse> jobsList = null;
    LinearLayout l1;
    ProgressDialog progressDialog;
    TextView txt_welcome;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);

        progressDialog  = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading jobs Data");
        progressDialog.show();
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        jobsResponseList = new ArrayList<>();
        jobsList = new ArrayList<>();

        apiService = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);

        rv_popular_jobs = rootView.findViewById(R.id.rv_popular_jobs);
        txt_welcome = rootView.findViewById(R.id.txt_welcome);
        txt_welcome.setText("Hello " +sharedpreferences.getString("username", ""));
        rv_nearby = rootView.findViewById(R.id.rv_nearby);

        rv_popular_jobs.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rv_nearby.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        allJobsAdapter = new AllJobsAdapter(jobsResponseList, getActivity());
        //nearbyJobsAdapter = new NearbyJobsAdapter(jobsResponseList, getActivity());
        rv_popular_jobs.setAdapter(allJobsAdapter);
       // rv_nearby.setAdapter(nearbyJobsAdapter);

        Utils.runAsyncTask(this::getAllJobs);

        return rootView;

    }

    //fetching all displayed jobs
    public void getAllJobs() {
        Call<List<AllJobsResponse>> call = apiService.getAllJobs();
        call.enqueue(new Callback<List<AllJobsResponse>>() {
            @Override
            public void onResponse(Call<List<AllJobsResponse>> call, Response<List<AllJobsResponse>> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    Log.e("TAG", "Response successful" +response.code() + response.message());

                    jobsResponseList = response.body();
                    allJobsAdapter.setJobsResponseList(jobsResponseList);

                }else {
                    progressDialog.dismiss();
                    Log.e("TAG", "response unsuccessful" + response.code() + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<AllJobsResponse>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("TAG", "Failed " + t.getMessage());
            }
        });


    }

}
