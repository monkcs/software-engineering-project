package com.example.covid_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    private Spinner spinner;
    private EditText edit_dosage;
    private RequestQueue queue;

    private String nameis_spinner;
    private String antal_doser_som_add;
    private Integer antal_convert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dosage);

        //Adds "Go-Back" button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //For Database Request
        queue = Volley.newRequestQueue(this);

        recyclerview = findViewById(R.id.recyclerView_dosage);

        GetfromDatabase();

        edit_dosage = findViewById(R.id.edit_amount);


        //Button and it´s actionlistener to add quantity
        Button addknapp = findViewById(R.id.addknapp);
        addknapp.setOnClickListener(view -> {

            if(edit_dosage.length() != 0) {

                String selected_id = "";

                nameis_spinner = spinner.getSelectedItem().toString();
                antal_doser_som_add = edit_dosage.getText().toString();
                antal_convert = Integer.parseInt(antal_doser_som_add);

                /*get the id for this vaccine*/
                for(int i = 0; i < list.size(); i++)
                {
                    if(list.get(i).getNamn().equals(nameis_spinner))
                        selected_id = list.get(i).getId();
                }
                edit_dosage.onEditorAction(EditorInfo.IME_ACTION_DONE);
                edit_dosage.setText("");

                addToDatabase(antal_convert, selected_id);
            }
            else{
                Toast.makeText(Admin_dosage.this, getString(R.string.error_missing_value), Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        if ( id == R.id.item2_menu2){
            loginScreen();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loginScreen() {

        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);


    }

    private void addToDatabase(Integer antal, String id) {
        StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "provider/vaccine_catalog.php",
                response -> {
                    Toast.makeText(Admin_dosage.this, getString(R.string.success), Toast.LENGTH_LONG).show();
                    GetfromDatabase();
                }, error -> Toast.makeText(Admin_dosage.this, getString(R.string.error_msg), Toast.LENGTH_LONG).show()) {
            @Override
            public Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<>();
                params.put("selected_id", id);
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void GetfromDatabase() {

        list = new ArrayList<>();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, WebRequest.urlbase + "provider/vaccine_catalog.php", null,
                response -> {

                    for (int i=0;i<response.length();i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            list.add(new Dosage_block(jsonObject.getString("id"), jsonObject.getInt("quantity"), jsonObject.getString("name")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        add_spinner();
                        setRecyclerView();
                    }
                }, error -> {
            Toast.makeText(Admin_dosage.this, getString(R.string.error_msg), Toast.LENGTH_LONG).show();
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