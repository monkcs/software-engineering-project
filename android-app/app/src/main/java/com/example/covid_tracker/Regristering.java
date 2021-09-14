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


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

public class Regristering extends AppCompatActivity implements OnClickListener{
    private static final String TAG_MSG = "message";
    private static final String TAG_SUC = "success";
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String SIGNUP_URL = "https://hex.cse.kau.se/~charhabo100/vaccine-tracker/signup.php";

    private EditText email, email_check, forename, lastname, password, number, DOB, street, city, zipcode;
    private Button BtnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regristering);

        email = (EditText) findViewById(R.id.email_1);
        email_check = (EditText) findViewById(R.id.email_check);
        forename = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        password = (EditText) findViewById(R.id.passw);
        number = (EditText) findViewById(R.id.phone_number);
        DOB = (EditText) findViewById(R.id.DOB);
        street = (EditText) findViewById(R.id.street);
        city = (EditText) findViewById(R.id.city);
        zipcode = (EditText) findViewById(R.id.zipCode);

        BtnReg = (Button) findViewById(R.id.signUp);

        BtnReg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.signUp)
            new AttemptRegister().execute();

    }
    class AttemptRegister extends AsyncTask<String, String, String>{
        protected void onPreExcecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Regristering.this);
            pDialog.setMessage("Attempting for registration...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {
            int success;
            String user = email.getText().toString();
            String first_name = forename.getText().toString();
            String last_name = lastname.getText().toString();
            String pass = password.getText().toString();
            String phone = number.getText().toString();
            String d_o_b = DOB.getText().toString();
            String Street = street.getText().toString();
            String ZipCode = zipcode.getText().toString();
            String City = city.getText().toString();

            try {
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("email", user));
                params.add(new BasicNameValuePair("firstname", first_name));
                params.add(new BasicNameValuePair("surname", last_name));
                params.add(new BasicNameValuePair("password", pass));
                params.add(new BasicNameValuePair("telephone", phone));
                params.add(new BasicNameValuePair("birthdate", d_o_b));
                params.add(new BasicNameValuePair("street", Street));
                params.add(new BasicNameValuePair("postalcode", ZipCode));
                params.add(new BasicNameValuePair("city", City));

                Log.d("request!", "starting");

                JSONObject json = jsonParser.makeHttpRequest(SIGNUP_URL, "POST", params);

                Log.d("Register attempt", json.toString());

                success = json.getInt(TAG_SUC);
                System.out.println(success);
                if (success == 1) {
                    Log.d("Successfully Registerd!", json.toString());
                    Intent intent = new Intent(Regristering.this, Login.class);
                    finish();
                    startActivity(intent);
                    return json.getString(TAG_MSG);
                } else {
                    return json.getString(TAG_MSG);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String message) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (message != null) {
                Toast.makeText(Regristering.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}