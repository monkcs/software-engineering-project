package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_tracker.Fragments.BookingStep3Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PlanAndShedVaccs extends AppCompatActivity  {
    private Spinner ageSpinner, timeSpinner;
    private Button uploadBtn;
    private Calendar nowCal, startCal,endCal;
    private int startHour, endHour, FUTURE_DAY = 1, FUTUTRE_MONTH= 1, ageLimit=-1;
    private ArrayList<Integer> timeSlothList;
    private Date tomorrowDate, startDate, endDate;
    private SimpleDateFormat sdf;
    //private String strDate;
    private ArrayList<String> ageList, strTimeList;
    private ArrayList<Date> timeList;
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_and_shed_vaccs);
        queue = Volley.newRequestQueue(this);

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
                String selectedAge = (String) adapterView.getItemAtPosition(i);
                int age = Integer.parseInt(selectedAge);
                setAgeLimit(age);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //---------------------------------------------------------

        timeSpinner = (Spinner) findViewById(R.id.start_date_spinner);
        nowCal = Calendar.getInstance();
        //setStartCal(startCal);
        nowCal.add(Calendar.DATE,1);
        tomorrowDate = nowCal.getTime();

        //-----------------------------------
        strTimeList = new ArrayList<>();
        setStrTimeList(tomorrowDate);
        ArrayAdapter<String> strTimeAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, strTimeList);
        //selected item will look like a spinner set from XML

        strTimeAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        timeSpinner.setAdapter(strTimeAdapter);

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String dateStr = (String) adapterView.getItemAtPosition(i);
                Calendar tempCal= Calendar.getInstance();
                Date date;

                try {
                    date = sdf.parse(dateStr);
                    tempCal.setTime(date);
                    setStartCal(tempCal);

                } catch (ParseException e) {
                    e.printStackTrace();
                }


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
        ageList.add("65");
        ageList.add("55");
        ageList.add("45");
        ageList.add("18");
        ageList.add("12");
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
    private void setAgeLimit(int age){ ageLimit = age;}
    private int getAgeLimit(){return ageLimit;}

    private void setStartCal(Calendar begin){
        startCal = (Calendar) begin.clone();
        setStartHour(7);setEndHour(19);
        startCal.add(Calendar.DATE, FUTURE_DAY);
        startCal.set(Calendar.HOUR, getStartHour());
        startCal.set(Calendar.MINUTE,0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND,0);
    }

    private Calendar getStartCal(){return startCal;}

    private void setEndCal(){
        endCal = Calendar.getInstance();
        setStartHour(7);setEndHour(19);
        endCal.add(Calendar.MONTH, FUTUTRE_MONTH);
        endCal.set(Calendar.HOUR, getStartHour());
        endCal.set(Calendar.MINUTE,0);
        endCal.set(Calendar.SECOND, 0);
        endCal.set(Calendar.MILLISECOND,0);

    }

    private Calendar getEndCal(){ return endCal;}

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
        int mins = getTimeSloth(1);//15min
        Date uploadDate;
        Calendar tempCal = (Calendar) getStartCal().clone();
        setEndCal();
        Date endDate = getEndCal().getTime();

        for (uploadDate = getStartCal().getTime();uploadDate.before(endDate); uploadDate = tempCal.getTime()){
            serverUpload(uploadDate, getAgeLimit());
            tempCal.add(Calendar.MINUTE, mins);
        }

    }

    private void serverUpload(Date time, int age){
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "provider/appointment/create.php",
                response -> {

                }, error -> {

        }
        ) {
            @Override
            public Map<String, String> getParams()  {

                Map<String, String> params = new HashMap<String, String>();
                params.put("datetime", time.toString());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
            }
        };

        queue.add(request);
    }



}