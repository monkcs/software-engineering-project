package com.example.covid_tracker;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class inboxAdmin extends Fragment {
    List<Admin_block> list;
    private RequestQueue queue;
    private View view;
    private Context context;
    private RecyclerView recyclerview;
    private Button boka;
    private ArrayList<Questions> questions;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(getActivity());

        questions = new ArrayList<>();

        view = inflater.inflate(R.layout.fragment_inboxadmin, container, false);
        context = getActivity();
        //setContentView(R.layout.fragment_inboxadmin);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerView_inboxAdmin);


        getQuestions();

        return view;
    }

    private void setRecyclerView() {

        Admin_block_Adapter Admin_block_adapter = new Admin_block_Adapter(list);
        recyclerview.setAdapter(Admin_block_adapter);
        recyclerview.setHasFixedSize(true);
    }

    String appointment_question(int available)
    {
        for (Questions question: questions)
        {
            if(question.id == available)
            {
                return question.questions;
            }
        }
        return "";
    }

    private void getPendingBookings() {

        list = new ArrayList<>();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, WebRequest.urlbase + "provider/pending/", null,
                response -> {

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            String test = appointment_question(response.getJSONObject(i).getInt("available"));
                            list.add(new Admin_block(response.getJSONObject(i), appointment_question(response.getJSONObject(i).getInt("available"))));
                            String test2 = "";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    setRecyclerView();
                }, error -> {
        }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
            }
        };

        queue.add(request);
    }

    int exists(int appointment) {
        for (int index = 0; index < questions.size(); index++) {
            if (questions.get(index).id == appointment) {
                return index;
            }
        }

        return -1;
    }

    void getQuestions() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, WebRequest.urlbase + "provider/pending/information.php", null,
                response -> {

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            int place = exists(response.getJSONObject(i).getInt("appointment"));

                            if (place != -1) {
                                questions.get(place).questions += "\n•" + response.getJSONObject(i).getString("text");
                            } else {
                                questions.add(new Questions(response.getJSONObject(i).getInt("appointment"), "•" + response.getJSONObject(i).getString("text")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    getPendingBookings();
                }, error -> {
            String t = "";
        }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
            }
        };

        queue.add(request);

    }

    class Questions {
        public int id;
        public String questions;

        public Questions(int id, String questions) {
            this.id = id;
            this.questions = questions;
        }
    }
}


