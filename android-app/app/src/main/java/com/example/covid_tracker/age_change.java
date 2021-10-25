package com.example.covid_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class age_change extends AppCompatActivity {

    private RequestQueue queue;
    private EditText age_change;
    private String reader;
    public String dateis;
    private int updateint;

    public List<age_change_block> list;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_change);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        queue = Volley.newRequestQueue(this);

        recyclerview = findViewById(R.id.recyclerView_age_age_age);

        CalendarView calv = findViewById(R.id.calender_agechange);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dateis = sdf.format(new Date(calv.getDate()));

        getTimesforDatabase();
        setRecyclerView();


        calv.setOnDateChangeListener((calendarView, i, i1, i2) -> {

            i1 = i1 + 1;



            dateis = i2 + "/" + i1 + "/" + i;

            System.out.println("detta är dateis" + dateis);

            getTimesforDatabase();
            setRecyclerView();
        });

        age_change = findViewById(R.id.edittext_changeage);

        Button update = findViewById(R.id.update_agechange);
        update.setOnClickListener(view -> {



            if(age_change.length() != 0) {
                reader = age_change.getText().toString();
                updateint = Integer.parseInt(reader);

                age_change.onEditorAction(EditorInfo.IME_ACTION_DONE);
                age_change.setText("");

                System.out.println("---Update---");
                System.out.println("detta är ålder: " + updateint);
                System.out.println("detta är datum: " + dateis);
            }

        });

        Button update2 = findViewById(R.id.update_agechange2);
        update2.setOnClickListener(view -> {
            Toast t1;
            t1 = Toast.makeText(age_change.this, "The update button takes the marked date and update the age-group on all the appointment on that day", Toast.LENGTH_LONG);
            t1.show();
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem i) {
        if (i.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, AdminDashboard.class);
            intent.putExtra("fragment", 12345);


            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(i);
    }

    private void getTimesforDatabase() {

        System.out.println("hej inne i gettimes");
        list = new ArrayList<>();
        System.out.println("detta är dateis inne i database" + dateis);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, WebRequest.urlbase + "provider/available_booking.php", null,
                response -> {

                String times = "";

                    System.out.println("hej inne i response");
                    for (int i = 0; i < response.length(); i++) {

                        try {

                            if(compareIfEqual(dateis, response.getJSONObject(i).getString("datetime"))){
                                System.out.println(response.getJSONObject(i));

                                JSONObject paket = response.getJSONObject(i);

                                times = times + "\n" + "Tid: " + getTime(paket) + ", Minimum age: " + getMinAge(paket) + "\n";

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                            System.out.println("ERRPR CATCH");
                        }


                    }

                    list.add(new age_change_block(dateis, times));
                    System.out.println("hej inne i recview");
                    setRecyclerView();
                }, error -> {
        }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
            }
        };
        System.out.println("hej inne i queueadd");
        queue.add(request);



    }

    private String getMinAge(JSONObject paket) {

        String returnis = null;
        try {
            returnis = paket.getString("minimum_age");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return returnis;
    }

    private String getTime(JSONObject paket) {

        String returnis = null;
        try {
            returnis = paket.getString("datetime");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //hämta char 11 till 18
        returnis = returnis.substring(11,19);

        return returnis;
    }

    private boolean compareIfEqual(String dateis, String s) {

     //   System.out.println(dateis.charAt(0));

        boolean flagga = true;

        if(dateis.charAt(0) != s.charAt(8) || dateis.charAt(1) != s.charAt(9)){

            //kollar om dagen stämmer överrens
            flagga = false;

        }

        if(dateis.charAt(3) != s.charAt(5) || dateis.charAt(4) != s.charAt(6)){

            //kollar om månaden är samma
            flagga = false;

        }

        if(dateis.charAt(8) != s.charAt(2) || dateis.charAt(9) != s.charAt(3)){

            //kollar om året är samma
            flagga = false;

        }

        return flagga;
    }

    private void setRecyclerView() {

        age_change_block_Adapter adp = new age_change_block_Adapter(list);
        recyclerview.setAdapter(adp);
        recyclerview.setHasFixedSize(true);

    }
}