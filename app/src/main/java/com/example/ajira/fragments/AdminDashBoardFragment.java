package com.example.ajira.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajira.R;
import com.example.ajira.model.Experience;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;

import java.util.List;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.sangcomz.stickytimelineview.TimeLineRecyclerView;

public class AdminDashBoardFragment extends Fragment  {

   TextView unpaidNumber,paid_number,jobsNumber,emp_no,employer_no;
    private ApiService apiService;
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile,container,false);

        progressDialog  = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading stats");
      //  progressDialog.show();

        apiService = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedpreferences.getString("token", "");
        Log.e("TAG", "token" + token);

        employer_no = rootView.findViewById(R.id.employer_no);
        emp_no = rootView.findViewById(R.id.emp_no);
        jobsNumber = rootView.findViewById(R.id.jobsNumber);
        paid_number = rootView.findViewById(R.id.paid_number);
        unpaidNumber = rootView.findViewById(R.id.unpaidNumber);

        apiService.employeesNumber(token).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful()){
                    response.body().doubleValue();
                    //emp_no.setText(response.body().doubleValue());
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

            }
        });

       /* emp_no.setText((CharSequence) apiService.employeesNumber(token));
        employer_no.setText((CharSequence) apiService.employersNumber(token));
        jobsNumber.setText((CharSequence) apiService.JobsNumber(token));
        paid_number.setText((CharSequence) apiService.paidJobsNumber(token));
        unpaidNumber.setText((CharSequence) apiService.unpaidJobsNumber(token));*/

        return rootView;
    }

}
