package com.example.covid_tracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import papaya.in.sendmail.SendMail;

public class ForgotPassword extends Activity implements View.OnClickListener {
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private RequestQueue queue;
    private EditText email;
    private Button goBack, send;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);

        queue = Volley.newRequestQueue(this);
        goBack= findViewById(R.id.BtnGoback);
        send= findViewById(R.id.BtnSend);
        email = findViewById(R.id.email_reset);

        goBack.setOnClickListener(this);
        send.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_forgotPsw);
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Anropa för byte av språk
                return false;
            }
        });
    }

    public void sendInfo(){
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "general/forgot_password.php",
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        password = object.getString("password");
                        sendEmail(Encryption.decryptData(password));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(ForgotPassword.this, Login.class);
                    finish();
                    startActivity(intent);

                    },error -> {
            Toast.makeText(ForgotPassword.this, R.string.error_msg, Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", Encryption.encryptData(email.getText().toString()));

                return params;
            };
        };

        queue.add(request);

    }
    public void sendEmail(String psw){
        SendMail mail = new SendMail(Util.EMAIL, Util.PASSWORD,
                email.getText().toString(),
                getString(R.string.forgot_psw),
                getString(R.string.msg_forgotpsw)  + " "+ psw);

        mail.execute();
    }
    public void goBack() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.BtnSend){
            sendInfo();
        }
        else if(view.getId() == R.id.BtnGoback)
            goBack();
    }
}
