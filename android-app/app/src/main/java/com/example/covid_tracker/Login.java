package com.example.covid_tracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Login extends AppCompatActivity implements OnClickListener {
    private EditText user_email, user_password;
    private Button BtnLogin, BtnReg, adminloginButton;

    private RequestQueue queue;

    private static final String TAG_MSG = "message";
    private static final String TAG_SUC = "success";
    private ProgressDialog pDialog;
    //JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL = "https://hex.cse.kau.se/~charhabo100/vaccine-tracker/information.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_login);

        queue = Volley.newRequestQueue(this);

        user_email = (EditText) findViewById(R.id.email);
        user_password = (EditText) findViewById(R.id.password);
        BtnLogin = (Button) findViewById(R.id.btnLogIn);
        BtnReg = (Button) findViewById(R.id.btnReg);

        adminloginButton = (Button) findViewById(R.id.adminLogin);


        BtnReg.setOnClickListener(this);
        adminloginButton.setOnClickListener(this);

        BtnLogin.setOnClickListener(this);
    }

    void login() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, WebRequest.urlbase + "user/information.php", null,
                response -> {
                    WebRequest.username = user_email.getText().toString();
                    WebRequest.password = user_password.getText().toString();

                    Intent intent = new Intent(Login.this, Dashboard.class);
                    finish();
                    startActivity(intent);

                }, error -> {
            Toast.makeText(Login.this, R.string.wrong_creeentials, Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(user_email.getText().toString(), user_password.getText().toString());
            }
        };

        queue.add(request);
    }


    public void signup() {
        Intent intent = new Intent(this, Regristering.class);
        startActivity(intent);
    }

    public void loginAdmin() {
        Intent intent = new Intent(this, Administartorlogin.class);
        startActivity(intent);
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnLogIn:
                login();
                break;

            case R.id.btnReg:
                System.out.println("login button has been pressed");
                //TODO signup
                signup();
                break;

            case R.id.adminLogin:
                loginAdmin();
                break;
        }
    }
}

      
      


