package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Dashboard extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        RelativeLayout bokavaccin1 = (RelativeLayout) findViewById(R.id.bokavaccin1);
        bokavaccin1.setOnClickListener(this);
    }

    public void openBokavaccin()
    {
        Intent intent = new Intent(this, Boka_vaccin.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.bokavaccin1:
                System.out.println("button has been pressed");
                openBokavaccin();
                break;


        }
    }
}