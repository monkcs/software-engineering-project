package com.example.covid_tracker.Fragments;
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
import com.android.volley.toolbox.Volley;
import com.example.covid_tracker.R;
import com.example.covid_tracker.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, WebRequest.urlbase + "user/appointment/available.php", null,
                response -> {
                    try {
                        System.out.println("response length: " + response.length());
                        for (int i=0; i<response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Times temporary= new Times(jsonObject.getInt("id"), jsonObject.getString("datetime"));
                            timesArrayList.add(temporary);
                            // int - id ; Datetime ; int


                        }

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }, error -> {

        });
        /*
        timesArrayList.add(new Times(0,"2021-09-29 10:20:00.00000"));
        timesArrayList.add(new Times(1,"2021-09-29 10:30:00.00000"));
        timesArrayList.add(new Times(2,"2021-09-29 10:40:00.00000"));
        timesArrayList.add(new Times(3,"2021-09-29 10:50:00.00000"));
        timesArrayList.add(new Times(4,"2021-09-29 11:00:00.00000"));

        timesArrayList.add(new Times(5,"2021-09-28 11:10:00.00000"));
        */

        queue.add(request);
    }

    private void showTimes(View itemView, Date date) {
        ListView availabletimes;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String calendardatetodatabas = formatter.format(date);
        //System.out.println(calendardatetodatabas);



        ArrayList<String> availabletimeslist = new ArrayList<>();

        for (Times time : timesArrayList) {
            if (calendardatetodatabas.equals(getdateortime(time.time, 1))){
                availabletimeslist.add(getdateortime(time.time, 2));
            }
        }

        availabletimes = itemView.findViewById(R.id.timesaviable);

        ArrayAdapter<String> timeslist = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, availabletimeslist);
        availabletimes.setAdapter(timeslist);
        availabletimes.setOnItemClickListener((adapterView, view, i, l) -> {
            //String time = aviabletimeslist.get(i).toString();
            String time = availabletimes.getItemAtPosition(i).toString();
            Toast.makeText(getActivity(), time + " is selected!", Toast.LENGTH_SHORT).show();
            //to save or not to save that is the question
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

        public String toString()
        {
            return time;
        }
    }

}