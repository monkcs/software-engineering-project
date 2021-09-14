package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Boka_vaccin extends AppCompatActivity implements View.OnClickListener {

    private Button button1boka, button2omboka, button3avboka, button4tillbaka;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boka_vaccin);
        GreedPerson();

        button1boka = (Button) findViewById(R.id.button11);
        button2omboka = (Button) findViewById(R.id.button22);
        button3avboka = (Button) findViewById(R.id.button33);
        button4tillbaka = (Button) findViewById(R.id.button44);

        button1boka.setOnClickListener(this);
        button2omboka.setOnClickListener(this);
        button3avboka.setOnClickListener(this);
        button4tillbaka.setOnClickListener(this);



    }

    private void GreedPerson() {

        TextView textView = findViewById(R.id.greeding);
        textView.setText("Hello " + getPerson());


    }

    //
    //Hämta person från databas!!!
    //
    private String getPerson() {

        return "Person string blalblalbalb";

    }

    public void openDashboard()
    {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    public void openBooking()
    {
        Intent intent = new Intent(this, Boka_vaccin2.class);
        startActivity(intent);
    }

    //
    // gör denna
    //
    public void removeAppointment() {

        //if tid bokad

        System.out.println("Tog bort bokade tid");

        // else
        // print "finns ingen aktuell tid

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.button11:
                //System.out.println("button boka");
                openBooking();
                break;


            case R.id.button22:
                System.out.println("button omboka");
                break;

            case R.id.button33:
                System.out.println("button avboka");
                removeAppointment();
                break;

            case R.id.button44:
                System.out.println("button tillbaka");
                openDashboard();
                break;
        }
    }
}