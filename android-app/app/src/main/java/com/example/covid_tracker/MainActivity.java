package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button1, button2, button3, button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    button1 = (Button) findViewById(R.id.button1);
    button2 = (Button) findViewById(R.id.button2);
    button3 = (Button) findViewById(R.id.button3);
    button4 = (Button) findViewById(R.id.button4);

    button1.setOnClickListener(this);
    button2.setOnClickListener(this);
    button3.setOnClickListener(this);
    button4.setOnClickListener(this);


    }

    public void openLogin()
    {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
    }

    public void openDashboard()
    {
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
    }

    public void openStat()
    {
        Intent intent = new Intent(this, StatisticsMenu.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.button1:
                System.out.println("button1");
                openLogin();
                break;


            case R.id.button2:
                System.out.println("button2");
                break;

            case R.id.button3:
                System.out.println("button3");
                openDashboard();
                break;

            case R.id.button4:
                System.out.println("button4");
                openStat();
                break;

        }
    }
}