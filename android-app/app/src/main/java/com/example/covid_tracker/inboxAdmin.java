package com.example.covid_tracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class inboxAdmin extends AppCompatActivity {


    private RecyclerView rV;
    List<Admin_block> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        list.add(new Admin_block("Amanda", "svarade ja på utomlands", "123456789", "20 oktober 2021"));
        list.add(new Admin_block("Axel", "svarade ja på utomlands", "123456789", "20 oktober 2021"));
        list.add(new Admin_block("Diego", "svarade ja på utomlands", "123456789", "20 oktober 2021"));
        list.add(new Admin_block("Carl", "vaccinerat sig inom kort tidspan innan", "123456789", "20 oktober 2021"));
        list.add(new Admin_block("Lukas", "vaccinerat sig inom kort tidspan innan", "123456789", "20 oktober 2021"));
        list.add(new Admin_block("Charlie", "vaccinerat sig inom kort tidspan innan", "123456789", "20 oktober 2021"));





    }
}
