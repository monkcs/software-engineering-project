package com.example.covid_tracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Registration extends Activity implements OnClickListener{
    private static final String TAG_MSG = "message";
    private static final String TAG_SUC = "success";
    private final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private RequestQueue queue;

    private EditText email, email_check, forename, lastname, password, number, birthdate, street, city, zipcode;
    private Button BtnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regristering);
        Toolbar toolbar = findViewById(R.id.toolbar_reg);
        queue = Volley.newRequestQueue(this);

        email = findViewById(R.id.email_1);
        email_check = findViewById(R.id.email_check);
        forename = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        password = findViewById(R.id.passw);
        number = findViewById(R.id.phone_number);
        birthdate = findViewById(R.id.DOB);
        street = findViewById(R.id.street);
        city = findViewById(R.id.city);
        zipcode = findViewById(R.id.zipCode);

        BtnReg = findViewById(R.id.signUp);
        BtnReg.setOnClickListener(this);
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(item -> {
            //Anropa för byte av språk
            return false;
        });
    }


    void signup() {
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "user/signup.php",
                response -> {
                    Toast.makeText(Registration.this, R.string.signup_success, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Registration.this, Login.class);
                    finish();
                    startActivity(intent);

                }, error -> Toast.makeText(Registration.this, R.string.signup_failed, Toast.LENGTH_LONG).show()) {
            @Override
            public Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<>();
                params.put("email", Encryption.encryptData(email.getText().toString()));
                params.put("firstname", Encryption.encryptData(forename.getText().toString()));
                params.put("surname", Encryption.encryptData(lastname.getText().toString()));
                params.put("password", Encryption.encryptData(password.getText().toString()));
                params.put("telephone", Encryption.encryptData(number.getText().toString()));
                params.put("birthdate", birthdate.getText().toString());
                params.put("street", Encryption.encryptData(street.getText().toString()));
                params.put("postalcode", Encryption.encryptData(zipcode.getText().toString()));
                params.put("city", Encryption.encryptData(city.getText().toString()));
                params.put("country", "55");

                return params;
            }
        };

        queue.add(request);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.signUp)
        {
            if (email.getText().toString().trim().matches(emailPattern) && email_check.getText().toString().trim().matches(emailPattern)) {
                if (email.getText().toString().equals(email_check.getText().toString()))
                    signup();
                else
                    Toast.makeText(getApplicationContext(), "invalid email address", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getApplicationContext(), "invalid email address", Toast.LENGTH_SHORT).show();

        }
    }
}
