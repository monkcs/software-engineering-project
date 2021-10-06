package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.Map;

public class AdminDashboard extends AppCompatActivity{

    private RelativeLayout rl1,rl2,rl3;
    //private TextView userCount = findViewById(R.id.adminUserCount);
    private TextView tx3, userCount;
    public RequestQueue queue;






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

        tx3 = findViewById(R.id.AdminInboxText);
        userCount= findViewById(R.id.adminUserCount);
        queue = Volley.newRequestQueue(this);
        userCountRequest();



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

    void userCountRequest(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, WebRequest.urlbase + "provider/quantity.php", null,
                response -> {
                    try {
                        userCount.append(response.getString("quantity"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {
            Toast.makeText(this, R.string.wrong_creeentials, Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
            }
        };

        queue.add(request);
        queue.start();
    }


}