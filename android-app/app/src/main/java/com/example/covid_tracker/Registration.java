package com.example.covid_tracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import papaya.in.sendmail.SendMail;

public class Registration extends Activity implements OnClickListener{
    private static final String TAG_MSG = "message";
    private static final String TAG_SUC = "success";
    private final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private RequestQueue queue;

    private boolean age_check;

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
                    sendEmail();
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
    public void sendEmail(){
        SendMail mail = new SendMail(Util.EMAIL, Util.PASSWORD,
                email.getText().toString(),
                getString(R.string.new_account),
                getString(R.string.msg_reg) + " "+ email.getText().toString()+ "\n" + getString(R.string.password) + " "+ password.getText().toString());

        mail.execute();
    }

    private boolean checkAge(String birthdate) throws ParseException {
        boolean age_ok = false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currDate = sdf.parse(getDate());
        Date parsed_birthDate = sdf.parse(birthdate);

        long diffInMillies = Math.abs(currDate.getTime() - parsed_birthDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        double age = diff / 365.25;


        if(age >= 18)
            age_ok = true;
        else{
            age_ok = false;
        }


        return age_ok;
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();


        return dateFormat.format(date);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.signUp)
        {
            try {
                age_check = checkAge(birthdate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (email.getText().toString().trim().matches(emailPattern) && email_check.getText().toString().trim().matches(emailPattern)) {
                if (email.getText().toString().equals(email_check.getText().toString()))
                    if(age_check)
                        signup();
                    else{
                        Toast.makeText(getApplicationContext(), "You must be over 18 to register", Toast.LENGTH_SHORT).show();
                    }
                else
                    Toast.makeText(getApplicationContext(), "invalid email address", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getApplicationContext(), "invalid email address", Toast.LENGTH_SHORT).show();

        }
    }
}
