package com.example.covid_tracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

import papaya.in.sendmail.SendMail;

//import papaya.in.sendmail.SendMail;


public class Login extends Activity implements OnClickListener {
    private EditText user_email, user_password;
    private Button BtnLogin, BtnReg, adminloginButton, BtnForgot;

    private RequestQueue queue;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String TAG_MSG = "message";
    private static final String TAG_SUC = "success";
    private ProgressDialog pDialog;
    //JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL = "https://hex.cse.kau.se/~charhabo100/vaccine-tracker/information.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        queue = Volley.newRequestQueue(this);

        user_email = (EditText) findViewById(R.id.email);
        user_password = (EditText) findViewById(R.id.password);
        BtnLogin = (Button) findViewById(R.id.btnLogIn);
        BtnReg = (Button) findViewById(R.id.btnReg);
        BtnForgot = (Button) findViewById(R.id.btnForgotPsw);

        adminloginButton = (Button) findViewById(R.id.adminLogin);


        BtnReg.setOnClickListener(this);
        adminloginButton.setOnClickListener(this);
        BtnForgot.setOnClickListener(this);

        BtnLogin.setOnClickListener(this);
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Anropa för byte av språk
                return false;
            }
        });

    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    void login() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, WebRequest.urlbase + "user/information.php", null,
                response -> {
                    WebRequest.User.username = Encryption.encryptData(user_email.getText().toString());
                    WebRequest.User.password = Encryption.encryptData(user_password.getText().toString());
                    Intent intent = new Intent(Login.this, Dashboard.class);
                    finish();
                    startActivity(intent);

                }, error -> {
            Toast.makeText(Login.this, R.string.wrong_creeentials, Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(Encryption.encryptData(user_email.getText().toString()), Encryption.encryptData(user_password.getText().toString()));
            }
        };

        queue.add(request);
        queue.start();
    }

    public void signup() {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

    public void loginAdmin() {
        Intent intent = new Intent(this, Administartorlogin.class);
        startActivity(intent);
    }
    public void ResetPassword(){
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnLogIn:
                if (user_email.getText().toString().trim().matches(emailPattern)) {
                    //Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT).show();
                    login();
                }
                else
                    Toast.makeText(getApplicationContext(), "invalid email address", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnReg:
                signup();
                break;

            case R.id.adminLogin:
                loginAdmin();
                break;
            case R.id.btnForgotPsw:
                ResetPassword();
                break;
        }
    }
}

      
      


