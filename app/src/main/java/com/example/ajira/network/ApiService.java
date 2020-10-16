package com.example.ajira.network;

import com.example.ajira.model.ApplicationResponse;
import com.example.ajira.model.JobPostRequest;
import com.example.ajira.model.JobPostResponse;
import com.example.ajira.model.JobsResponse;
import com.example.ajira.model.User;
import com.example.ajira.model.WorkerProfile;
import com.example.ajira.model.WorkerRequest;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("jobs")
    Call<List<JobsResponse>> getJobPopular();

    @GET()
    Call<JobsResponse> getNearbyJobs();

    @GET()
    Call<JobsResponse> getFilteredJobs();

    @GET()
    Call<JobsResponse> getSearchedJobs();

    @GET("applications")
    Call<List<ApplicationResponse>> getMyApplications();

    @POST("users/register")
    @FormUrlEncoded
    Call<User> RegisterUser(@Field("username") String username,
                            @Field("password") String password,
                            @Field("userType") String userType);

    @POST("/authenticate")
    @Headers("content-type: application/json")
    Call<User> LoginUser(@Body HashMap<String, String> credentials);

    //api to add to do list
    @POST("/create/jobs")
    @Headers("content-type: application/json")
    Call<JobPostResponse> createJobList(@Header("Authorization") String token, @Body JobPostResponse jobPostResponse);

    @GET("/user/profiles")
    Call<List<WorkerProfile>> getUserProfiles();

    @POST("/workers/new")
    Call<WorkerProfile> createWorkerProfile(@Body WorkerRequest workerRequest);

    @GET("/my-jobs")
    Call<List<JobPostResponse>> getMyJobs(@Header("Authorization") String token);

    @GET("/nearby-jobs")
    Call<List<JobPostResponse>> getNearbyJobs(@Query("location") String location);

    @GET("/jobs")
    Call<List<JobPostResponse>> getAllJobs();


}
