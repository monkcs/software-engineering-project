package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationBarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PlanAndShedVaccs extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner ageSpinner, dateSpinner;
    private Calendar calendar;
    private SimpleDateFormat sdf;
    private String strDate;
    private ArrayList<String> ageList, dateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_and_shed_vaccs);
        ageSpinner = (Spinner) findViewById(R.id.age_spinner);
        dateSpinner = (Spinner) findViewById(R.id.start_date_spinner);

        ageList = new ArrayList<>();
        ageList.add("Age");
        ageList.add("65+");
        ageList.add("55+");
        ageList.add("45+");
        ageList.add("18+");

        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        ageList); //selected item will look like a spinner set from XML
        ageAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        ageSpinner.setAdapter(ageAdapter);
        ageSpinner.setOnItemSelectedListener(this);


        /*
        dateList = new ArrayList<>();
        dateList.add("Datum: yyuy-mm-dd");

        //skapar ett kalender object som formateras till en string
        calendar = Calendar.getInstance();
        Log.i("testa log 1", "Current time:" + calendar);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        strDate = sdf.format(calendar);

        Log.i("testa log 2", "Current date as a string :" + strDate);
        */



        /*Hur skall datumet laggas ut?
        * fran  start datum till all framtid
        * sedan skall 15 min tidsintervall laggas till frn sag kl 06:00 till 20:00*/


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    //what should go here?
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    //what should go here??
    }
}