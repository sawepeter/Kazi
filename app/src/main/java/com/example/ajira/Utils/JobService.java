package com.example.ajira.Utils;

import android.app.Activity;

import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;

public class JobService {

    private final String TAG = this.getClass().getSimpleName();
    private ApiService apiService;
    private Activity activity;

    public JobService(Activity activity) {
        apiService = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);
        this.activity = activity;
    }

}
