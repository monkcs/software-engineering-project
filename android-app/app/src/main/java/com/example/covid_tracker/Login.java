package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button loginButton, signUpButton, adminloginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_login);

        loginButton = (Button) findViewById(R.id.Login);
        signUpButton = (Button) findViewById(R.id.Signup);
        adminloginButton = (Button) findViewById(R.id.adminLogin);
        
        loginButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
        adminloginButton.setOnClickListener(this);
    }


    public void login(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    public void signUp(){
        Intent intent = new Intent(this, Regristering.class);
        startActivity(intent);
    }
    public void loginAdmin(){
        Intent intent = new Intent(this, Administartorlogin.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.Login:
                System.out.println("has login");
                //TODO login
                login();
                break;

            case R.id.Signup:
                System.out.println("login button has been pressed");
                //TODO signup
                signUp();
                break;

            case R.id.adminLogin:
                loginAdmin();
                break;
        }
    }

}