package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Map;

public class Administartorlogin extends AppCompatActivity implements View.OnClickListener {
    /*
        public Administartorlogin(){
            Spinner dropdown = findViewById(R.id.spinner1);
            String[] items = new String[]{"bästa Hammarö", "Karlstad", "2 i tabelen"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            dropdown.setAdapter(adapter);
        }

    */
    private RequestQueue queue;
    private Button loginbutton, gobackbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administartorlogin);

        queue = Volley.newRequestQueue(this);

        getProviders();

        loginbutton= findViewById(R.id.adminloginbutton);
        gobackbutton= findViewById(R.id.gobackbutton);

        loginbutton.setOnClickListener(this);
        gobackbutton.setOnClickListener(this);
    }

    void getProviders() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, WebRequest.urlbase + "provider/account.php", null,
                response -> {

            try {
                Spinner dropdown = findViewById(R.id.clinics);
                ArrayList<String> items = new ArrayList<String>();

                for (int i = 0; i < response.length(); i++)
                {
                    items.add(response.get(i).toString());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
                dropdown.setAdapter(adapter);
            }
            catch (Exception e) {}

                response.toString();

                }, error -> {
            Toast.makeText(Administartorlogin.this, R.string.wrong_creeentials, Toast.LENGTH_LONG).show();
        });

        queue.add(request);
    }
    public void login() {
        Intent intent = new Intent(this,AdminDashboard.class);
        startActivity(intent);
    }

    public void goback() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.adminloginbutton:
                login();
                break;

            case R.id.gobackbutton:
                goback();
                break;
        }

    }
}