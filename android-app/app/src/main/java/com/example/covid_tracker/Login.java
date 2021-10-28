package com.example.covid_tracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

import java.util.Locale;
import java.util.Map;

import papaya.in.sendmail.SendMail;

//import papaya.in.sendmail.SendMail;


public class Login extends Activity implements OnClickListener {
    private EditText user_email, user_password;
    private Button BtnLogin, BtnReg, BtnForgot, adminloginButton;
    private ChangeLanguage cl= new ChangeLanguage();
    private TextView new_user_tv;


    private RequestQueue queue;
    private final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String TAG_MSG = "message";
    private static final String TAG_SUC = "success";
    private ProgressDialog pDialog;
    //JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL = "https://hex.cse.kau.se/~charhabo100/vaccine-tracker/information.php";

    @Override
    protected void onResume() {

        super.onResume();
        refreshlocaltext();
        this.onCreate(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setAppLocate("sv");
        cl.setLanguage(Login.this, cl.getLanguage());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_login);
        Toolbar toolbar = findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        queue = Volley.newRequestQueue(this);


        user_email = (EditText) findViewById(R.id.email);
        user_password = (EditText) findViewById(R.id.password);
        BtnLogin = (Button) findViewById(R.id.btnLogIn);
        new_user_tv = (TextView) findViewById(R.id.new_user_TextView);
        BtnForgot = (Button) findViewById(R.id.btnForgotPsw);
        BtnReg = (Button) findViewById(R.id.btnReg);
        adminloginButton = (Button) findViewById(R.id.adminLogin);


        BtnReg.setOnClickListener(this);
        adminloginButton.setOnClickListener(this);
        BtnForgot.setOnClickListener(this);

        BtnLogin.setOnClickListener(this);

        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){

            //Anropa för byte av språk
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(cl.is_swedish){
                    cl.setLanguage(Login.this, "en");
                    item.setIcon(cl.getFlagIcon());
                    //setAppLocate("en");
                    //onResume();// flaggbyte fungerar ej har...
                    refreshlocaltext();
                    //finish();
                    //startActivity(getIntent());
                    //recreate();
                }
                else {
                    cl.setLanguage(Login.this, "sv");
                    item.setIcon(cl.getFlagIcon());
                    //setAppLocate("sv");
                    //onResume();//flaggbyte fungerar ej har...
                    refreshlocaltext();
                    //finish();
                    //startActivity(getIntent());
                    //recreate();
                }
                return false;
            }
        });

    }

    private void refreshlocaltext() {
        user_email.setHint(R.string.email);
        user_password.setHint(R.string.password);
        BtnLogin.setText(R.string.login);
        new_user_tv.setText(R.string.new_user);
        BtnReg.setText(R.string.signup);
        adminloginButton.setText(R.string.login_admin);
        BtnForgot.setText(R.string.forgot_psw);
    }


    private void setSupportActionBar(Toolbar toolbar) {
    }

    void login() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, WebRequest.urlbase + "user/information.php", null,
                response -> {
                    WebRequest.User.username = Encryption.encryptData(user_email.getText().toString());
                    WebRequest.User.password = Encryption.encryptData(user_password.getText().toString());
                    Intent intent = new Intent(Login.this, Dashboard.class);
                    intent.putExtra("change_language",cl);
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
        //Intent intent = new Intent(this, Registration.class);
        //startActivity(intent);
        Intent intent = new Intent(this, Registration.class);
        intent.putExtra("change_language",cl);
        startActivity(intent);
    }

    public void loginAdmin() {
        Intent intent = new Intent(this, Administartorlogin.class);
        intent.putExtra("change_language",cl);
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

    public void setAppLocate(String language){


        Resources re = getResources();
        DisplayMetrics dm = re.getDisplayMetrics();
        Configuration config = re.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            config.setLocale(new Locale(language.toLowerCase()));
        }
        else{

            config.locale = new Locale(language.toLowerCase());

        }


        re.updateConfiguration(config, dm);
    }
}

      
      


