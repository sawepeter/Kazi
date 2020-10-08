package com.example.ajira.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajira.R;
import com.example.ajira.model.Experience;

import java.util.List;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;
import xyz.sangcomz.stickytimelineview.TimeLineRecyclerView;

public class ProfileFragment extends Fragment implements VerticalStepperForm {

    private VerticalStepperFormLayout vertical_stepper_form;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile,container,false);

        TimeLineRecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL, false));


        String[] mySteps = {"Experience1", "Experience2", "Experience3"};
        int colorPrimary = ContextCompat.getColor(getActivity(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark);


        vertical_stepper_form = (VerticalStepperFormLayout) rootView.findViewById(R.id.vertical_stepper_form);

        VerticalStepperFormLayout.Builder.newInstance(vertical_stepper_form, mySteps, this, getActivity())
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(true)
                .init();

        return rootView;
    }

    @Override
    public View createStepContentView(int stepNumber) {
        View view = null;
        switch (stepNumber){
            case 0:
                view = createExperience1();
                break;
            case 1:
                view = createExperience2();
                break;
            case 2:
                view = createExperience3();
                break;
        }

        return view;
    }

    private View createExperience3() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        RelativeLayout level = (RelativeLayout) inflater.inflate(R.layout.layout_experience, null, false);
        return  level;
    }

    private View createExperience2() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        RelativeLayout level2 = (RelativeLayout) inflater.inflate(R.layout.layout_experience, null, false);
        return  level2;
    }

    private View createExperience1() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        RelativeLayout level1 = (RelativeLayout) inflater.inflate(R.layout.layout_experience, null, false);
        return  level1;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        switch (stepNumber){
            case 0:
                //set the stuff here
                Toast.makeText(getActivity(), "Case 0", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                break;
            case 2:
                vertical_stepper_form.setStepAsCompleted(2);
                break;
        }

    }

    @Override
    public void sendData() {

    }
}
