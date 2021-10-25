package com.example.covid_tracker;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_tracker.Adapter.ViewPagerAdapter;
import com.example.covid_tracker.Fragments.BookingStep1Fragment;
import com.example.covid_tracker.Fragments.BookingStep2Fragment;
import com.example.covid_tracker.Fragments.BookingStep3Fragment;
import com.example.covid_tracker.Fragments.BookingStep4Fragment;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPickerListener;
import com.google.android.material.tabs.TabLayout;
import com.shuhart.stepview.StepView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import java.util.HashMap;
import java.util.Map;

public class BookingActivity extends AppCompatActivity{
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewpager;
    private TabLayout tabLayout;
    public RequestQueue queue;
    private Integer pending = 0;
    private Integer Q1 = 0, Q2 = 0, Q3 = 0, Q4 = 0, Q5 = 0;

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
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
        {
            Boolean health = false;
            int tot = 0;
            if(pref.getBoolean("Q1", false)) {
                tot = tot + 1;
            }
            if(pref.getBoolean("Q2", false)) {
                tot = tot + 1;
            }
            if(pref.getBoolean("Q3", false)) {
                tot = tot + 1;
            }
            if(pref.getBoolean("Q4", false)) {
                tot = tot + 1;
            }
            if(pref.getBoolean("Q5", false)) {
                tot = tot + 1;
            }
            if(pref.getBoolean("Question1", false)) {
                Q1 = 1;
                health = true;
            }
            if(pref.getBoolean("Question2", false)) {
                Q2 = 2;
                health = true;
            }
            if(pref.getBoolean("Question3", false)) {
                Q3 = 3;
                health = true;
            }
            if(pref.getBoolean("Question4", false)) {
                Q4 = 4;
                health = true;
            }
            if(pref.getBoolean("Question5", false)) {
                Q5 = 5;
                health = true;
            }
            //Boolean health = pref.getBoolean("Health_info", true);
            if (health && tot == 5) {
                pending = 1;
                add_questions();
                book_time();
            }
            else if(tot != 5)
                Toast.makeText(this, "Please answer all the questions", Toast.LENGTH_LONG).show();

            else{
                pending = 0;
                book_time();
            }
        }
    }

    public void previous_fragment(View view) {
        viewpager.setCurrentItem(viewpager.getCurrentItem()-1);
    }

    public void book_time(){

        SharedPreferences pref = this.getSharedPreferences("Booking", Context.MODE_PRIVATE);
        Integer id_time =  pref.getInt("time", 0);
        Integer id_vaccine = pref.getInt("vaccine_ID", -1);
        String id_string = String.valueOf(id_vaccine);
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "user/appointment/create.php",
                response -> {
                    Toast.makeText(BookingActivity.this, "Booking time successfull", Toast.LENGTH_LONG).show();
                    Log.i("gDFA", "Vaccine id: " + id_vaccine);
                    Log.i("gDFA", response);
                    removeFromCatalog(id_string);
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
                params.put("pending", pending.toString());
                params.put("vaccine", id_vaccine.toString());

                return params;
            }
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.User.username, WebRequest.User.password);
            }
        };

        queue.add(request);
    }

    public void add_questions(){
        SharedPreferences pref = this.getSharedPreferences("Booking", Context.MODE_PRIVATE);
        Integer id_clinic =  pref.getInt("clinic_ID", -1);
        Integer id_time =  pref.getInt("time", 0);;
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "user/appointment/health_questions.php",
                response -> {
                    //Toast.makeText(BookingActivity.this, "Questions added", Toast.LENGTH_LONG).show();
                    System.out.println(response.toString());

                }, error -> {
            Toast.makeText(BookingActivity.this, "Questions added failed", Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("appointment", id_time.toString());
                params.put("provider", id_clinic.toString());
                params.put("question1", Q1.toString());
                params.put("question2", Q2.toString());
                params.put("question3", Q3.toString());
                params.put("question4", Q4.toString());
                params.put("question5", Q5.toString());

                return params;
            }
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.User.username, WebRequest.User.password);
            }
        };

        queue.add(request);
    }

    public void removeFromCatalog(String vaccine_id){
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "user/vaccine_catalog.php",
                response -> {
                    //success
                }, error -> {
            Toast.makeText(BookingActivity.this, getString(R.string.error_msg), Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("selected_id", vaccine_id);
                params.put("input_value", String.valueOf(-1));
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
