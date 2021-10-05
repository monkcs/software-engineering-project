package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AdminDashboard extends AppCompatActivity{

    private RelativeLayout rl1,rl2,rl3;
    //private TextView userCount = findViewById(R.id.adminUserCount);
    private TextView tx3 = findViewById(R.id.AdminInboxText);





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
            public void onClick(View view) { adminInbox();}

        });

    }

    public void uppcApoint(){
        Intent intent = new Intent(this, UppcommingApointments.class);
        startActivity(intent);
    }

    public void planAndShedVacc(){
        Intent intent = new Intent(this, PlanAndShedVaccs.class);
        startActivity(intent);
    }
    public void adminInbox(){
        //Intent intent = new Intent(this,CalleCharlieInboxKlassen());
        tx3.setText("Calle and Charle, link your code here//dg");
    }


}