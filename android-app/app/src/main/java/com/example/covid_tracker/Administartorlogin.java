package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


public class Administartorlogin extends AppCompatActivity implements View.OnClickListener {
    /*
        public Administartorlogin(){
            Spinner dropdown = findViewById(R.id.spinner1);
            String[] items = new String[]{"bästa Hammarö", "Karlstad", "2 i tabelen"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            dropdown.setAdapter(adapter);
        }

    */

    class Provider
    {
        public String name;
        public Integer id;

        public Provider(JSONObject object)
        {
            try {
                this.name = object.getString("name");
                this.id = object.getInt("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public String toString() {
            return name;
        }
    };

    private RequestQueue queue;
    private Button loginbutton, gobackbutton;
    private EditText password;
    Spinner dropdown;

    private ArrayList<Provider> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administartorlogin);

        queue = Volley.newRequestQueue(this);
        dropdown = findViewById(R.id.clinics);

        password = findViewById(R.id.adminpassword);

        getProviders();

        loginbutton= findViewById(R.id.adminloginbutton);
        gobackbutton= findViewById(R.id.gobackbutton);

        loginbutton.setOnClickListener(this);
        gobackbutton.setOnClickListener(this);
    }

    void getProviders() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, WebRequest.urlbase + "provider/", null,
                response -> {


                for (int i = 0; i < response.length(); i++)
                {
                    try {
                        items.add(new Provider(response.getJSONObject(i)));
                    }
                    catch (Exception e) { }
                }

                ArrayAdapter<Provider> adapter = new ArrayAdapter<Provider>(this, android.R.layout.simple_spinner_dropdown_item, items);
                dropdown.setAdapter(adapter);


                }, error -> {
            Toast.makeText(Administartorlogin.this, R.string.network_down, Toast.LENGTH_LONG).show();
        });

        queue.add(request);
    }
    public void login() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, WebRequest.urlbase + "provider/information.php", null,
                response -> {

                    WebRequest.Provider.username = ((Provider) dropdown.getSelectedItem()).id.toString();
                    WebRequest.Provider.password = password.getText().toString();

                    Intent intent = new Intent(this,AdminDashboard.class);
                    startActivity(intent);

                }, error -> {
            Toast.makeText(Administartorlogin.this, R.string.wrong_creeentials, Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(((Provider) dropdown.getSelectedItem()).id.toString(), password.getText().toString());
            }
        };

        queue.add(request);
    }

    public void goback() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.adminloginbutton:
                login();
                break;

            case R.id.gobackbutton:
                goback();
                break;
        }
    }
}