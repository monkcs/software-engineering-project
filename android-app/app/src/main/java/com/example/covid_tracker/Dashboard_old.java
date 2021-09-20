package com.example.covid_tracker;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Dashboard_old extends AppCompatActivity {

    private Button logindashbord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_fragments);

        //RelativeLayout bokavaccin1 = (RelativeLayout) findViewById(R.id.bokavaccin1);
        //RelativeLayout digitalhelth = (RelativeLayout) findViewById(R.id.digitalHealth);
        //bokavaccin1.setOnClickListener(this);

        
        //RelativeLayout statistics = (RelativeLayout) findViewById(R.id.btn_statistics);
        //statistics.setOnClickListener(this);
      
        //digitalhelth.setOnClickListener(this);
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
/*
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
    }*/
}
  