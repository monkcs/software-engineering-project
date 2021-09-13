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


public class Login extends AppCompatActivity implements OnClickListener{
    private EditText user_email, user_password;
    private Button BtnLogin, BtnReg;

    private static final String TAG_MSG = "message";
    private static final String TAG_SUC = "success";
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL = "https://hex.cse.kau.se/~amanjohn102/vaccin_tracker/test_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_login);

        user_email = (EditText) findViewById(R.id.email);
        user_password = (EditText) findViewById(R.id.password);
        BtnLogin = (Button) findViewById(R.id.btnLogIn);
        BtnReg = (Button) findViewById(R.id.btnReg);

        BtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Regristering.class);
                startActivity(intent);
                finish();
            }
        });
        // BtnLogin.setOnClickListener(new View.OnClickListener(){
        BtnLogin.setOnClickListener(this);
    }
        @Override
        public void onClick (View v){
        if(v.getId() == R.id.btnLogIn){
            //case R.id.btnLogIn:
                new AttemptLogin().execute();
        }
        }

        class AttemptLogin extends AsyncTask<String, String, String> {
        boolean failure = false;


        protected void onPreExcecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Attempting for login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            int success;
            String username = user_email.getText().toString();
            String pass = user_password.getText().toString();

            try {
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", pass));
                Log.d("request!", "starting");

                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                Log.d("Login attempt", json.toString());

                success = json.getInt(TAG_SUC);
                System.out.println(success);
                if (success == 1) {
                    Log.d("Successfully Login!", json.toString());
                    //Lägg till när klassen skapats
                    Intent intent = new Intent(Login.this, LoginDashboard.class);
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
                Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

}

