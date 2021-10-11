package com.example.covid_tracker.User.Fragment.BookingStep;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.covid_tracker.R;

public class BookingStep4Fragment extends Fragment {


    public BookingStep4Fragment() {
        // required empty public constructor.
    }

    static BookingStep4Fragment instance;

    public static BookingStep4Fragment getInstance() {
        if (instance == null)
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
        SharedPreferences.Editor edit= getActivity().getSharedPreferences("Booking", Context.MODE_PRIVATE).edit();
        edit.putBoolean("Q1", false);
        edit.putBoolean("Q2", false);
        edit.putBoolean("Q3", false);
        edit.putBoolean("Q4", false);
        edit.putBoolean("Q5", false);
        edit.apply();
        RadioGroup radio1 = (RadioGroup) view.findViewById(R.id.radiogroup_1);
        radio1.clearCheck();
        radio1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(i);
                if (rb != null) {
                    if (i == R.id.button1_yes) {
                        edit.putBoolean("Health_info", true);
                        Toast.makeText(getActivity(), "Yes", Toast.LENGTH_LONG).show();
                    } else if (i == R.id.button1_no) {
                        edit.putBoolean("Health_info", false);
                        Toast.makeText(getActivity(), "No", Toast.LENGTH_LONG).show();
                    }
                    edit.putBoolean("Q1", true);
                    edit.apply();
                }
            }
        });
        RadioGroup radio2 = (RadioGroup) view.findViewById(R.id.radiogroup_2);
        radio2.clearCheck();
        radio2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(i);
                if (rb != null) {
                    if (i == R.id.button2_yes) {
                        edit.putBoolean("Health_info", true);
                        Toast.makeText(getActivity(), "Yes", Toast.LENGTH_LONG).show();
                    } else if (i == R.id.button2_no) {
                        edit.putBoolean("Health_info", false);
                        Toast.makeText(getActivity(), "No", Toast.LENGTH_LONG).show();
                    }
                    edit.putBoolean("Q2", true);
                    edit.apply();
                }
            }
        });
        RadioGroup radio3 = (RadioGroup) view.findViewById(R.id.radiogroup_3);
        radio3.clearCheck();
        radio3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(i);
                if (rb != null) {
                    if (i == R.id.button3_yes) {
                        edit.putBoolean("Health_info", true);
                        Toast.makeText(getActivity(), "Yes", Toast.LENGTH_LONG).show();
                    } else if (i == R.id.button3_no) {
                        edit.putBoolean("Health_info", false);
                        Toast.makeText(getActivity(), "No", Toast.LENGTH_LONG).show();
                    }
                    edit.putBoolean("Q3", true);
                    edit.apply();
                }
            }
        });
        RadioGroup radio4 = (RadioGroup) view.findViewById(R.id.radiogroup_4);
        radio4.clearCheck();
        radio4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(i);
                if (rb != null) {
                    if (i == R.id.button4_yes) {
                        edit.putBoolean("Q4", true);
                        edit.putBoolean("Health_info", true);
                        Toast.makeText(getActivity(), "Yes", Toast.LENGTH_LONG).show();
                    } else if (i == R.id.button4_no) {
                        edit.putBoolean("Health_info", false);
                        Toast.makeText(getActivity(), "No", Toast.LENGTH_LONG).show();
                    }
                    edit.putBoolean("Q4", true);
                    edit.apply();
                }
            }
        });
        RadioGroup radio5 = (RadioGroup) view.findViewById(R.id.radiogroup_5);
        radio5.clearCheck();
        radio5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(i);
                if (rb != null) {
                    if (i == R.id.button5_yes) {
                        edit.putBoolean("Health_info", true);
                        Toast.makeText(getActivity(), "Yes", Toast.LENGTH_LONG).show();
                    } else if (i == R.id.button5_no) {
                        edit.putBoolean("Health_info", false);
                        Toast.makeText(getActivity(), "No", Toast.LENGTH_LONG).show();
                    }
                    edit.putBoolean("Q5", true);
                    edit.apply();
                }
            }
        });

    return view;
    }

}