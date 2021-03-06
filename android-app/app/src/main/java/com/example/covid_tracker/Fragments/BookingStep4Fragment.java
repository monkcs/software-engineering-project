package com.example.covid_tracker.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.covid_tracker.Login;
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
        setHasOptionsMenu(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.item2_menu2:
                System.out.println("hejsan du loggades");
                loginScreen();
                return true;


            default:
                return super.onOptionsItemSelected(item);


        }



    }

    private void loginScreen() {

        Intent intent = new Intent(getActivity(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

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
        edit.putBoolean("Question1", false);
        edit.putBoolean("Question2", false);
        edit.putBoolean("Question3", false);
        edit.putBoolean("Question4", false);
        edit.putBoolean("Question5", false);

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
                        edit.putBoolean("Question1", true);

                    } else if (i == R.id.button1_no) {
                        edit.putBoolean("Question1", false);
                        edit.putBoolean("Health_info", false);
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
                        edit.putBoolean("Question2", true);

                    } else if (i == R.id.button2_no) {
                        edit.putBoolean("Question2", false);
                        edit.putBoolean("Health_info", false);
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
                        edit.putBoolean("Question3", true);
                    } else if (i == R.id.button3_no) {
                        edit.putBoolean("Question3", false);
                        edit.putBoolean("Health_info", false);
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
                        edit.putBoolean("Question4", true);
                        edit.putBoolean("Health_info", true);
                    } else if (i == R.id.button4_no) {
                        edit.putBoolean("Question4", false);
                        edit.putBoolean("Health_info", false);
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
                        edit.putBoolean("Question5", true);

                    } else if (i == R.id.button5_no) {
                        edit.putBoolean("Question5", false);
                        edit.putBoolean("Health_info", false);
                    }
                    edit.putBoolean("Q5", true);
                    edit.apply();
                }
            }
        });

    return view;
    }

}