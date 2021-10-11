package com.example.covid_tracker.Administrator.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_tracker.Administrator.Adapter.UpcommingAppointmentsBlockAdapter;
import com.example.covid_tracker.Administrator.Block.UpcommingApointment.UpcommingAppointmentsBlock;
import com.example.covid_tracker.R;
import com.example.covid_tracker.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class UpcomingAppointments extends AppCompatActivity {

    private Context context;
    private RequestQueue queue;

    CalendarView calView_uppcAppoint;
    RecyclerView recyclerView_uppc_appoint;

    String currDate;

    ArrayList<UpcommingAppointmentsBlock> booked_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        queue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_uppcomming_apointments);

        calView_uppcAppoint = findViewById(R.id.calView_uppcAppoint);
        recyclerView_uppc_appoint = findViewById(R.id.recyclerView_uppc_appoint);

        currDate = getDate();

        getBookedTimes(currDate);

        calView_uppcAppoint.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                String day = String.valueOf(dayOfMonth);

                /*Masterpiece down below*/
                if(day.length() < 2) {
                    String temp = day;
                    day = "0" + day;
                }

                currDate = String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + day;
                Log.i("UCA", "Current date: " + currDate);
                getBookedTimes(currDate);
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar
    }

    private void getBookedTimes(String currDate){
        booked_list = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, WebRequest.urlbase + "provider/today_appointments.php", null,
                response -> {
                    try {
                        Log.i("UCA", "In request: current date is: " + currDate);
                        for (int i=0;i<response.length();i++) {
                            String datetime, date;
                            JSONObject jsonObject = response.getJSONObject(i);
                            datetime = jsonObject.getString("datetime");
                            date = getDateAndTime(datetime, 0);
                            if(date.equals(currDate))
                                booked_list.add(new UpcommingAppointmentsBlock(date, getDateAndTime(datetime, 1), jsonObject.getString("surname"),
                                        jsonObject.getString("firstname"), jsonObject.getString("telephone"), Integer.parseInt(jsonObject.getString("dose"))));
                        }
                        setRecyclerView(booked_list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    /*if no array can be found, look for jsonObject*/
                    Toast.makeText(this, "No response from server, could not retrieve booked times", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
            }
        };
        queue.add(request);
    }

    private void setRecyclerView(ArrayList<UpcommingAppointmentsBlock> list) {
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

    private String getDateAndTime(String datetime, int b){
        String array[];
        array = datetime.split("\\s");
        if(b==0)
            return array[0];
        else {
            array[1] = array[1].substring(0, array[1].length() - 3);
            return array[1];
        }
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

}