package com.example.covid_tracker.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.example.covid_tracker.Login;
import com.example.covid_tracker.R;
import com.example.covid_tracker.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookingStep2Fragment extends Fragment {
    private Spinner spinner;
    private RequestQueue queue;
    private ArrayList<Vaccines> vaccine = new ArrayList<>();
    private int pos;

    public BookingStep2Fragment() {
        // required empty public constructor.
    }
    static BookingStep2Fragment instance;
    public static BookingStep2Fragment getInstance(){
        if(instance == null)
            instance = new BookingStep2Fragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        queue = Volley.newRequestQueue(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookingstep2, container, false);
        getVaccine();


        return view;
    }
    class Vaccines
    {
        public Vaccines(int id, String name)
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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.item2_menu2:
                System.out.println("hejsan du loggades");
                loginScreen();
                return true;


            default:
                return super.onOptionsItemSelected(item);


        }



    }

    private void loginScreen() {

        Intent intent = new Intent(getActivity(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    private void getVaccine() {
        ArrayList<String> name_vaccine = new ArrayList<>();
        name_vaccine.add(getString(R.string.select_vaccine_type));
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, WebRequest.urlbase + "provider/vaccines.php", null,
                response -> {

                    try {

                        for (int i=0;i<response.length();i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Vaccines temporary= new Vaccines(jsonObject.getInt("id"), jsonObject.getString("name"));
                            vaccine.add(temporary);
                            name_vaccine.add(jsonObject.getString("name"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    spinner = getActivity().findViewById(R.id.spinnerVaccine);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, name_vaccine);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            SharedPreferences.Editor edit = getActivity().getSharedPreferences("Booking", Context.MODE_PRIVATE).edit();

                            if(position>0) {
                                System.out.println(position);
                                pos = position;
                                pos = pos - 1;
                                int ID = vaccine.get(pos).id;
                                edit.putInt("vaccine_ID", ID);

                            }
                            else{
                                edit.putInt("vaccine_ID", -1);
                            }
                            edit.apply();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // your code here
                        }
                    });

                }, error -> {
            Toast.makeText(getActivity(), getString(R.string.no_vaccine_available), Toast.LENGTH_LONG).show();
        });
        queue.add(request);
    }

}
