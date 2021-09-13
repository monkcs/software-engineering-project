package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Administartorlogin extends AppCompatActivity {
/*
    public Administartorlogin(){
        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"bästa Hammarö", "Karlstad", "2 i tabelen"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administartorlogin);
    }


}