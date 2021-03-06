package com.example.covid_tracker.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_tracker.Login;
import com.example.covid_tracker.R;
import com.example.covid_tracker.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class BookingStep3Fragment extends Fragment {
    private ArrayList<Times> timesArrayList = new ArrayList<>();

    private RequestQueue queue;


    public BookingStep3Fragment() {
        // required empty public constructor.
    }

    static BookingStep3Fragment instance;

    public static BookingStep3Fragment getInstance() {
        if (instance == null)
            instance = new BookingStep3Fragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        queue = Volley.newRequestQueue(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View itemView = inflater.inflate(R.layout.fragment_bookingstep3, container, false);
        getListOfavailableappointment();
        init(itemView);

        return itemView;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.item2_menu2:
                System.out.println("hejsan du loggades");
                loginScreen();
                return true;


            default:
                return super.onOptionsItemSelected(item);


        }



    }

    private void loginScreen() {

        Intent intent = new Intent(getActivity(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    private void init(View itemView){
        HorizontalCalendar horizontalCalendar;

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, 0);

        horizontalCalendar = new HorizontalCalendar.Builder(itemView, R.id.calendarView)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(5)
                .dayNameFormat("EEE")
                .dayNumberFormat("dd")
                .monthFormat("MMM")
                .textSize(14f, 24f, 14f)
                .showDayName(true)
                .showMonthName(true)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                showTimes(itemView, date);
            }

        });

    }
    public void getListOfavailableappointment() {
        SharedPreferences pref = getActivity().getSharedPreferences("Booking", Context.MODE_PRIVATE);
        Integer id_clinic =  pref.getInt("clinic_ID", -1);
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "user/appointment/available.php",
                response -> {
                   // try {
                        System.out.println("response length: " + response);
                    try {
                        JSONArray array = new JSONArray(response);

                        for (int i=0; i<array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        Times temporary= new Times(jsonObject.getInt("id"), jsonObject.getString("datetime"));
                        timesArrayList.add(temporary);
                        // int - id ; Datetime ; int


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {
            System.out.println("Error... no response :(");

            int mess;
        }
        ) {
            @Override
            public Map<String, String> getParams()  {

                Map<String, String> params = new HashMap<>();
                params.put("provider", id_clinic.toString());
              
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.User.username, WebRequest.User.password);
            }
        };

        queue.add(request);
    }

    private void showTimes(View itemView, Date date) {
        ListView availabletimes;

        @SuppressLint
        ("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String calendardatetodatabas = formatter.format(date);
        //System.out.println(calendardatetodatabas);



        ArrayList<Times> availabletimeslist = new ArrayList<>();

        for (Times time : timesArrayList) {
            if (calendardatetodatabas.equals(getdateortime(time.time, 1))){
                availabletimeslist.add(time);
            }
        }

        availabletimes = itemView.findViewById(R.id.timesaviable);

        ArrayAdapter<Times> timeslist = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, availabletimeslist);

        availabletimes.setAdapter(timeslist);

        availabletimes.setOnItemClickListener((adapterView, view, i, l) -> {

            Times temp  = timeslist.getItem(i);
            //to save or not to save that is the question
            SharedPreferences.Editor edit = getActivity().getSharedPreferences("Booking", Context.MODE_PRIVATE).edit();
            edit.putInt("time", -1);
            if(temp.getId() != -1) {
                edit.putInt("time", temp.getId());
            }
            else{
                edit.putInt("time", -1);
            }
            edit.apply();

        });

    }

    private String getdateortime(String source, int part){
        switch (part){
            case 1:
                return source.split(" ")[0];
            case 2:
                String time = source.split(" ")[1];
                return time.substring(0,5); //hours minutes
                //return source.split(" ")[1].substring(0,5);
            default:
                return "";
        }
    }


    class Times
    {
        public Times(int id, String time)
        {
            this.id = id;
            this.time = time;
        }
        public final int id;
        public final String time;

        public String getHourMinits(){ return this.time.split(" ")[1].substring(0,5);}

        public int getId()
        {
            return id;
        }

        @NonNull
        public String toString()
        {
            return  getHourMinits();
        }
    }

}