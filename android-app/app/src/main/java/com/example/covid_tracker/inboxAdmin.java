package com.example.covid_tracker;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class inboxAdmin extends Fragment {
    List<Admin_block> list;
    private RequestQueue queue;
    private View view;
    private Context context;
    //private RecyclerView rV;
    private Button boka;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(getActivity());

        view = inflater.inflate(R.layout.fragment_inboxadmin, container, false);
        context = getActivity();
        //setContentView(R.layout.fragment_inboxadmin);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        System.out.println("insidde box");

        //rV = (RecyclerView) findViewById(R.id.recyclerView_inboxAdmin);


        getPendingBookings();
        //setRecyclerView();

        return view;
    }
/*
    private void setRecyclerView() {

        Admin_block_Adapter Admin_block_adapter = new Admin_block_Adapter(list);
        rV.setAdapter(Admin_block_adapter);
        rV.setHasFixedSize(true);


    }*/


    private void getPendingBookings() {

        list = new ArrayList<>();


        //Här hämtar du hela listan med pending bookings


        JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, WebRequest.urlbase + "provider/pending/index.php", null,
                response -> {

                    for (int i = 0; i < response.length(); i++) {

                        try {

                            System.out.println("response namn: " + response.getJSONObject(i).getString("firstname"));

                            list.add(new Admin_block(response.getJSONObject(i).getString("firstname"), getQuestions(i), response.getJSONObject(i).getString("telephone"), response.getJSONObject(i).getString("datetime"), response.getJSONObject(i).getInt("account")));
                            System.out.println("Try fungerade");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("Catch fungerade");

                        }

                    }
                    //setRecyclerView();

                }, error -> {

            System.out.println("Error, den når inte fram 33214412");

        }
        ) {

            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
            }
        };

        queue.add(request2);


    }


    //fixa denna
    //DATABASEN KOMMUNICATION
    public String getQuestions(int i) {


        // här hämtar du alla frågor från databasen gällande användar ID (i), detta innebär att du hämtar alla frågar o en sträng


        return "You answered yes on question 3";


    }


}

