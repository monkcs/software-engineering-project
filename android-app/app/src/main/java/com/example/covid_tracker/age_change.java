package com.example.covid_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    private Button update, update2;
    private EditText age_change;
    private CalendarView calv;
    private String reader;
    public String dateis;
    private int updateint;

    public List<Integer> listIDs;
    public List<age_change_block> list;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_change);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        queue = Volley.newRequestQueue(this);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerView_age_age_age);

        calv = (CalendarView) findViewById(R.id.calender_agechange);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dateis = sdf.format(new Date(calv.getDate()));

        getTimesforDatabase();
        setRecyclerView();


        calv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                i1 = i1 + 1;



                dateis = i2 + "/" + i1 + "/" + i;

                System.out.println("detta är dateis" + dateis);

                getTimesforDatabase();
                setRecyclerView();
            }
        });

        age_change = (EditText) findViewById(R.id.edittext_changeage);

        update = (Button) findViewById(R.id.update_agechange);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(age_change.length() != 0) {
                    reader = age_change.getText().toString();
                    updateint = Integer.parseInt(reader);

                    age_change.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    age_change.setText("");

                    System.out.println("---Update---");
                    System.out.println("detta är ålder: " + updateint);
                    System.out.println("detta är datum: " + dateis);
                    System.out.println("detta är storlek list: " + listIDs.size());

                    addToDataB(updateint);

                }
                else{
                    //do nothing
                }

            }
        });

        update2 = (Button) findViewById(R.id.update_agechange2);
        update2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t1;
                t1 = Toast.makeText(age_change.this, "The update button takes the marked date and update the age-group on all the appointment on that day", Toast.LENGTH_LONG);
                t1.show();
            }
        });


    }

    private void addToDataB(int updateint) {

      //implement

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem i) {
        switch (i.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, AdminDashboard.class);
                intent.putExtra("fragment", 12345);


                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(i);
        }
    }

    public void displayToast(View v){

        Toast.makeText(this, "helpis", Toast.LENGTH_LONG);
        System.out.println("Hej i toast");
    }

    private void getTimesforDatabase() {

        System.out.println("hej inne i gettimes");
        list = new ArrayList<>();
        System.out.println("detta är dateis inne i database" + dateis);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, WebRequest.urlbase + "provider/available_booking.php", null,
                response -> {

                String times = "";
                listIDs = new ArrayList<>();

                    System.out.println("hej inne i response");
                    for (int i = 0; i < response.length(); i++) {

                        try {

                            if(compareIfEqual(dateis, response.getJSONObject(i).getString("datetime"))){
                                System.out.println(response.getJSONObject(i));

                                JSONObject paket = response.getJSONObject(i);

                                listIDs.add(getIDpack(paket));
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

    private Integer getIDpack(JSONObject paket) {

        Integer returnis = null;
        try {
            returnis = paket.getInt("ID");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return returnis;

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