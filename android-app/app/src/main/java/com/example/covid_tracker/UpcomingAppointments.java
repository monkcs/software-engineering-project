package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_tracker.Fragments.BookingStep1Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UpcomingAppointments extends AppCompatActivity {

    private Context context;
    private RequestQueue queue;

    CalendarView calView_uppcAppoint;
    RecyclerView recyclerView_uppc_appoint;

    String currDate;

    List<UpcommingAppointmentsBlock> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        queue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_uppcomming_apointments);

        calView_uppcAppoint = findViewById(R.id.calView_uppcAppoint);
        recyclerView_uppc_appoint = findViewById(R.id.recyclerView_uppc_appoint);

        currDate = getDate();

        getBookedTimes2();

        initTimeslots();
        setRecyclerView(currDate);

        calView_uppcAppoint.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                currDate = String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth);
                Log.i("UCA", "Current date: " + currDate);
            }
        });

        /*Idea:
        * 1. admin selects a date from calendar (todays appointments shown as default)
        * 2. recyclerView updates with every timestamp (hour:min) that has at least 1 booked time
        * 3. time clicked, recyclerView brings up all the names (& pers nr??) of the persons booked that time
        * 4. persons name clicked, brings user to new activity personilized for that person (person_admin)
        * 5. in person_admin, ability to check 1st dose or 2nd dose as complete and/or book a new appointment */

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar
    }

    private void getBookedTimes(){
        StringRequest request = new StringRequest(Request.Method.GET, WebRequest.urlbase + "provider/today_appointments.php",
                response -> {
                    Log.i("UCA", "Json request: " + response.toString());
                    Toast.makeText(this, "Got response", Toast.LENGTH_LONG).show();

                }, error -> {
            Toast.makeText(this, "Error, no response here", Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.username, WebRequest.password);
            }
        };
        queue.add(request);
    }

    private void getBookedTimes2(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, WebRequest.urlbase + "provider/today_appointments.php", null,
                response -> {
                    try {
                        Log.i("UCA", response.toString());
                        for (int i=0;i<response.length();i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Log.i("UCA", jsonObject.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(this, "Error, no response 2", Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.username, WebRequest.password);
            }
        };

        queue.add(request);
    }

    private void initTimeslots() {

        /*Here we are going to create a list of booked timeslots
        * containing every person full name that has a booked time*/

        list = new ArrayList<>();

        list.add(new UpcommingAppointmentsBlock("2021-10-5","10:11", "Alvin", "Axel", "0708888888", 1, "AstraZeneca"));
        list.add(new UpcommingAppointmentsBlock("2021-10-7","10:15", "Olsson", "Gunnar", "0708888899", 2, "Moderna"));
    }

    private void setRecyclerView(String date) {
        /*set the recycler view with the UpcommingAppointmentsBlockAdapter*/
        UpcommingAppointmentsBlockAdapter ua_block_adapter = new UpcommingAppointmentsBlockAdapter(list, context);
        recyclerView_uppc_appoint.setAdapter(ua_block_adapter);
        recyclerView_uppc_appoint.setHasFixedSize(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

}