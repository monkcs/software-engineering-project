package com.example.covid_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Boka_vaccin2 extends AppCompatActivity {


    CalendarView calendarView;
    TextView mydate, outputvaccin, outputvaccin2;
    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    ArrayList<String> arraylist;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boka_vaccin2);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        mydate = (TextView) findViewById(R.id.mydate);
        outputvaccin = (TextView) findViewById(R.id.output_vaccin);
        outputvaccin2 = (TextView) findViewById(R.id.output_vaccin2);

        textInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout);
        autoCompleteTextView = (AutoCompleteTextView)  findViewById(R.id.autoCompleteTextView);


        //Detta får göras med en funktion och databas senare

        arraylist = new ArrayList<>();
        arraylist.add("Göteborg");
        arraylist.add("Uddevalla");
        arraylist.add("Karlstad");

        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arraylist);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setThreshold(1);



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                String date = i2 + "-" + (i1 + 1) + "-" + i;
                mydate.setText(date);

                outputvaccin.setText(date);
            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                outputvaccin2.setText(autoCompleteTextView.getText());

            }
        });

    }
}