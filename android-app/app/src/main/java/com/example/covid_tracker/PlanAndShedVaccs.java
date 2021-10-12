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

public class PlanAndShedVaccs extends AppCompatActivity  /*implements AdapterView.OnItemSelectedListener, View.OnClickListener*/ {
    private Spinner ageSpinner, startDateSpinner, endDateSpinner, timeSlotSpinner;
    private Button uploadBtn, changeBookingBtn;
    private Calendar nowCal, startCal, endCal;
    private int startHour, endHour, FUTURE_DAY = 1, FUTUTRE_MONTH = 1, ageLimit = -1;
    private ArrayList<Integer> timeslotList;
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

        timeslotList = new ArrayList<>();
        setTimeslotList();// min 10,15,20,30

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
                try {
                    int age = Integer.parseInt(selectedAge);
                    Log.i("Chosen age", "Age limit: " + age);
                    setAgeLimit(age);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //---------------------------------------------------------

        startDateSpinner = (Spinner) findViewById(R.id.start_date_spinner);
        nowCal = Calendar.getInstance();

        setStartCal(nowCal);
        nowCal.add(Calendar.DATE, 1);
        tomorrowDate = nowCal.getTime();

        //-----------------------------------
        strTimeList = new ArrayList<>();
        setStrTimeList(tomorrowDate);
        ArrayAdapter<String> strTimeAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, strTimeList);
        //selected item will look like a spinner set from XML

        strTimeAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        startDateSpinner.setAdapter(strTimeAdapter);

        startDateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String dateStr = (String) adapterView.getItemAtPosition(i);
                Calendar tempCal = Calendar.getInstance();
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

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadBookingTime();
            }
        });

    }


    public void setAgeList() {
        ageList.add("Age");
        ageList.add("65");
        ageList.add("55");
        ageList.add("45");
        ageList.add("18");
        ageList.add("12");
    }

    public void setTimeList(Date startDate) {
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
        setEndCal();
        endTime = getEndCal().getTime();

        strTimeList.add("Start Date");
        sdf = new SimpleDateFormat("E, dd MMM yyyy");

        for (nextTime = startTime; nextTime.before(endTime) || nextTime.equals(endTime);
             nextTime = tempCal.getTime()) {
            //define a method who fix the time to 15min tim slot between a timeinteral
            //then define a method who upload the timeslot to the data base.
            //This is called after an the items are selected and button pressed.


            strTime = sdf.format(nextTime);
            strTimeList.add(strTime);
            tempCal.add(Calendar.DATE, 1);
        }


    }

    private int getStartHour() {
        return startHour;
    }

    private void setStartHour(int inHour) {
        startHour = inHour;
    }

    private int getEndHour() {
        return endHour;
    }

    private void setEndHour(int inHour) {
        endHour = inHour;
    }

    private int getAgeLimit() {
        return ageLimit;
    }

    private void setAgeLimit(int age) {
        ageLimit = age;
    }

    private Calendar getStartCal() {
        return startCal;
    }

    private void setStartCal(Calendar begin) {
        startCal = (Calendar) begin.clone();
        setStartHour(7);
        setEndHour(19);
        startCal.add(Calendar.DATE, FUTURE_DAY);
        startCal.set(Calendar.HOUR, getStartHour());
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
    }

    private void setEndCal() {
        endCal = Calendar.getInstance();
        setStartHour(7);
        setEndHour(19);
        endCal.add(Calendar.MONTH, FUTUTRE_MONTH);
        endCal.set(Calendar.HOUR, getStartHour());
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        endCal.set(Calendar.MILLISECOND, 0);

    }

    private Calendar getEndCal() {
        return endCal;
    }

    private void setTimeslotList() {
        timeslotList.add(10);
        timeslotList.add(15);
        timeslotList.add(20);
        timeslotList.add(30);
    }

    private int getTimeslot(int pos) {
        int timeslot;

        if (pos <= 0 || pos < timeslotList.size()) {
            timeslot = timeslotList.get(pos);
        } else {
            Log.i("timeslot lenght", "wrong position value");
            timeslot = 0;
        }

        return timeslot;
    }

    private void uploadBookingTime() {
        //code for uploading timeslots to database
        //should it return something?
        int mins = getTimeslot(1);//15min
        Date uploadDate;
        Calendar tempCal = (Calendar) getStartCal().clone();
        setEndCal();
        Date endDate = getEndCal().getTime();

        for (uploadDate = getStartCal().getTime(); uploadDate.before(endDate); uploadDate = tempCal.getTime()) {

            SimpleDateFormat servrSdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String strDateUpload = servrSdf.format(uploadDate);
            serverUpload(strDateUpload, getAgeLimit());
            tempCal.add(Calendar.MINUTE, mins);
        }

    }

    private void serverUpload(String time, int age) {
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "provider/appointment/create.php",
                response -> {
                    response.toString();
                }, error -> {
            error.toString();
        }
        ) {
            @Override
            public Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("datetime", time);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
            }
        };

        queue.add(request);
    }

    /*
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.upload_time_button:
                System.out.println("button1");
                uploadBookingTime();
                break;


           /* case R.id.button2:
                System.out.println("button2");
                break;

            case R.id.button3:
                System.out.println("button3");
                break;

        }

    }
*/
    /*
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    int test;

    switch (adapterView.getId())
    {
        case R.id.age_spinner:
        {
            tring selectedAge = (String) adapterView.getItemAtPosition(i);
            try {
                int age = Integer.parseInt(selectedAge);
                Log.i("Chosen age", "Age limit: " + age);
                setAgeLimit(age);
            }catch(NumberFormatException e){
                e.printStackTrace();
            }
        }

        case R.id.start_date_spinner:
        {
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

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        int test;
    }

     */
}