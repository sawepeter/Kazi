package com.example.ajira.network;

import com.example.ajira.model.AllJobsResponse;
import com.example.ajira.model.ApplicationModel;
import com.example.ajira.model.JobApplicationResponse;
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
import retrofit2.http.PUT;
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

    //api to add to do list
    @POST("/apply/jobs")
    @Headers("content-type: application/json")
    Call<JobApplicationResponse> applyJobs(@Header("Authorization") String token, @Body ApplicationModel applicationModel);

    @GET("/worker/profiles")
    Call<List<WorkerProfile>> getUserProfiles();

    @GET("/jobs/Active")
    Call<List<ApplicationModel>> getMyActiveJobs(@Header("Authorization") String token);

    @GET("/jobs/complete")
    Call<List<ApplicationModel>> getCompleteJobs(@Header("Authorization") String token);

    @PUT("my-jobs/status/{id}")
    Call<JobPostResponse> changeJobVisibility(@Header("Authorization") String token);

    @PUT("my-jobs/completed/{id}")
    Call<JobPostResponse> changeJobStatus(@Header("Authorization") String token);

    @POST("/workers/new")
    Call<WorkerProfile> createWorkerProfile(@Body WorkerRequest workerRequest);

    @GET("/my-jobs")
    Call<List<JobPostResponse>> getMyJobs(@Header("Authorization") String token);

    @GET("/nearby-jobs")
    Call<List<JobPostResponse>> getNearbyJobs(@Query("location") String location);

    @GET("/jobs")
    Call<List<AllJobsResponse>> getAllJobs();

    @GET("/jobs-pending")
    Call<List<AllJobsResponse>> getPendingJobs();

    @GET("/jobs-done")
    Call<List<AllJobsResponse>> getApprovedJobs();


}
