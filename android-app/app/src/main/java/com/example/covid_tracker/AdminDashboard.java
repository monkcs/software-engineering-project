package com.example.covid_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboard extends AppCompatActivity{

    private RelativeLayout rl1, rl2, rl3;
    //private TextView userCount = findViewById(R.id.adminUserCount);





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        rl1= (RelativeLayout) findViewById(R.id.Rellay1);
        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uppcApoint();
            }
        });

        rl2 = (RelativeLayout) findViewById(R.id.Rellay2);
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planAndShedVacc();
            }
        });

        rl3 = (RelativeLayout) findViewById(R.id.Rellay3);
        rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inboxAdmin();
            }
        });
    }

    public void inboxAdmin(){
        Intent intent = new Intent(this, inboxAdmin.class);
        startActivity(intent);
    }

    public void uppcApoint(){
        Intent intent = new Intent(this, UpcomingAppointments.class);
        startActivity(intent);
    }

    public void planAndShedVacc(){
        Intent intent = new Intent(this, PlanAndShedVaccs.class);
        startActivity(intent);
    }


}