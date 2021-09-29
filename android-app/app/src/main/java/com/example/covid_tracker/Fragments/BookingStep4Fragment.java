package com.example.covid_tracker.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.covid_tracker.R;

public class BookingStep4Fragment extends Fragment {

    public RadioButton yes1, yes2, yes3, yes4, yes5, no1, no2, no3, no4, no5;
    public Boolean isOK = true;
    private int checked_yes = 0;
    private int checked_no = 0;


    public BookingStep4Fragment() {
        // required empty public constructor.
    }
    static BookingStep4Fragment instance;
    public static BookingStep4Fragment getInstance(){
        if(instance == null)
            instance = new BookingStep4Fragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookingstep4, container, false);
        yes1 = view.findViewById(R.id.button1_yes);
        yes2 = view.findViewById(R.id.button2_yes);
        yes3 = view.findViewById(R.id.button3_yes);
        yes4 = view.findViewById(R.id.button4_yes);
        yes5 = view.findViewById(R.id.button5_yes);

        no1 = view.findViewById(R.id.button1_no);
        no2 = view.findViewById(R.id.button2_no);
        no3 = view.findViewById(R.id.button3_no);
        no4 = view.findViewById(R.id.button4_no);
        no5 = view.findViewById(R.id.button5_no);

        yes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if_yes();
                checked_yes = checked_yes + 1;
            }
        });
        yes2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if_yes();
                checked_yes = checked_yes + 1;
            }
        });
        yes3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if_yes();
                checked_yes = checked_yes + 1;
            }
        });
        yes4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if_yes();
                checked_yes = checked_yes + 1;

            }
        });
        yes5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if_yes();
                checked_yes = checked_yes + 1;
            }
        });

        no1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checked_no = checked_no + 1;
            }
        });
        no2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checked_no = checked_no + 1;
            }
        });
        no3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checked_no = checked_no + 1;
            }
        });
        no4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checked_no = checked_no + 1;
            }
        });
        no5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checked_no = checked_no + 1;
            }
        });

        int total = checked_no + checked_yes;
        SharedPreferences.Editor edit= getActivity().getSharedPreferences("Booking", Context.MODE_PRIVATE).edit();
        edit.putInt("Checked_boxes", total);
        edit.commit();
        return view;
    }

    public void if_yes()
    {
        isOK = false;
        SharedPreferences.Editor edit= getActivity().getSharedPreferences("Booking", Context.MODE_PRIVATE).edit();
        edit.putBoolean("Health_info", false);
        edit.commit();
    }

    public void book_appointment() {

    }




}
