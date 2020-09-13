package com.example.ajira.network;

import com.example.ajira.model.JobsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("jobs")
    Call<List<JobsResponse>> getJobPopular();

    @GET()
    Call<JobsResponse> getNearbyJobs();

    @GET()
    Call<JobsResponse> getFilteredJobs();

    @GET()
    Call<JobsResponse> getSearchedJobs();

    @GET()
    Call<JobsResponse> getMyApplications();

}
