package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_tracker.Fragments.BookingStep3Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PlanAndShedVaccs extends AppCompatActivity  /*implements AdapterView.OnItemSelectedListener, View.OnClickListener*/ {

    ArrayList<String> all_days_text;
    ArrayList<String> sublist_days_text;
    private RequestQueue queue;
    private SimpleDateFormat format;
    private Spinner start_date_spinner, end_date_spinner;
    private Button add_time_button, change_booking_time_button;

    private ArrayList<Date> all_days;
    private ArrayList<Date> sublist_days;

    private int index_start = 0;
    private int index_end = 0;

    private boolean error_code = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_and_shed_vaccs);

        queue = Volley.newRequestQueue(this);

        format = new SimpleDateFormat("E, dd MMM yyyy");

        start_date_spinner = (Spinner) findViewById(R.id.start_date_spinner);

        start_date_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                index_start = index;
                updateEndTimeSpinner(index);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        end_date_spinner = (Spinner) findViewById(R.id.end_date_spinner);
        end_date_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                index_end = index;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        add_time_button = (Button) findViewById(R.id.add_time_button);
        add_time_button.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadAppointments();
            }
        }));

        change_booking_time_button = (Button) findViewById(R.id.change_booking_time_button);
        change_booking_time_button.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlanAndShedVaccs.this, AgePrioritizations.class);
                startActivity(intent);
            }
        }));

        Calendar start_calendar = Calendar.getInstance();
        start_calendar.add(Calendar.DATE, 1);

        Calendar end_calendar = (Calendar) start_calendar.clone();
        end_calendar.add(Calendar.MONTH, 1);
        end_calendar.add(Calendar.DATE, 1);

        all_days = new ArrayList<>();
        all_days_text = new ArrayList<>();
        sublist_days_text = new ArrayList<>();
        sublist_days = new ArrayList<>();

        for (Calendar temporary = (Calendar) start_calendar.clone(); temporary.before(end_calendar); temporary.add(Calendar.DATE, 1)) {
            all_days.add(temporary.getTime());
            all_days_text.add(format.format(temporary.getTime()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, all_days_text);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        start_date_spinner.setAdapter(adapter);

        updateEndTimeSpinner(0);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    private void updateEndTimeSpinner(int index) {
        sublist_days = new ArrayList<>();
        for (int i = index; i < all_days.size(); i++) {
            sublist_days.add(all_days.get(i));
        }

        sublist_days_text = new ArrayList<>();
        for (Date date : sublist_days) {
            sublist_days_text.add(format.format(date.getTime()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sublist_days_text);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        end_date_spinner.setAdapter(adapter);
    }

    private void uploadAppointments() {

        int appointment_lenght = 15;
        ArrayList<Date> result = new ArrayList<>();

        for (int i = 0; i <= index_end; i++) {
            result.add(sublist_days.get(i));
        }

        SimpleDateFormat format_server = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        ArrayList<String> server_time_list = new ArrayList<>();

        for (Date date : result) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR, 7);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);


            while (true) {
                if (calendar.get(Calendar.HOUR) < 10) {
                    server_time_list.add(format_server.format(calendar.getTime()));
                    serverUpload(format_server.format(calendar.getTime()));
                    calendar.add(Calendar.MINUTE, appointment_lenght);
                } else {
                    break;
                }
            }
        }

        Toast.makeText(this, "Upload request made", Toast.LENGTH_LONG).show();
    }

    private void serverUpload(String time) {
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "provider/appointment/create.php",
                response -> {
                }, error -> {
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
}
