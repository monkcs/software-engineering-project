package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class PlanAndShedVaccs extends AppCompatActivity {

    Spinner ageSpinner, dateSpinner;
    private Calendar calendar;
    private SimpleDateFormat sdf;
    private String strDate;
    private ArrayList<String> ageSpan, dateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_and_shed_vaccs);
        ageSpinner = (Spinner) findViewById(R.id.age_spinner);
        dateSpinner = (Spinner) findViewById(R.id.start_date_spinner);

        ageSpan = new ArrayList<>();
        ageSpan.add("Age");
        ageSpan.add("65+");
        ageSpan.add("55+");
        ageSpan.add("45+");
        ageSpan.add("18+");

        dateList = new ArrayList<>();
        dateList.add("Datum: yyuy-mm-dd");

        //skapar ett kalender object som formateras till en string
        /*calendar = Calendar.getInstance();
        Log.i("testa log 1", "Current time:" + calendar);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        strDate = sdf.format(calendar);

        Log.i("testa log 2", "Current date as a string :" + strDate);
        */



        /*Hur skall datumet laggas ut?
        * fran  start datum till all framtid
        * sedan skall 15 min tidsintervall laggas till frn sag kl 06:00 till 20:00*/


    }
}