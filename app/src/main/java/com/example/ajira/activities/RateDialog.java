package com.example.ajira.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ajira.R;
import com.example.ajira.model.JobUpdateResponse;
import com.example.ajira.model.RatingModel;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;
import com.freddygenicho.mpesa.stkpush.Mode;
import com.freddygenicho.mpesa.stkpush.api.response.STKPushResponse;
import com.freddygenicho.mpesa.stkpush.interfaces.STKListener;
import com.freddygenicho.mpesa.stkpush.interfaces.TokenListener;
import com.freddygenicho.mpesa.stkpush.model.Mpesa;
import com.freddygenicho.mpesa.stkpush.model.STKPush;
import com.freddygenicho.mpesa.stkpush.model.Token;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.UnsupportedEncodingException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.ajira.Utils.Constant.CALLBACK_URL;
import static com.example.ajira.Utils.Constant.CONSUMER_KEY;
import static com.example.ajira.Utils.Constant.CONSUMER_SECRET;
import static com.example.ajira.Utils.Constant.CUSTOMER_PAY_BILL_ONLINE;
import static com.example.ajira.Utils.Constant.PARTYB;
import static com.example.ajira.Utils.Constant.PASS_KEY;
import static com.example.ajira.Utils.Constant.SHORT_CODE;

public class RateDialog extends BottomSheetDialogFragment {

    private SweetAlertDialog sweetAlertDialog;
    public static final String TAG = RateDialog.class.getSimpleName();
    private Button button_rate;
    private EditText edt_comment;
    private RatingBar rBar;
    private ApiService apiService;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String token;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rating_dialog, container, false);

        edt_comment = rootView.findViewById(R.id.edt_comment);
        button_rate = rootView.findViewById(R.id.button_rate);
        rBar = (RatingBar) rootView.findViewById(R.id.ratingBar1);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedpreferences.getString("token", "");

        Log.e("Rate Dialog", "token" + token);

        sweetAlertDialog = new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
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
                            sweetAlertDialog.dismissWithAnimation();
                            Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }else {
                            sweetAlertDialog.dismissWithAnimation();
                            Log.e("TAG", "Response Unsuccessful" + response.code() + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<JobUpdateResponse> call, Throwable t) {
                        Log.e("TAG", "Failed " + t.getMessage());
                        sweetAlertDialog.dismiss();
                    }
                });
            }
        });




        return rootView;

    }




}