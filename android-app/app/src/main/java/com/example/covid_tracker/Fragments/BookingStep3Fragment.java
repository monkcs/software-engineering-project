package com.example.covid_tracker.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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




    private void init(View itemView){
        HorizontalCalendar horizontalCalendar;

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

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
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "user/appointment/available.php",
                response -> {
                   // try {
                        System.out.println("response length: " + response.toString());
                    try {
                        JSONArray array = new JSONArray(response.toString());

                        for (int i=0; i<array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        Times temporary= new Times(jsonObject.getInt("id"), jsonObject.getString("datetime"));
                        timesArrayList.add(temporary);
                        // int - id ; Datetime ; int


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                   // } catch (JSONException e) {

                 //       e.printStackTrace();
                 //   }
                }, error -> {
                int mess;
        }
        ) {
            @Override
            public Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("provider", "1");

                return params;
            };
        };

        queue.add(request);
    }

    private void showTimes(View itemView, Date date) {
        ListView availabletimes;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String calendardatetodatabas = formatter.format(date);
        //System.out.println(calendardatetodatabas);



        ArrayList<Times> availabletimeslist = new ArrayList<>();

        for (Times time : timesArrayList) {
            if (calendardatetodatabas.equals(getdateortime(time.time, 1))){
                availabletimeslist.add(time);
            }
        }

        availabletimes = itemView.findViewById(R.id.timesaviable);

        ArrayAdapter<Times> timeslist = new ArrayAdapter<Times>(getActivity(), android.R.layout.simple_list_item_1, availabletimeslist);

        availabletimes.setAdapter(timeslist);

        availabletimes.setOnItemClickListener((adapterView, view, i, l) -> {

            Times temp  = timeslist.getItem(i);
            Toast.makeText(getActivity(), temp.id + " Är vald!", Toast.LENGTH_SHORT).show();
            //to save or not to save that is the question

            SharedPreferences.Editor edit = getActivity().getSharedPreferences("Booking", Context.MODE_PRIVATE).edit();
            edit.putInt("Time", temp.getId());
            edit.commit();

        });

    }

    private String getdateortime(String source, int part){
        String leftover = new String("");
        switch (part){
            case 1:
                leftover =  source.split(" ")[0];
                break;
            case 2:
                leftover = source.split(" ")[1].substring(0,5);
                break;
        }

        return leftover;
    }


    class Times
    {
        public Times(int id, String time)
        {
            this.id = id;
            this.time = time;
        };
        public final int id;
        public final String time;

        public String getHourMinits(){ return this.time.split(" ")[1].substring(0,5);}

        public int getId()
        {
            return id;
        }

        public String toString()
        {
            return id +" " +time;
        }
    }

}