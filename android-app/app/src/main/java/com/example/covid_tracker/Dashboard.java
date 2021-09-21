package com.example.covid_tracker;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard_fragments);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new StatisticsMenu()).commit();


    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.statistic:
                        selectedFragment = new StatisticsMenu();
                        break;
                    case R.id.boka_vaccinicon:
                        selectedFragment = new Boka_vaccin();
                        break;
                    case R.id.digitalHealth:
                        selectedFragment = new DigitalHealth();
                        break;

                    case R.id.faq:
                        selectedFragment = new Faq();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            };




}
  