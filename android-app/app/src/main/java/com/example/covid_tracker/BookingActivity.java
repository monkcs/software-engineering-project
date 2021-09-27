package com.example.covid_tracker;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import androidx.viewpager.widget.ViewPager;

public class BookingActivity extends AppCompatActivity{
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewpager;
    private TabLayout tabLayout;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        viewpager = findViewById(R.id.viewpager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.add(new BookingStep1Fragment(), "clinic");
        viewPagerAdapter.add(new BookingStep2Fragment(), "vaccine");
        viewPagerAdapter.add(new BookingStep3Fragment(), "time");
        viewPagerAdapter.add(new BookingStep4Fragment(), "info");

        viewpager.setAdapter(viewPagerAdapter);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewpager);
        viewpager.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {
                return true;
            }
        });

    }
    public void next_fragment(View view) {
        viewpager.setCurrentItem(viewpager.getCurrentItem()+1);
        if(viewpager.getCurrentItem() + 1 == 4)
            Toast.makeText(this, "Book time", Toast.LENGTH_LONG).show();
    }

    public void previous_fragment(View view) {
        viewpager.setCurrentItem(viewpager.getCurrentItem()-1);
    }


}
