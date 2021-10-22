package com.example.covid_tracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Registration extends Activity implements OnClickListener{
    private static final String TAG_MSG = "message";
    private static final String TAG_SUC = "success";
    private ProgressDialog pDialog;

    private RequestQueue queue;

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
        String encr = decryptData("zusfxr");
        //System.out.println("Encr: " + encr);
        //String decr = decryptData(encr);
        System.out.println("Decr: " + encr);

    }

    void signup() {
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "user/signup.php",
                response -> {
                    Toast.makeText(Registration.this, R.string.signup_success, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Registration.this, Login.class);
                    finish();
                    startActivity(intent);

                }, error -> {
            Toast.makeText(Registration.this, R.string.signup_failed, Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", encryptData(email.getText().toString()));
                params.put("firstname", encryptData(forename.getText().toString()));
                params.put("surname", encryptData(lastname.getText().toString()));
                params.put("password", encryptData(password.getText().toString()));
                params.put("telephone", encryptData(number.getText().toString()));
                params.put("birthdate", birthdate.getText().toString());
                params.put("street", encryptData(street.getText().toString()));
                params.put("postalcode", encryptData(zipcode.getText().toString()));
                params.put("city", encryptData(city.getText().toString()));
                params.put("country", "55");

                return params;
            };
        };

        queue.add(request);
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

    public String decryptData(String s){

        byte[] decryptedChars = s.getBytes(StandardCharsets.UTF_8);

        for(int i = 0; i < s.length(); i++){
            decryptedChars[i] = (byte) (decryptedChars[i] - 1);
        }

        String decryptedWithSpec = reverseString(new String(decryptedChars));

        //check for åäö
        String decrypted = decryptedWithSpec.replaceAll("%", "å");
        decrypted = decrypted.replaceAll("&", "å");
        decrypted = decrypted.replaceAll("#", "ö");
        decrypted = decrypted.replaceAll("!", "Å");
        decrypted = decrypted.replaceAll("£", "Ä");
        decrypted = decrypted.replaceAll("¤", "Ö");

        return decrypted;
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

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.signUp)
        {
            signup();
        }
    }
}
