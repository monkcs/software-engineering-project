package com.example.covid_tracker;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;

public class HandlePerson extends AppCompatActivity {

    private RequestQueue queue;

    TextView tv_personFullName, tv_phone, tv_bookedDate, tv_bookedDose;
    Button btn_confirmVaccine, btn_cancelAppoint;
    String [] nameArray;
    String firstName, lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_person);

        queue = Volley.newRequestQueue(this);

        Bundle extras = getIntent().getExtras();

        tv_personFullName = findViewById(R.id.tv_personFullName);
        tv_phone = findViewById(R.id.tv_phone);
        tv_bookedDate = findViewById(R.id.tv_bookedDate);
        tv_bookedDose = findViewById(R.id.tv_bookedDose);

        btn_confirmVaccine = findViewById(R.id.btn_confirmVaccine);
        btn_cancelAppoint = findViewById(R.id.btn_cancelAppoint);

        /*functionality to send data between activities*/
        if (extras != null) {
            /*The key argument here must match that used in the other activity*/
            String fullName = extras.getString("key");
            nameArray = fullName.split(",");
            lastName = nameArray[0];
            firstName = nameArray[1];

            tv_personFullName.setText(lastName + ", " + firstName);
        }

        getBookingInfo(lastName, firstName);


        btn_confirmVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDose();
                if(getDose())
                    Toast.makeText(HandlePerson.this, "First dose, book second dose time", Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(HandlePerson.this, "Second dose, set timer for passport", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btn_cancelAppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDose();
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar
    }

    private boolean getDose() {
        if(tv_bookedDose.getText().equals("1")) return true;
        else{
            return false;
        }
    }

    private void getBookingInfo(String lastName, String firstName){

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, WebRequest.urlbase + "provider/today_appointments.php", null,
                response -> {
                    try {
                        for (int i=0;i<response.length();i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            if(jsonObject.getString("firstname").equals(firstName) && jsonObject.getString("surname").equals(lastName)){
                                tv_phone.setText(jsonObject.getString("telephone"));
                                tv_bookedDate.setText(jsonObject.getString("datetime"));
                                tv_bookedDose.setText(jsonObject.getString("dose"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            /*if no array can be found, look for jsonObject*/
            Toast.makeText(this, "No response from server, could not retrieve personal info", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
            }
        };
        queue.add(request);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
