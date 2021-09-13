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

    private Button logindashbord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        logindashbord = (Button) findViewById(R.id.LoginDashbord);
        logindashbord.setOnClickListener(this);

        RelativeLayout bokavaccin1 = (RelativeLayout) findViewById(R.id.bokavaccin1);
        bokavaccin1.setOnClickListener(this);
    }



    public void Login(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
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

            case R.id.LoginDashbord:
                System.out.println("login button has been pressed");

                Login();
                break;

        }
    }
}
  