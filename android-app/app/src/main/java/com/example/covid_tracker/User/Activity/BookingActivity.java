package com.example.covid_tracker.User.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_tracker.User.Adapter.ViewPagerAdapter;
import com.example.covid_tracker.R;
import com.example.covid_tracker.User.Fragment.BookingStep.BookingStep1Fragment;
import com.example.covid_tracker.User.Fragment.BookingStep.BookingStep2Fragment;
import com.example.covid_tracker.User.Fragment.BookingStep.BookingStep3Fragment;
import com.example.covid_tracker.User.Fragment.BookingStep.BookingStep4Fragment;
import com.example.covid_tracker.WebRequest;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;

import java.util.HashMap;
import java.util.Map;

public class BookingActivity extends AppCompatActivity{
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewpager;
    private TabLayout tabLayout;
    public RequestQueue queue;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        queue = Volley.newRequestQueue(this);
        viewpager = findViewById(R.id.viewpager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.add(new BookingStep1Fragment(), "clinic");
        viewPagerAdapter.add(new BookingStep2Fragment(), "vaccine");
        viewPagerAdapter.add(new BookingStep3Fragment(), "time");
        viewPagerAdapter.add(new BookingStep4Fragment(), "info");

        viewpager.setAdapter(viewPagerAdapter);
        tabLayout = findViewById(R.id.tab_layout);

            tabLayout.setupWithViewPager(viewpager, true);
            tabLayout.clearOnTabSelectedListeners();
            for (View v : tabLayout.getTouchables()) {
                v.setEnabled(false);

        }
        viewpager.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                return true;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void next_fragment(View view) {
        SharedPreferences pref = this.getSharedPreferences("Booking", Context.MODE_PRIVATE);

        if(viewpager.getCurrentItem() + 1 == 1){
            Integer clinic_chosen = pref.getInt("clinic_ID", 0);
            if(clinic_chosen != 0)
                viewpager.setCurrentItem(viewpager.getCurrentItem()+1);
            else
                Toast.makeText(this, "Please choose a clinic", Toast.LENGTH_LONG).show();
        }
        if(viewpager.getCurrentItem() + 1 == 2){
            Integer clinic_chosen = pref.getInt("vaccine_ID", -1);
            if(clinic_chosen != -1) {
                SharedPreferences.Editor edit = this.getSharedPreferences("Booking", Context.MODE_PRIVATE).edit();
                edit.putInt("time", -1);
                edit.apply();
                viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
            }
            else
                Toast.makeText(this, "Please choose a vaccine", Toast.LENGTH_LONG).show();
        }
        if(viewpager.getCurrentItem() + 1 == 3){
            Integer time_chosen = pref.getInt("time", -1);
            if(time_chosen != -1)
                viewpager.setCurrentItem(viewpager.getCurrentItem()+1);
            else
                Toast.makeText(this, "Please choose a time", Toast.LENGTH_LONG).show();
        }
        if(viewpager.getCurrentItem() + 1 == 4)
        {   int tot = 0;
            if(pref.getBoolean("Q1", true))
                tot = tot +1;
            if(pref.getBoolean("Q2", true))
                tot = tot +1;
            if(pref.getBoolean("Q3", true))
                tot = tot +1;
            if(pref.getBoolean("Q4", true))
                tot = tot +1;
            if(pref.getBoolean("Q5", true))
                tot = tot +1;

            if(pref.getBoolean("Health_info", true) && tot == 5)
                Toast.makeText(this, "Please contact your doctor", Toast.LENGTH_LONG).show();
            else if(tot != 5)
                Toast.makeText(this, "Please answer all the questions", Toast.LENGTH_LONG).show();

            else
                book_time();
        }
    }

    public void previous_fragment(View view) {
        viewpager.setCurrentItem(viewpager.getCurrentItem()-1);
    }

    public void book_time(){
        SharedPreferences pref = this.getSharedPreferences("Booking", Context.MODE_PRIVATE);
        Integer id_time =  pref.getInt("time", 0);;
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "user/appointment/create.php",
                response -> {
                    Toast.makeText(BookingActivity.this, "Booking time successfull", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(BookingActivity.this, Dashboard.class);
                    finish();
                    startActivity(intent);

                }, error -> {
            Toast.makeText(BookingActivity.this, "Booking time failed", Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("appointment", id_time.toString());

                return params;
            }
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.User.username, WebRequest.User.password);
            }
        };

        queue.add(request);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
