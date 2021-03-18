package com.example.ajira.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.ajira.R;
import com.example.ajira.model.JobUpdateResponse;
import com.example.ajira.model.RatingModel;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingActivity extends AppCompatActivity {

    private SweetAlertDialog sweetAlertDialog;
    public static final String TAG = RateDialog.class.getSimpleName();
    private Button button_rate;
    private EditText edt_comment;
    private RatingBar rBar;
    private ApiService apiService;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        edt_comment = findViewById(R.id.edt_comment);
        button_rate = findViewById(R.id.button_rate);
        rBar = (RatingBar) findViewById(R.id.ratingBar1);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedpreferences.getString("token", "");

        Log.e("Rate Dialog", "token" + token);

        sweetAlertDialog = new SweetAlertDialog(RatingActivity.this,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Posting Rating");
        sweetAlertDialog.setContentText("Please wait...");
        sweetAlertDialog.setCancelable(false);

        apiService = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);

        button_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int noofstars = rBar.getNumStars();
                float getrating = rBar.getRating();

                Double d = (double) noofstars;

                String review =  edt_comment.getText().toString();

                RatingModel ratingModel = new RatingModel();
                ratingModel.setComment(review);
                ratingModel.setRating(String.valueOf(d));

                sweetAlertDialog.show();

                apiService.submitRating("Bearer " +token, ratingModel).enqueue(new Callback<JobUpdateResponse>() {
                    @Override
                    public void onResponse(Call<JobUpdateResponse> call, Response<JobUpdateResponse> response) {
                        if (response.isSuccessful()){
                           // sweetAlertDialog.dismissWithAnimation();
                            Toast.makeText(RatingActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }else {
                           // sweetAlertDialog.dismissWithAnimation();
                            Log.e("TAG", "Response Unsuccessful" + response.code() + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<JobUpdateResponse> call, Throwable t) {
                        Log.e("TAG", "Failed " + t.getMessage());
                       // sweetAlertDialog.dismiss();
                    }
                });
            }
        });
    }
}