package com.example.covid_tracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AgePrioritizations extends AppCompatActivity {

    private EditText age_input;
    private EditText date_input;


    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_prioritizations);

        queue = Volley.newRequestQueue(this);

        age_input = findViewById(R.id.age_input);
        date_input = findViewById(R.id.date_input);

        Button add = findViewById(R.id.add);
        add.setOnClickListener((view -> {

            StringRequest request = new StringRequest(Request.Method.POST, WebRequest.urlbase + "provider/prioritization/create.php",
                    response -> {
                        // update table
                    }, error -> {
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
                }

                @Override
                public Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("not_younger", age_input.getText().toString());
                    params.put("forthcoming", date_input.getText().toString());

                    return params;
                }
            };
            queue.add(request);
        }));
    }
}