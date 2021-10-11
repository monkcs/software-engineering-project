package com.example.covid_tracker;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class inboxAdmin extends AppCompatActivity {
    private RequestQueue queue;

    private RecyclerView rV;
    private Button boka;
    List<Admin_block> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(this);

        setContentView(R.layout.inboxadmin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        System.out.println("insidde box");

        rV = (RecyclerView) findViewById(R.id.recyclerView_inboxAdmin);



        getPendingBookings();
        setRecyclerView();



    }
    private void setRecyclerView() {

        Admin_block_Adapter Admin_block_adapter = new Admin_block_Adapter(list);
        rV.setAdapter(Admin_block_adapter);
        rV.setHasFixedSize(true);


    }

    private void getPendingBookings() {

        list = new ArrayList<>();



            //Här hämtar du hela listan med pending bookings


            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, WebRequest.urlbase + "provider/pending/",null,
                    response -> {

                        for (int i =0; i < response.length(); i++) {

                            try {

                                System.out.println("response namn: " + response.getJSONObject(i).getString("firstname"));

                                list.add(new Admin_block(response.getJSONObject(i).getString("firstname"),"123","123","123", response.getJSONObject(i).getInt("account")) );
                                System.out.println("Try fungerade");

                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println("Catch fungerade");

                            }

                        }
                        setRecyclerView();

                    }, error -> {

                    System.out.println("Error, den når inte fram");

            }
            ) {

                @Override
                public Map<String, String> getHeaders() {
                    return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
                }
            };

            queue.add(request);
        }
}
