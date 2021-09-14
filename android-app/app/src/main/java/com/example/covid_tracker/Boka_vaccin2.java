package com.example.covid_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Boka_vaccin2 extends AppCompatActivity implements View.OnClickListener {


    CalendarView calendarView;
    TextView mydate, outputvaccin, outputvaccin2, outputvaccin3;
    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;
    private Button vaccinet1;
    private Button vaccinet2;
    private Button bookAppoint;

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

        outputvaccin3 = (TextView) findViewById(R.id.output_vaccin3);

        vaccinet1 = (Button) findViewById(R.id.vaccinet1);
        vaccinet2 = (Button) findViewById(R.id.vaccinet2);
        bookAppoint = (Button) findViewById(R.id.bookAppoint);

        vaccinet1.setOnClickListener(this);
        vaccinet2.setOnClickListener(this);
        bookAppoint.setOnClickListener(this);

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

    public void openBokavaccin()
    {
        Intent intent = new Intent(this, Boka_vaccin.class);

        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.vaccinet1:
                outputvaccin3.setText("Dos 1");

                break;


            case R.id.vaccinet2:
                outputvaccin3.setText("Dos 2");

                break;

            case R.id.bookAppoint:
                System.out.println("Du har bokat: " + outputvaccin3.getText() + " i " + outputvaccin2.getText() + " den " +outputvaccin.getText());
                openBokavaccin();
                break;
        }
    }
}