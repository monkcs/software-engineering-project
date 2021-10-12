package com.example.covid_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AdminLandingFragment extends Fragment {

    private RelativeLayout rl1, rl2, rl3;
    private View view;
    //private TextView userCount = findViewById(R.id.adminUserCount);
    // how to display this in fragments?

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_landing, container, false);
        //setContentView(R.layout.fragment_admin_dashboard);
        rl1= (RelativeLayout) view.findViewById(R.id.Rellay1);
        /*rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uppcApoint();
            }
        });*/

        rl2 = (RelativeLayout) view.findViewById(R.id.Rellay2);
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planAndShedVacc();
            }
        });

        rl3 = (RelativeLayout) view.findViewById(R.id.Rellay3);
        /*rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inboxAdmin();
            }
        });*/

        return view;
    }

    public void inboxAdmin(){
        Intent intent = new Intent(getActivity(), inboxAdmin.class);
        startActivity(intent);
    }

    public void uppcApoint(){
        Intent intent = new Intent(getActivity(), UpcomingAppointments.class);
        startActivity(intent);
    }

    public void planAndShedVacc(){
        Intent intent = new Intent(getActivity(), PlanAndShedVaccs.class);
        startActivity(intent);
    }
/*
    void userCountRequest(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, WebRequest.urlbase + "provider/quantity.php", null,
                response -> {
                    try {
                        userCount.append(response.getString("quantity"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {
            Toast.makeText(this, R.string.wrong_creeentials, Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
            }
        };

        queue.add(request);
        queue.start();
    }
*/

}