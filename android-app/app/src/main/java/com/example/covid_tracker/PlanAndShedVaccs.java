package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PlanAndShedVaccs extends AppCompatActivity  {
    private Spinner ageSpinner, timeSpinner;
    private Button uploadBtn;
    private Calendar nowCal, startCal,endCal;
    private int startHour, endHour, FUTURE_DAY = 1, FUTUTRE_MONTH= 1;
    private ArrayList<Integer> timeSlothList;
    private Date todayDate, startDate, endDate;
    private SimpleDateFormat sdf;
    //private String strDate;
    private ArrayList<String> ageList, strTimeList;
    private ArrayList<Date> timeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_and_shed_vaccs);

        setTimeSlothList();// min 10,15,20,30
        uploadBtn = (Button) findViewById(R.id.upload_time_button);
        uploadBtn.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        uploadBookingTime();
                    }
                }));
        //-------------------------------------------------
        ageSpinner = (Spinner) findViewById(R.id.age_spinner);
        ageList = new ArrayList<>();

        setAgeList();
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        ageList); //selected item will look like a spinner set from XML
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(ageAdapter);
        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //---------------------------------------------------------

        timeSpinner = (Spinner) findViewById(R.id.start_date_spinner);
        nowCal = Calendar.getInstance();
        setStartCal();
        nowCal.add(Calendar.DATE,1);
        todayDate = nowCal.getTime();

        //---------------------------
        /*
        timeList = new ArrayList<>();

        setTimeList(todayDate);
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
        setStrTimeList(todayDate);
        ArrayAdapter<String> strTimeAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, strTimeList);
        //selected item will look like a spinner set from XML

        strTimeAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        timeSpinner.setAdapter(strTimeAdapter);

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String selectedAge = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //en liten toast?
            }
        });

        
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

    private void setStrTimeList(Date startTime) {
        String strTime;
        Date nextTime, endTime;
        Calendar tempCal;
        tempCal = (Calendar) startCal.clone();
        endTime = endCal.getTime();

        strTimeList.add("Start Date");
        sdf = new SimpleDateFormat("E, dd MMM yyyy");

        for (nextTime= startTime; nextTime.before(endTime) || nextTime.equals(endTime);
             nextTime = tempCal.getTime()){
            //define a method who fix the time to 15min tim sloth between a timeinteral
            //then define a method who upload the timesloth to the data base.
            //This is called after an the items are selected and button pressed.


            strTime = sdf.format(nextTime);
            strTimeList.add(strTime);
            tempCal.add(Calendar.DATE,1);
        }


    }

    private void setStartHour( int inHour){ startHour = inHour;}
    private int getStartHour(){return startHour;}
    private void setEndHour( int inHour){ endHour = inHour;}
    private int getEndHour(){return endHour;}

    private void setStartCal(){
        startCal = Calendar.getInstance();
        setStartHour(7);setEndHour(19);
        startCal.add(Calendar.DATE, FUTURE_DAY);
        startCal.set(Calendar.HOUR, getStartHour());
        startCal.set(Calendar.MINUTE,0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND,0);
    }
    private void setEndCal(){
        endCal = Calendar.getInstance();
        setStartHour(7);setEndHour(19);
        endCal.add(Calendar.MONTH, FUTUTRE_MONTH);
        endCal.set(Calendar.HOUR, getStartHour());
        endCal.set(Calendar.MINUTE,0);
        endCal.set(Calendar.SECOND, 0);
        endCal.set(Calendar.MILLISECOND,0);

    }

    private void setTimeSlothList(){
        timeSlothList.add(10);
        timeSlothList.add(15);
        timeSlothList.add(20);
        timeSlothList.add(30);
    }
    private int getTimeSloth(int pos){
        int timesloth;

        if(pos <=0 || pos < timeSlothList.size()){ timesloth = timeSlothList.get(pos);}
        else { Log.i("timesloth lenght", "wrong position value"); timesloth=0;}

        return timesloth;
    }

    private  void uploadBookingTime(){
        //code for uploading timesloths to database
        //should it return something?
    }



}