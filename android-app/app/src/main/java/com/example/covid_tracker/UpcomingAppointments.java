package com.example.covid_tracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class UpcomingAppointments extends Fragment {

    private View view;
    private Context context;
    private RequestQueue queue;
    private Button change;

    CalendarView calView_uppcAppoint;
    RecyclerView recyclerView_uppc_appoint;
    TextView tv_none_booked;
    ImageView imageviewis;

    String currDate;

    ArrayList<UpcommingAppointmentsBlock> booked_list;

    @Override
    public void onResume() {
        super.onResume();
        getBookedTimes(currDate);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_uppcomming_apointments, container, false);
        context = getActivity();

        queue = Volley.newRequestQueue(getActivity());


        imageviewis = (ImageView) view.findViewById(R.id.infobuttonuppa);
        imageviewis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maketoastinfo();
            }
        });


        calView_uppcAppoint = view.findViewById(R.id.calView_uppcAppoint);
        recyclerView_uppc_appoint = view.findViewById(R.id.recyclerView_uppc_appoint);
        tv_none_booked = view.findViewById(R.id.tv_none_booked);

        currDate = getDate();

        change = (Button) view.findViewById(R.id.change_uppcomming);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ageChanger();

            }
        });

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
                getBookedTimes(currDate);
            }
        });

        return view;
    }

    private void maketoastinfo() {

        Toast t1;
        t1 = Toast.makeText(getActivity(), "Click here if you want to change the current age group for appointments", Toast.LENGTH_LONG);
        t1.show();

    }

    private void ageChanger() {

        Intent intent = new Intent(getActivity(), age_change.class);
        startActivity(intent);

    }

    private void getBookedTimes(String currDate){
        booked_list = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, WebRequest.urlbase + "provider/today_appointments.php", null,
                response -> {
                    try {
                        for (int i=0;i<response.length();i++) {
                            String datetime, date;
                            JSONObject jsonObject = response.getJSONObject(i);
                            datetime = jsonObject.getString("datetime");
                            date = getDateAndTime(datetime, 0);
                            if(date.equals(currDate))
                                booked_list.add(new UpcommingAppointmentsBlock(date, getDateAndTime(datetime, 1), Integer.parseInt(jsonObject.getString("account")), jsonObject.getString("surname"),
                                        jsonObject.getString("firstname"), jsonObject.getString("telephone"), Integer.parseInt(jsonObject.getString("dose"))));
                        }
                        if(booked_list.size() > 0) {
                            tv_none_booked.setText("");
                            setRecyclerView(booked_list);
                        }
                        else{
                            tv_none_booked.setText(getText(R.string.no_appointments_today));
                            setRecyclerView(booked_list);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    tv_none_booked.setText(getText(R.string.no_appointments_today));
                    setRecyclerView(booked_list);
                    Toast.makeText(getActivity(), R.string.no_appointments_today, Toast.LENGTH_SHORT).show();
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