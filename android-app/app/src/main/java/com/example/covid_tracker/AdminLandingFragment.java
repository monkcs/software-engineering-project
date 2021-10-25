package com.example.covid_tracker;

import static com.example.covid_tracker.DigitalHealth.CAMERA_PERMISSION_CODE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.Map;

public class AdminLandingFragment extends Fragment {

    private RelativeLayout rl1, rl2, rl3, rl4;
    private View view;
    private TextView userCount;
    private RequestQueue queue;
    // how to display this in fragments?

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_landing, container, false);
        queue = Volley.newRequestQueue(getActivity());

        //setContentView(R.layout.fragment_admin_dashboard);
        rl1= (RelativeLayout) view.findViewById(R.id.Rellay1);
        /*rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uppcApoint();
            }
        });*/

        rl2 = view.findViewById(R.id.Rellay2);
        rl2.setOnClickListener(view -> planAndShedVacc());

        rl3 = view.findViewById(R.id.Rellay3);
        rl3.setOnClickListener(view -> dosage());

        rl4 = view.findViewById(R.id.Rellay4);
        rl4.setOnClickListener(view -> {

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

            }else{
                startActivity(new Intent(getActivity(), CameraScannerActivity.class));
            }

        });

        userCount = view.findViewById(R.id.adminUserCount);
        userCountRequest();
        return view;
    }

    public void dosage() {
        Intent intent = new Intent(getActivity(), Admin_dosage.class);
        startActivity(intent);
    }

    public void inboxAdmin(){
        Intent intent = new Intent(getActivity(), inboxAdmin.class);
        startActivity(intent);
    }

    public void uppcApoint(){

    }

    public void planAndShedVacc(){
        Intent intent = new Intent(getActivity(), PlanAndShedVaccs.class);
        startActivity(intent);
    }

    public void userCountRequest(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, WebRequest.urlbase + "provider/quantity.php", null,
                response -> {
                    try {
                        userCount.append(response.getString("quantity"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {
            Toast.makeText(getActivity(), R.string.incorrect_credentials, Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.Provider.username, WebRequest.Provider.password);
            }
        };

        queue.add(request);
        queue.start();
    }


}