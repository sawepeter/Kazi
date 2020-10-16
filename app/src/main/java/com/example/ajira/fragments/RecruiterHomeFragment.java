package com.example.ajira.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.ajira.adapter.PopularJobsAdapter;
import com.example.ajira.adapter.UsersAdapter;
import com.example.ajira.model.JobsResponse;
import com.example.ajira.model.WorkerProfile;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

public class RecruiterHomeFragment extends Fragment {

    TextView txt_welcome;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String username;
    ImageView image_back;
    RecyclerView rv_users;
    UsersAdapter usersAdapter;
    List<WorkerProfile> workerProfileList = null;
    ProgressDialog progressDialog;
    ApiService apiService;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recruiter_home,container,false);

        progressDialog  = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading jobs Data");
        progressDialog.show();

        workerProfileList = new ArrayList<>();

        apiService = RetrofitBuilder.getAjiraBackendInstance().create(ApiService.class);

        txt_welcome = rootView.findViewById(R.id.txt_welcome);
        rv_users = rootView.findViewById(R.id.rv_users);
        image_back = rootView.findViewById(R.id.image_back);

        rv_users.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        username = sharedpreferences.getString("userName", "");
        Log.e("TAG", "userName" + username);

        image_back.setOnClickListener(v -> {
            getActivity().finish();
        });

        txt_welcome.setText("Hello " +username);

        usersAdapter = new UsersAdapter(workerProfileList, getActivity());

        Utils.runAsyncTask(this::getWorkerProfile);
        return rootView;
    }

    //fetching data for counter values
    public void getWorkerProfile() {
        Call<List<WorkerProfile>> call = apiService.getUserProfiles();
        call.enqueue(new Callback<List<WorkerProfile>>() {
            @Override
            public void onResponse(Call<List<WorkerProfile>> call, Response<List<WorkerProfile>> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    Log.e("TAG", "Response successful" +response.code() + response.message());

                    workerProfileList = response.body();
                    usersAdapter.setWorkerProfileList(workerProfileList);

                }else {
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
