package com.example.ajira.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ajira.R;

public class WorkerProfileFragment extends Fragment {

    TextView txt_earnings,text_user_name;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    String token;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_worker_profile, container, false);

        Log.e("Admin DashBoard", "token" + token);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        txt_earnings = rootView.findViewById(R.id.txt_earnings);
        text_user_name = rootView.findViewById(R.id.text_user_name);

        text_user_name.setText(sharedpreferences.getString("username", ""));

        return rootView;
    }
}
