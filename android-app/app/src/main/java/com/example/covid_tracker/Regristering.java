package com.example.covid_tracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

public class Regristering extends AppCompatActivity implements OnClickListener{
    private static final String TAG_MSG = "message";
    private static final String TAG_SUC = "success";
    private ProgressDialog pDialog;

    private RequestQueue queue;

    JSONParser jsonParser = new JSONParser();
    private static final String SIGNUP_URL = "https://hex.cse.kau.se/~charhabo100/vaccine-tracker/signup.php";

    private EditText email, email_check, forename, lastname, password, number, birthdate, street, city, zipcode;
    private Button BtnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regristering);

        queue = Volley.newRequestQueue(this);

        email = (EditText) findViewById(R.id.email_1);
        email_check = (EditText) findViewById(R.id.email_check);
        forename = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        password = (EditText) findViewById(R.id.passw);
        number = (EditText) findViewById(R.id.phone_number);
        birthdate = (EditText) findViewById(R.id.DOB);
        street = (EditText) findViewById(R.id.street);
        city = (EditText) findViewById(R.id.city);
        zipcode = (EditText) findViewById(R.id.zipCode);

        BtnReg = (Button) findViewById(R.id.signUp);
        BtnReg.setOnClickListener(this);
    }

    void signup() {
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "user/signup.php",
                response -> {
                    Toast.makeText(Regristering.this, R.string.signup_success, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Regristering.this, Login.class);
                    finish();
                    startActivity(intent);

                }, error -> {
            Toast.makeText(Regristering.this, R.string.signup_failed, Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email.getText().toString());
                params.put("firstname", forename.getText().toString());
                params.put("surname",lastname.getText().toString() );
                params.put("password", password.getText().toString());
                params.put("telephone", number.getText().toString());
                params.put("birthdate", birthdate.getText().toString());
                params.put("street", street.getText().toString());
                params.put("postalcode", zipcode.getText().toString());
                params.put("city", city.getText().toString());
                params.put("country", "55");

                return params;
            };
        };

        queue.add(request);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.signUp)
        {
            signup();
        }
    }
}