package com.example.covid_tracker;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
            person_id = Integer.parseInt(extras.getString("personID"));
            nameArray = fullName.split(",");
            lastName = nameArray[0];
            firstName = nameArray[1];

            tv_personFullName.setText(lastName + ", " + firstName);
        }

        getBookingInfo(person_id);

        /*IDEA:
        * get ID from upcomming appoint instead of names (two people could have the same name)
        * use ID to call getBookingInfo when tables are updated */


        btn_confirmVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //person_id = Integer.parseInt((String) tv_person_id.getText());
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
                if(tv_person_id.getText().length() > 0) {
                    person_id = Integer.parseInt((String) tv_person_id.getText());
                    RemoveAppointment(person_id);
                }
                else{
                    Toast.makeText(HandlePerson.this, "Error", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar
    }

    private void firstDoseTaken(Integer id) {
        dose = 1;
        update_tables(id, dose);
        bookSecondDose(id);
    }

    private void secondDoseTaken(Integer id) {
        dose = 2;
        update_tables(id, dose);
        tv_bookedDate.append(" (CONFIRMED)");
        Toast.makeText(HandlePerson.this, "Second dose for " + id + ", set timer for passport", Toast.LENGTH_SHORT).show();
        /*set passport timer functionality*/
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
                //finish();
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
                    tv_bookedDate.append(" (CANCELLED)");
                    //finish();
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

    private void getBookingInfo(Integer id){

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, WebRequest.urlbase + "provider/today_appointments.php", null,
                response -> {
                    try {
                        for (int i=0;i<response.length();i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            if(String.valueOf(id).equals(jsonObject.getString("account"))){
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
                    System.out.println("IN BOOKING DOSE 2:");
                    System.out.println(response);
                    Toast.makeText(HandlePerson.this, "Second dose booked for person with ID: " + id, Toast.LENGTH_LONG).show();
                    getBookingInfo(id);

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
