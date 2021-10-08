package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PlanAndShedVaccs extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner ageSpinner, timeSpinner;
    private Calendar calendar, start,end;
    private Date today;
    private SimpleDateFormat sdf;
    //private String strDate;
    private ArrayList<String> ageList, strTimeList;
    private ArrayList<Date> timeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_and_shed_vaccs);

        //-------------------------------------------------
        ageSpinner = (Spinner) findViewById(R.id.age_spinner);
        ageList = new ArrayList<>();

        setAgeList();
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        ageList); //selected item will look like a spinner set from XML
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(ageAdapter);
        ageSpinner.setOnItemSelectedListener(this);

        //---------------------------------------------------------

        timeSpinner = (Spinner) findViewById(R.id.start_date_spinner);
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        today = calendar.getTime();

        //---------------------------
        /*
        timeList = new ArrayList<>();

        setTimeList(today);
        ArrayAdapter<Date> timeAdapter = new ArrayAdapter<Date>
                (this, android.R.layout.simple_spinner_item, timeList);
        //selected item will look like a spinner set from XML

        timeAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);
        timeSpinner.setOnItemSelectedListener(this);

         */

        //------------------------
        strTimeList = new ArrayList<>();
        setStrTimeList(today);
        ArrayAdapter<String> strTimeAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, strTimeList);
        //selected item will look like a spinner set from XML

        strTimeAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        timeSpinner.setAdapter(strTimeAdapter);
        timeSpinner.setOnItemSelectedListener(this);

        
        /*Hur skall datumet laggas ut?
        * fran  start datum till all framtid
        * sedan skall 15 min tidsintervall laggas till frn sag kl 06:00 till 20:00*/

    }


    public  void setAgeList(){
        ageList.add("Age");
        ageList.add("65+");
        ageList.add("55+");
        ageList.add("45+");
        ageList.add("18+");
        ageList.add("0+");
    }

    public void setTimeList(Date startDate){
        // int minutes, Calender startDate, Calender endDate
        //Produceras tidsintervaller mellan two datum,
        // skall ocksa timmar laggas in? -> lar dig Calender Classen forst..
        //aList.add("Date");
        timeList.add(startDate);
    }

    private void setStrTimeList(Date startDate) {
        String strDate;
        sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm");
        strDate = sdf.format(startDate);
        strTimeList.add(strDate);

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