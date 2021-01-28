package com.example.ajira.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.ListFormatter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajira.R;
import com.example.ajira.Utils.Utils;
import com.example.ajira.adapter.UsersAdapter;
import com.example.ajira.model.WorkerProfile;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecruiterHomeFragment extends Fragment {

    TextView txt_top;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String username,currentTime,token;
    RecyclerView rv_users;
    UsersAdapter usersAdapter;
    private List<WorkerProfile> workerProfileList = null;
    ProgressDialog progressDialog;
    ApiService apiService;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recruiter_home, container, false);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedpreferences.getString("token", "");

        Log.e("Admin DashBoard", "token" + token);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading jobs Data");
        progressDialog.show();

        /*workerProfileList = new ArrayList<>();*/

        apiService = RetrofitBuilder.getAjiraBackendInstance().create(ApiService.class);
        currentTime = DateFormat.getDateTimeInstance().format(new Date());

        txt_top = rootView.findViewById(R.id.txt_top);
        rv_users = rootView.findViewById(R.id.rv_users);


        txt_top.setText("Welcome " +sharedpreferences.getString("username", ""));


        Utils.runAsyncTask(this::getWorkerProfile);
        return rootView;
    }

    //fetching data for counter values
    public void getWorkerProfile() {
        Call<List<WorkerProfile>> call = apiService.getUserProfiles();
        call.enqueue(new Callback<List<WorkerProfile>>() {
            @Override
            public void onResponse(Call<List<WorkerProfile>> call, Response<List<WorkerProfile>> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();

                    workerProfileList = response.body();

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    rv_users.setLayoutManager(layoutManager);

                    usersAdapter = new UsersAdapter(getActivity(),workerProfileList);
                    rv_users.setAdapter(usersAdapter);

                    Log.e("TAG  successful", "Response successful " + workerProfileList.size() + response.code() + response.message());

                } else {
                    progressDialog.dismiss();
                    Log.e("TAG", "response unsuccessful" + response.code() + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<WorkerProfile>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("TAG", "Failed " + t.getMessage());
            }
        });


    }
}
