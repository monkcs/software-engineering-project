package com.example.covid_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HandlePerson extends AppCompatActivity {

    private RequestQueue queue;

    TextView tv_person_id, tv_personFullName, tv_phone, tv_bookedDate, tv_bookedDose;
    Button btn_confirmVaccine, btn_cancelAppoint;
    String [] nameArray;
    String firstName, lastName;
    int person_id;
    private Integer dose;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_person);

        queue = Volley.newRequestQueue(this);

        Bundle extras = getIntent().getExtras();

        tv_personFullName = findViewById(R.id.tv_personFullName);
        tv_person_id = findViewById(R.id.tv_person_id);
        tv_phone = findViewById(R.id.tv_phone);
        tv_bookedDate = findViewById(R.id.tv_bookedDate);
        tv_bookedDose = findViewById(R.id.tv_bookedDose);

        btn_confirmVaccine = findViewById(R.id.btn_confirmVaccine);
        btn_cancelAppoint = findViewById(R.id.btn_cancelAppoint);

        /*functionality to send data between activities*/
        if (extras != null) {
            /*The key argument here must match that used in the other activity*/
            String fullName = extras.getString("personName");
            nameArray = fullName.split(",");
            lastName = nameArray[0];
            firstName = nameArray[1];

            tv_personFullName.setText(lastName + ", " + firstName);
        }

        getBookingInfo(lastName, firstName);


        btn_confirmVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                person_id = Integer.parseInt((String) tv_person_id.getText());
                if(firstDose())
                    firstDoseTaken(person_id);
                else{
                    secondDoseTaken(person_id);
                }
            }
        });

        btn_cancelAppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstDose();
                person_id = Integer.parseInt((String) tv_person_id.getText());
                RemoveAppointment(person_id);
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar
    }

    private void firstDoseTaken(Integer id) {
        dose = 1;
        update_tables(id, dose);
        Toast.makeText(HandlePerson.this, "First dose for " + id + ", book second dose time", Toast.LENGTH_SHORT).show();
        bookSecondDose(id);
    }

    private void secondDoseTaken(Integer id) {
        dose = 2;
        update_tables(id, dose);
        Toast.makeText(HandlePerson.this, "Second dose for " + id + ", set timer for passport", Toast.LENGTH_SHORT).show();

    }

    public void RemoveAppointment(Integer id) {
        //if tid bokad
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View CancelPopupView = getLayoutInflater().inflate(R.layout.deletepopup, null);
        Button btnDelete = (Button) CancelPopupView.findViewById(R.id.DeleteBtn);
        Button btnGoBack = (Button) CancelPopupView.findViewById(R.id.GobackBtn);

        dialogBuilder.setView(CancelPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelAppointment(id);
                dialog.dismiss();

            }
        });
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    private void CancelAppointment(Integer id) {
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "provider/cancel_time.php",
                response -> {
                    Toast.makeText(HandlePerson.this, "Canceled time for person with ID: " + id, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(HandlePerson.this, UpcomingAppointments.class);
                    finish();
                    startActivity(intent);

                }, error -> {
            Toast.makeText(HandlePerson.this, "Not able to cancel time", Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ID", id.toString());

                return params;
            }
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
            }
        };

        queue.add(request);
    }

    private boolean firstDose() {
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
                                tv_person_id.setText(jsonObject.getString("account"));
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
    public void update_tables(Integer id, Integer dose){
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "provider/dose_taken.php",
                response -> {
                    System.out.println(response);

                }, error -> {
            Toast.makeText(HandlePerson.this, "Failed", Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ID", id.toString());
                params.put("dose", dose.toString());

                return params;
            }
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
            }
        };

        queue.add(request);
    }
    public void bookSecondDose(Integer id){
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "provider/auto_book.php",
                response -> {
                    Toast.makeText(HandlePerson.this, "Second dose booked for person with ID: " + id, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(HandlePerson.this, UpcomingAppointments.class);
                    finish();
                    startActivity(intent);

                }, error -> {
            Toast.makeText(HandlePerson.this, "Not able to book second time", Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ID", id.toString());

                return params;
            }
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
