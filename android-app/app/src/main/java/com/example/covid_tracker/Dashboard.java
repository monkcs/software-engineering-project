package com.example.covid_tracker;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
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

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new DigitalHealth()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
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
                        //selectedFragment = new DigitalHealth();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            };




    /*
    private Button logindashbord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_fragments);

        RelativeLayout bokavaccin1 = (RelativeLayout) findViewById(R.id.bokavaccin1);
        RelativeLayout digitalhelth = (RelativeLayout) findViewById(R.id.digitalHealth);
        bokavaccin1.setOnClickListener(this);

        
        RelativeLayout statistics = (RelativeLayout) findViewById(R.id.btn_statistics);
        statistics.setOnClickListener(this);
      
        digitalhelth.setOnClickListener(this);
    }

    public void openBokavaccin()
    {
        Intent intent = new Intent(this, Boka_vaccin.class);
        startActivity(intent);
    }

    public void openStat()
    {
        Intent intent = new Intent(this, StatisticsMenu.class);
        startActivity(intent);
    }

    public void openDigitalHelth(){
        Intent intent = new Intent(this, DigitalHealth.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.bokavaccin1:
                System.out.println("button has been pressed");
                openBokavaccin();
                break;

            case R.id.btn_statistics:
                openStat();
                break;

            case  R.id.digitalHealth:
                openDigitalHelth();
                break;
        }
    }
     */
}
  