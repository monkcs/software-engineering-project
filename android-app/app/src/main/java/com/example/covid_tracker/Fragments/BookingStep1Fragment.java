package com.example.covid_tracker.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_tracker.R;
import com.example.covid_tracker.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


public class BookingStep1Fragment extends Fragment {
    private ArrayList<Provider> clinics = new ArrayList<>();
    private RequestQueue queue;
    private Spinner spinner;
    private int pos;
    public BookingStep1Fragment() {
        // required empty public constructor.
    }
    static BookingStep1Fragment instance;
    public static BookingStep1Fragment getInstance(){
        if(instance == null)
            instance = new BookingStep1Fragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getClinics();
        View initview = inflater.inflate(R.layout.fragment_bookingstep1, container, false);
        getClinics();
        return initview;
        
    }

    class Provider
    {
        public Provider(int id, String name)
        {
            this.id = id;
            this.name = name;
        };
        public final int id;
        public final String name;

        public String toString()
        {
            return name;
        }
    }

    private void getClinics(){
        ArrayList<String> name_clinic = new ArrayList<>();
        name_clinic.add(getString(R.string.select_healthcare_provider));

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, WebRequest.urlbase + "provider/", null,
                response -> {

                    try {

                        for (int i=0;i<response.length();i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Provider temporary= new Provider(jsonObject.getInt("id"), jsonObject.getString("name"));
                            clinics.add(temporary);
                            name_clinic.add(jsonObject.getString("name"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    spinner = getActivity().findViewById(R.id.spinner);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, name_clinic);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    String text = spinner.getSelectedItem().toString();
                    System.out.println(text);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            SharedPreferences.Editor edit = getActivity().getSharedPreferences("Booking", Context.MODE_PRIVATE).edit();
                            if(position>0)
                            {
                                System.out.println(position);
                                pos = position;
                                pos = pos - 1;
                                int ID = clinics.get(pos).id;
                                edit.putInt("clinic_ID", ID);
                            }
                            else{
                                edit.putInt("clinic_ID", 0);

                            }
                            edit.apply();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // your code here
                        }
                    });


                }, error -> {
            Toast.makeText(getActivity(), getString(R.string.no_healthcare_provider), Toast.LENGTH_LONG).show();
        });

        queue.add(request);
    }



}
