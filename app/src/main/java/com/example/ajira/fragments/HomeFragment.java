package com.example.ajira.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajira.R;
import com.example.ajira.Utils.Utils;
import com.example.ajira.adapter.JobsAdapter;
import com.example.ajira.model.JobModelResponse;
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

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private ApiService apiService;
    JobsAdapter allJobsAdapter;
    RecyclerView rv_popular_jobs;
    List<JobModelResponse> jobsResponseList = null;
    List<JobUpdateResponse> jobsList = null;
    ProgressDialog progressDialog;
    TextView txt_welcome;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    String token,payment,status;
    public static final String TAG = HomeFragment.class.getSimpleName();
    Spinner spinner;
    String[] job_status = {"Available", "InProgress", "Delivered", "Completed"};;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedpreferences.getString("token", "");
        Log.e("Admin DashBoard", "token" + token);

        progressDialog  = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading jobs Data");
        progressDialog.show();
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        jobsResponseList = new ArrayList<>();
        jobsList = new ArrayList<>();

        apiService = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);

        rv_popular_jobs = rootView.findViewById(R.id.rv_popular_jobs);
        spinner = rootView.findViewById(R.id.spinner);
        txt_welcome = rootView.findViewById(R.id.txt_welcome);
        txt_welcome.setText("Hello " +sharedpreferences.getString("username", ""));


        spinner.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, job_status);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(aa);
        
        status = "Available";
        payment = "paid";

        Utils.runAsyncTask(this::getPaidJobs);

        return rootView;

    }

    //fetching all displayed jobs
    public void getPaidJobs() {
        Call<List<JobModelResponse>> call = apiService.getStatusJobs("Bearer " +token, payment,status);
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

                    allJobsAdapter = new JobsAdapter(jobsResponseList, getActivity(), HomeFragment.this);
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getActivity(), job_status[position], Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
