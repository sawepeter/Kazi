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
import com.example.ajira.activities.RateDialog;
import com.example.ajira.adapter.ApplicationAdapter;
import com.example.ajira.model.ApplicationModel;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EarningsFragment extends Fragment {

    private ApiService apiService;
    ApplicationAdapter applicationAdapter;
    RecyclerView rv_applications;
    List<ApplicationModel> applicationResponseList = null;
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_application,container,false);

        progressDialog  = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading My Applications");
        progressDialog.show();

        applicationResponseList = new ArrayList<>();

        apiService = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedpreferences.getString("token", "");
        Log.e("TAG", "token" + token);

        rv_applications = rootView.findViewById(R.id.rv_applications);
        rv_applications.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        applicationAdapter = new ApplicationAdapter(applicationResponseList, getActivity());
        rv_applications.setAdapter(applicationAdapter);

        Utils.runAsyncTask(this::getApplications);

        return rootView;
    }

    //fetching data for counter values
    public void getApplications() {
        Call<List<ApplicationModel>> call = apiService.getCompleteJobs("Bearer " +token);
        call.enqueue(new Callback<List<ApplicationModel>>() {
            @Override
            public void onResponse(Call<List<ApplicationModel>> call, Response<List<ApplicationModel>> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    Log.e("TAG", "Response successful" +response.code() + response.message());
                    applicationResponseList = response.body();
                    applicationAdapter.setApplicationResponseList(applicationResponseList);

                }else {
                    progressDialog.dismiss();
                    Log.e("TAG", "response unsuccessful" + response.code() + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ApplicationModel>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("TAG", "Failed " + t.getMessage());
            }
        });


    }
}