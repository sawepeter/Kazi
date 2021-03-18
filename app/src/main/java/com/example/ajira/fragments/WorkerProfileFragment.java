package com.example.ajira.fragments;

import android.os.Bundle;
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_worker_profile, container, false);

        txt_earnings = rootView.findViewById(R.id.txt_earnings);
        return rootView;
    }
}
