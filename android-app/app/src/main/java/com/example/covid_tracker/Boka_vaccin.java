package com.example.covid_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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

    private Button buttonBook, buttonRebook, buttonCancel;
    private View view;
    private RequestQueue queue;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button BtnDelete, BtnGoBack;

    RecyclerView recyclerView;
    List<Booking_block> list;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getActivity());

        view  = inflater.inflate(R.layout.fragment_boka_vaccin, container, false);
        GreedPerson();

        buttonBook = (Button) view.findViewById(R.id.BtnBook);
        buttonRebook = (Button) view.findViewById(R.id.BtnRebook);
        buttonCancel = (Button) view.findViewById(R.id.BtnCancel);

        buttonBook.setOnClickListener(this);
        buttonRebook.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_bokis);


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


    private void GreedPerson() {

        TextView textView = view.findViewById(R.id.greeding);
        textView.setText("Hello " + getPerson());


    }

    //
    //Hämta person från databas!!!
    //
    private String getPerson() {

        return "Person string blalblalbalb";

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
        Button BtnRebook = (Button) CancelPopupView.findViewById(R.id.DeleteBtn);
        BtnGoBack = (Button) CancelPopupView.findViewById(R.id.GobackBtn);

        String string = getString(R.string.rebook);
        BtnRebook.setText(string);

        dialogBuilder.setView(CancelPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        BtnRebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteAppointment();
                dialog.dismiss();
                openBooking();
            }
        });
        BtnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    public void removeAppointment() {

        //if tid bokad
        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View CancelPopupView = getLayoutInflater().inflate(R.layout.deletepopup, null);
        BtnDelete = (Button) CancelPopupView.findViewById(R.id.DeleteBtn);
        BtnGoBack = (Button) CancelPopupView.findViewById(R.id.GobackBtn);

        dialogBuilder.setView(CancelPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteAppointment();
                dialog.dismiss();

            }
        });
        BtnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void DeleteAppointment() {
        StringRequest request = new StringRequest(Request.Method.GET, WebRequest.urlbase + "user/appointment/cancel.php",
                response -> {
                    System.out.println("Tog bort bokade tid");
                    Toast.makeText(getActivity(), R.string.time_canceled, Toast.LENGTH_LONG).show();

                }, error -> {
            Toast.makeText(getActivity(), R.string.TimeNotCanceled, Toast.LENGTH_LONG).show();
        }) {
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