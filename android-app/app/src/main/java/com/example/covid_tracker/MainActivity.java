package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button1, button2, button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    button1 = (Button) findViewById(R.id.button1);
    button2 = (Button) findViewById(R.id.button2);
    button3 = (Button) findViewById(R.id.button3);

    button1.setOnClickListener(this);
    button2.setOnClickListener(this);
    button3.setOnClickListener(this);



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
        }
    }
}