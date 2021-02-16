package com.example.ajira.network;

import com.example.ajira.model.AllJobsResponse;
import com.example.ajira.model.ApplicationModel;
import com.example.ajira.model.JobApplicationResponse;
import com.example.ajira.model.JobModelResponse;
import com.example.ajira.model.JobPostResponse;
import com.example.ajira.model.JobUpdateResponse;
import com.example.ajira.model.RatingModel;
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
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("jobs")
    Call<List<JobUpdateResponse>> getJobPopular();

    @GET()
    Call<JobUpdateResponse> getNearbyJobs();

    @GET()
    Call<JobUpdateResponse> getFilteredJobs();

    @GET()
    Call<JobUpdateResponse> getSearchedJobs();

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

    @POST("/rating")
    @Headers("content-type: application/json")
    Call<JobUpdateResponse> submitRating(@Header("Authorization") String token, @Body RatingModel ratingModel);

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
    Call<List<JobModelResponse>> getMyJobs(@Header("Authorization") String token);

    @GET("/nearby-jobs")
    Call<List<JobPostResponse>> getNearbyJobs(@Query("location") String location);

    @GET("/jobs")
    Call<List<AllJobsResponse>> getAllJobs();

    @GET("/jobs-pending")
    Call<List<AllJobsResponse>> getPendingJobs(@Header("Authorization") String token);

    @GET("/paid-jobs")
    Call<List<JobModelResponse>> getPaidJobs(@Query("payment_status") String payment_status);

    @GET("/jobs-done")
    Call<List<AllJobsResponse>> getApprovedJobs(@Header("Authorization") String token);

    @PUT("my-jobs/status/{id}")
    Call<JobUpdateResponse> approveJob(@Header("Authorization") String token, @Path("id") long id);

    @PUT("my-jobs/payment/{id}")
    Call<JobUpdateResponse> adminApproveJob(@Header("Authorization") String token, @Path("id") long id);


}
