package com.example.covid_tracker;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Admin_dosage extends AppCompatActivity {

    private RecyclerView recyclerview;
    public List<Dosage_block> list;
    public ArrayList<String> lista_spinner;
    private TextView rubrik;
    private Spinner spinner;
    private EditText edit_dosage;
    private Button addknapp;
    private RequestQueue queue;

    private String nameis_spinner;
    private String antal_doser_som_add;
    private Integer antal_convert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dosage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //byt till getactivity när det fragment
        queue = Volley.newRequestQueue(this);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerView_dosage);
        rubrik = (TextView) findViewById(R.id.lager);

        GetfromDatabase();



        edit_dosage = (EditText) findViewById(R.id.edit_amount);

        addknapp = (Button) findViewById(R.id.addknapp);
        addknapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              //  System.out.println(edit_dosage.getText());

                if(edit_dosage.length() != 0) {

                    System.out.println("hejsan");

                    nameis_spinner = spinner.getSelectedItem().toString();
                    antal_doser_som_add = edit_dosage.getText().toString();
                    antal_convert = Integer.parseInt(antal_doser_som_add);
                    System.out.println("---Add---");
                    System.out.println("Antal: " + antal_convert);
                    System.out.println(nameis_spinner);
                    edit_dosage.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    edit_dosage.setText("");
                    System.out.println("-----------");

                    addToDatabase(antal_convert, nameis_spinner);
                }
                else{

                    Toast.makeText(Admin_dosage.this, "@error_missing_value", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    private void addToDatabase(Integer antal, String namn) {

        System.out.println("in add to database before request");

        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "provider/vaccine_catalog.php",
                response -> {
                    Toast.makeText(Admin_dosage.this, "Success!", Toast.LENGTH_LONG).show();
                    GetfromDatabase();
                }, error -> {
            Toast.makeText(Admin_dosage.this, "Error", Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("selected_name", namn);
                params.put("input_value", String.valueOf(antal));
                return params;
            }
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
            }
        };
        queue.add(request);
    }

    private void add_spinner() {

        lista_spinner = new ArrayList<>();

        for(int i=0;i<list.size();i++){

            lista_spinner.add(list.get(i).getNamn());
        }

        spinner = this.findViewById(R.id.spinnerVaccine_dosage);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }


    //detta ska bytas ut mot att hämta från databasen
    //glöm inte att calla set Recyclerview
    private void GetfromDatabase() {

        list = new ArrayList<>();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, WebRequest.urlbase + "provider/vaccine_catalog.php", null,
                response -> {

                    for (int i=0;i<response.length();i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            list.add(new Dosage_block(jsonObject.getInt("quantity"), jsonObject.getString("name")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        add_spinner();
                        setRecyclerView();
                    }
                }, error -> {

            System.out.println("Error no response");
            setRecyclerView();


        }) {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
            }
        };
        queue.add(request);



    }





    private void setRecyclerView() {

        Dosage_block_adapter Dosage_block_adapter = new Dosage_block_adapter(list);
        recyclerview.setAdapter(Dosage_block_adapter);
        recyclerview.setHasFixedSize(true);
    }

}