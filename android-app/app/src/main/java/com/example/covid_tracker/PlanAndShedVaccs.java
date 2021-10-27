package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlanAndShedVaccs extends AppCompatActivity  /*implements AdapterView.OnItemSelectedListener, View.OnClickListener*/ {

    private RequestQueue queue;
    private SimpleDateFormat format;
    private Spinner age_spinner; //,start_date_spinner, end_date_spinner;
    ArrayList<String> all_days_text;
    ArrayList<String> sublist_days_text;
    ArrayList<String> age_list;
    private int selected_age;
    private Button add_time_button, change_booking_time_button;

    private ArrayList<Date> all_days;
    private ArrayList<Date> sublist_days;

    private int index_start = 0;
    private int index_end = 0;

    private boolean error_code = false;

    private DatePickerDialog picker;
    private EditText editText_start_date, editText_end_date;

    private Calendar start_cal, end_cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_and_shed_vaccs);

        queue = Volley.newRequestQueue(this);

        format = new SimpleDateFormat("E, dd MMM yyyy");

        age_spinner = (Spinner) findViewById(R.id.age_spinner);
        age_list = new ArrayList<>();
        addSomeAges();
        ArrayAdapter<String> age_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, age_list);

        age_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age_spinner.setAdapter(age_adapter);
        age_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String selected = (String) adapterView.getItemAtPosition(i);
            selected_age = Integer.parseInt(selected);

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

        Calendar start_calendar = Calendar.getInstance();
        start_calendar.add(Calendar.DATE, 1);

        Calendar end_calendar = (Calendar) start_calendar.clone();
        end_calendar.add(Calendar.MONTH, 1);
        end_calendar.add(Calendar.DATE, 1);

        all_days = new ArrayList<>();
        all_days_text = new ArrayList<>();
        sublist_days_text = new ArrayList<>();
        sublist_days = new ArrayList<>();


        //-----------------------------------------   NYTT  sprint 4 ----------------------------------------

        editText_start_date=(EditText) findViewById(R.id.editText_start_date);
        editText_start_date.setInputType(InputType.TYPE_NULL);

        start_cal = Calendar.getInstance();
        int tomorrow = start_cal.get(Calendar.DAY_OF_MONTH) +1;
        start_cal.set(Calendar.DAY_OF_MONTH, tomorrow);
        editText_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = start_cal.get(Calendar.DAY_OF_MONTH);
                int month = start_cal.get(Calendar.MONTH);
                int year = start_cal.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(PlanAndShedVaccs.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Date today = Calendar.getInstance().getTime();
                                Calendar temp_cal = Calendar.getInstance();
                                temp_cal.set(year, monthOfYear, dayOfMonth);
                                Date selected_start_date = temp_cal.getTime();

                                if (selected_start_date.after(today)) {
                                    start_cal.set(Calendar.YEAR, year);
                                    start_cal.set(Calendar.MONTH, monthOfYear);
                                    start_cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                    Date start_date = start_cal.getTime();
                                    String temp_string = format.format(start_date);
                                    editText_start_date.setText(temp_string);

                                    sublist_days.clear();
                                    make_end_date_possible();
                                }
                                else {
                                    editText_start_date.setText(R.string.select_start_date);
                                    Toast.makeText(PlanAndShedVaccs.this, "Invalid date, please try again", Toast.LENGTH_LONG).show();

                                }

                            }
                        }, year, month, day);
                picker.show();
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
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

    private void updateSublistDays(){
        Calendar temp_cal = (Calendar) start_cal.clone();
        Calendar temp_cal2 = (Calendar) end_cal.clone();
        temp_cal2.add(Calendar.DATE, 1);
        Date end_date = temp_cal2.getTime();

        if (!sublist_days.isEmpty()) {
        sublist_days.clear();
        }
        for (Date temp_date= temp_cal.getTime(); temp_date.before(end_date); temp_date= temp_cal.getTime()){
            sublist_days.add(temp_date);
            temp_cal.add(Calendar.DATE, 1);
            Log.i("date list", format.format(temp_date));
        }


    }

    private void uploadAppointments() {

        int appointment_lenght = 15;
        ArrayList<Date> result = new ArrayList<>();

        for (int i = 0; i < sublist_days.size(); i++) {
            result.add(sublist_days.get(i));
        }

        SimpleDateFormat format_server = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        JSONArray payload = new JSONArray();

        for (Date date : result) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR, 7);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);


            while (true) {
                if (calendar.get(Calendar.HOUR) < 17) {

                    JSONObject temporary = new JSONObject();

                    try {

                        temporary.put("datetime", format_server.format(calendar.getTime()));
                       // temporary.put("provider", 1);
                        temporary.put("minimum_age", selected_age);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    payload.put(temporary);

                    calendar.add(Calendar.MINUTE, appointment_lenght);
                } else {
                    break;
                }
            }
        }
        serverUpload(payload);
        Toast.makeText(this, R.string.upload_request, Toast.LENGTH_LONG).show();
    }

    private void serverUpload(JSONArray payload) {
        //the global variable int selected_age should be uploaded somewhere...

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, WebRequest.urlbase + "provider/appointment/create.php", payload,
                response -> {
                }, error -> {
        }
        ) {

            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
            }
        };

        queue.add(request);
    }

    private void addSomeAges(){
        age_list.add("65");
        age_list.add("55");
        age_list.add("45");
        age_list.add("35");
        age_list.add("18");
        age_list.add("0");

    }

    private void make_end_date_possible(){

        editText_end_date=(EditText) findViewById(R.id.editText_end_date);
        editText_end_date.setInputType(InputType.TYPE_NULL);
        if (end_cal != null) {editText_end_date.setText(R.string.select_end_date);}

        end_cal = (Calendar) start_cal.clone();
        editText_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = start_cal.get(Calendar.DAY_OF_MONTH);
                int month = start_cal.get(Calendar.MONTH);
                int year = start_cal.get(Calendar.YEAR);

                // date picker dialog
                picker = new DatePickerDialog(PlanAndShedVaccs.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar temp_cal = (Calendar) start_cal.clone();
                                int day_before = temp_cal.get(Calendar.DAY_OF_MONTH) -1;
                                temp_cal.set(Calendar.DAY_OF_MONTH, day_before);
                                Date day_before_selected_start_date = temp_cal.getTime();
                                temp_cal = Calendar.getInstance();
                                temp_cal.set(year, monthOfYear, dayOfMonth);
                                Date selected_end_date = temp_cal.getTime();

                                if (day_before_selected_start_date.before(selected_end_date)) {
                                    end_cal.set(Calendar.YEAR, year);
                                    end_cal.set(Calendar.MONTH, monthOfYear);
                                    end_cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                    Date end_date = end_cal.getTime();
                                    String temp_string = format.format(end_date);
                                    editText_end_date.setText(temp_string);

                                    updateSublistDays();
                                }
                                else {
                                    editText_end_date.setText(R.string.select_end_date);
                                    Toast.makeText(PlanAndShedVaccs.this, "Invalid date, please try again", Toast.LENGTH_LONG).show();

                                }

                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }
}
