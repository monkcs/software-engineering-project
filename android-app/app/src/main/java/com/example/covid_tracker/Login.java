package com.example.covid_tracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.nio.charset.StandardCharsets;
import java.util.Map;


public class Login extends Activity implements OnClickListener {
    private EditText user_email, user_password;
    private Button BtnLogin, BtnReg, adminloginButton;

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
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
// Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

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

    private void setSupportActionBar(Toolbar toolbar) {
    }

    void login() {
        /*System.out.println("Username: " + user_email.getText().toString());
        System.out.println("Username encr: " + encryptData(user_email.getText().toString()));
        System.out.println("Password: " + user_password.getText().toString());
        System.out.println("Password encr: " + encryptData(user_password.getText().toString()));*/
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, WebRequest.urlbase + "user/information.php", null,
                response -> {
                    WebRequest.User.username = encryptData(user_email.getText().toString());
                    WebRequest.User.password = encryptData(user_password.getText().toString());

                    System.out.println("Response: " + response);

                    Intent intent = new Intent(Login.this, Dashboard.class);
                    finish();
                    startActivity(intent);

                }, error -> {
            Toast.makeText(Login.this, R.string.incorrect_credentials, Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(encryptData(user_email.getText().toString()), encryptData(user_password.getText().toString()));
            }
        };

        queue.add(request);
        queue.start();
    }

    private String encryptData(String s){

        //check for åäö
        String mod = s.replaceAll("å", "%");
        mod = mod.replaceAll("ä", "&");
        mod = mod.replaceAll("ö", "#");
        mod = mod.replaceAll("Å", "!");
        mod = mod.replaceAll("Ä", "£");
        mod = mod.replaceAll("Ö", "¤");

        System.out.println("modded string: " + mod);

        String reversed = reverseString(mod);

        byte[] encrypted = reversed.getBytes(StandardCharsets.UTF_8);

        for(int i = 0; i < reversed.length(); i++){
            encrypted[i] = (byte) (encrypted[i] + 1);
        }

        return new String(encrypted);
    }

    private String reverseString(String s){
        // getBytes() method to convert string
        // into bytes[].
        byte[] strAsByteArray = s.getBytes();

        byte[] result = new byte[strAsByteArray.length];

        // Store result in reverse order into the
        // result byte[]
        for (int i = 0; i < strAsByteArray.length; i++)
            result[i] = strAsByteArray[strAsByteArray.length - i - 1];

        //System.out.println(new String(result));

        return new String(result);
    }


    public void signup() {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

    public void loginAdmin() {
        Intent intent = new Intent(this, Administartorlogin.class);
        startActivity(intent);
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnLogIn:
                if (user_email.getText().toString().trim().matches(emailPattern)) {
                    login();
                }
                else
                    Toast.makeText(getApplicationContext(), R.string.invalid_email, Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnReg:
                signup();
                break;

            case R.id.adminLogin:
                loginAdmin();
                break;
        }
    }
}

      
      


