package com.example.ajira.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajira.R;
import com.example.ajira.Utils.Utils;
import com.example.ajira.activities.MpesaPayDialog;
import com.example.ajira.adapter.AllJobsAdapter;
import com.example.ajira.model.AllJobsResponse;
import com.example.ajira.model.JobModelResponse;
import com.example.ajira.model.JobPostResponse;
import com.example.ajira.model.JobUpdateResponse;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private ApiService apiService;
    AllJobsAdapter allJobsAdapter;
    RecyclerView rv_popular_jobs;
    List<JobModelResponse> jobsResponseList = null;
    List<JobUpdateResponse> jobsList = null;
    LinearLayout l1;
    ProgressDialog progressDialog;
    TextView txt_welcome;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    String token;
    public static final String TAG = HomeFragment.class.getSimpleName();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);


        Log.e("Admin DashBoard", "token" + token);

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
                    rv_popular_jobs.setLayoutManager(layoutManager);

                    allJobsAdapter = new AllJobsAdapter(jobsResponseList, getActivity(), HomeFragment.this);
                    rv_popular_jobs.setAdapter(allJobsAdapter);

                    allJobsAdapter.setJobsResponseList(jobsResponseList);


                }else {
                    progressDialog.dismiss();
                    try {

                        String errorBody = response.errorBody().string();

                        JSONObject jsonObject = new JSONObject(errorBody.trim());

                        jsonObject = jsonObject.getJSONObject("error");

                        jsonObject = jsonObject.getJSONObject("message");

                        Iterator<String> keys = jsonObject.keys();
                        String errors = "";
                        while (keys.hasNext()){
                            String key = keys.next();
                            JSONArray arr = jsonObject.getJSONArray(key);
                            for (int i = 0; i <  arr.length(); i++){
                                errors += key + " : " +arr.getString(i) + "\n";
                            }
                        }
                    } catch (JSONException | IOException e){
                        e.printStackTrace();
                    }
                    Log.e(TAG, "response unsuccessful" + response.code() + response.message());
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
