package com.example.covid_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Boka_vaccin extends Fragment implements View.OnClickListener {

    private RequestQueue queue;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button BtnGoBack;

    RecyclerView recyclerView;
    List<Booking_block> list;

    public Boka_vaccin() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getActivity());

        View view = inflater.inflate(R.layout.fragment_boka_vaccin, container, false);

        Button buttonBook = view.findViewById(R.id.BtnBook);
        Button buttonRebook = view.findViewById(R.id.BtnRebook);
        Button buttonCancel = view.findViewById(R.id.BtnCancel);



        buttonBook.setOnClickListener(this);
        buttonRebook.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.recyclerView_bokis);


        fetch_available_appointmentd();

        return view;
    }




    void fetch_available_appointmentd() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, WebRequest.urlbase + "user/appointment/", null,
                response -> {

                   try {
                        initAppointment(response.getString("datetime"), "Dos: " + response.getString("dose"), response.getString("name"));

                       setRecyclerView();
                   } catch (JSONException e) {
                        e.printStackTrace();
                    }




                }, error -> {

            initAppointment("No Appointment", "-", "-");
            setRecyclerView();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.User.username, WebRequest.User.password);
            }
        };

        queue.add(request);
    }

    private void setRecyclerView() {

            Booking_block_Adapter Booking_block_adapter = new Booking_block_Adapter(list);
            recyclerView.setAdapter(Booking_block_adapter);
            recyclerView.setHasFixedSize(true);



    }
    private void initAppointment(String date, String dose, String location) {
        list = new ArrayList<>();
        list.add(new Booking_block(date, dose, location));
    }



    public void openBooking()
    {
        Intent intent = new Intent(getActivity(), BookingActivity.class);
        startActivity(intent);
    }

    //
    // gör denna
    //
    public void reBooking() {

        //if tid bokad
        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View CancelPopupView = getLayoutInflater().inflate(R.layout.deletepopup, null);
        Button BtnRebook = CancelPopupView.findViewById(R.id.DeleteBtn);
        BtnGoBack = CancelPopupView.findViewById(R.id.GobackBtn);

        String string = getString(R.string.rebook);
        BtnRebook.setText(string);

        dialogBuilder.setView(CancelPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        BtnRebook.setOnClickListener(view -> {
            DeleteAppointment();
            dialog.dismiss();
            openBooking();
        });
        BtnGoBack.setOnClickListener(view -> dialog.dismiss());
    }
    public void removeAppointment() {

        //if tid bokad
        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View CancelPopupView = getLayoutInflater().inflate(R.layout.deletepopup, null);
        Button btnDelete = CancelPopupView.findViewById(R.id.DeleteBtn);
        BtnGoBack = CancelPopupView.findViewById(R.id.GobackBtn);

        dialogBuilder.setView(CancelPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        btnDelete.setOnClickListener(view -> {
            DeleteAppointment();
            /*1. check vaccine product
            * 2. increment selected product*/
            dialog.dismiss();

        });
        BtnGoBack.setOnClickListener(view -> dialog.dismiss());
    }

    public void DeleteAppointment() {
        StringRequest request = new StringRequest(Request.Method.GET, WebRequest.urlbase + "user/appointment/cancel.php",
                response -> {
                    System.out.println(response);
                    Toast.makeText(getActivity(), R.string.canceled_appointment, Toast.LENGTH_LONG).show();

                }, error -> Toast.makeText(getActivity(), R.string.canceled_appointment_failed, Toast.LENGTH_LONG).show()) {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.User.username, WebRequest.User.password);
            }
        };
        queue.add(request);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.BtnBook:
                //System.out.println("button boka");
                openBooking();
                break;


            case R.id.BtnRebook:
                System.out.println("button omboka");
                reBooking();
                break;

            case R.id.BtnCancel:
                System.out.println("button avboka");
                removeAppointment();
                break;

        }
    }
}