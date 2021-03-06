package com.example.ajira.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ajira.R;
import com.example.ajira.model.JobUpdateResponse;
import com.example.ajira.network.ApiService;
import com.example.ajira.network.RetrofitBuilder;
import com.freddygenicho.mpesa.stkpush.Mode;
import com.freddygenicho.mpesa.stkpush.api.response.STKPushResponse;
import com.freddygenicho.mpesa.stkpush.interfaces.STKListener;
import com.freddygenicho.mpesa.stkpush.interfaces.TokenListener;
import com.freddygenicho.mpesa.stkpush.model.Mpesa;
import com.freddygenicho.mpesa.stkpush.model.STKPush;
import com.freddygenicho.mpesa.stkpush.model.Token;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

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

public class MpesaPayDialog extends BottomSheetDialogFragment implements TokenListener {

    private Mpesa mpesa;
    String number,amount,token;
    int jobId;
    private SweetAlertDialog sweetAlertDialog;
    public static final String TAG = MpesaPayDialog.class.getSimpleName();
    private Button button_Pay;
    private EditText edt_phone_number;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    ApiService apiService;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_mpesa_pay, container, false);


        apiService = RetrofitBuilder.getAjiraBackendInstance().create(ApiService.class);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        token = sharedpreferences.getString("token", "");
        jobId = sharedpreferences.getInt("jobId",0 );

        edt_phone_number = rootView.findViewById(R.id.edt_phone_number);
        button_Pay = rootView.findViewById(R.id.button_Pay);

        button_Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMpesa(v);

                apiService.adminApproveJob(token,jobId).enqueue(new Callback<JobUpdateResponse>() {
                    @Override
                    public void onResponse(Call<JobUpdateResponse> call, Response<JobUpdateResponse> response) {
                        if (response.isSuccessful()) {

                            Log.e("TAG", "Status changed !!!" +response.body().getState() + response.body().getMsg());
                            Toast.makeText(getActivity(), " "+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("TAG", "response unsuccessful " + response.code() + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<JobUpdateResponse> call, Throwable t) {
                        Log.e("TAG", "Failed " + t.getMessage());
                    }
                });
            }
        });


        //create an instance of Mpesa
        mpesa = new Mpesa(CONSUMER_KEY, CONSUMER_SECRET, Mode.SANDBOX);

        sweetAlertDialog = new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Connecting to Safaricom");
        sweetAlertDialog.setContentText("Please wait...");
        sweetAlertDialog.setCancelable(false);

        return rootView;

    }


    public void startMpesa(View view){
        number = edt_phone_number.getText().toString();
        amount = "10";

        if (number.isEmpty()){
            Toast.makeText(getActivity(), "Phone Number is required", Toast.LENGTH_SHORT).show();
            return;
        } else {
            sweetAlertDialog.show();
            try {
                mpesa.getToken(this);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void onToken(Token token) {
        STKPush stkPush = new STKPush();
        stkPush.setBusinessShortCode(SHORT_CODE);
        stkPush.setPassword(STKPush.getPassword(SHORT_CODE, PASS_KEY, STKPush.getTimestamp()));
        stkPush.setTimestamp(STKPush.getTimestamp());
        stkPush.setTransactionType(CUSTOMER_PAY_BILL_ONLINE);
        stkPush.setAmount(amount);
        stkPush.setPartyA(STKPush.sanitizePhoneNumber(number));
        stkPush.setPartyB(PARTYB);
        stkPush.setPhoneNumber(STKPush.sanitizePhoneNumber(number));
        stkPush.setCallBackURL(CALLBACK_URL);
        stkPush.setAccountReference("Ajira Test");
        stkPush.setTransactionDesc("Activating Sucbscription");

        mpesa.startStkPush(token, stkPush, new STKListener() {
            @Override
            public void onResponse(STKPushResponse stkPushResponse) {
                Log.e(TAG, "onResponse: " + stkPushResponse.toJson(stkPushResponse));
                String message = "Please enter your pin to complete transaction";
                sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                sweetAlertDialog.setTitleText("Transaction started");
                sweetAlertDialog.setContentText(message);
                sweetAlertDialog.dismissWithAnimation();
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG, "stk onError" +throwable.getMessage());
                sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText("Error");
                sweetAlertDialog.setContentText(throwable.getMessage());
                sweetAlertDialog.dismissWithAnimation();
            }
        });
    }

    @Override
    public void OnError(Throwable throwable) {
        Log.e(TAG, "Mpesa Error: " + throwable.getMessage());
        sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setTitleText("Error");
        sweetAlertDialog.setContentText(throwable.getMessage());
        sweetAlertDialog.dismissWithAnimation();
    }
}