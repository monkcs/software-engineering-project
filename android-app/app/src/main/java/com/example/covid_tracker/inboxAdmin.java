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


            list.add(new Admin_block("hej", "hej", "hej"));
            list.add(new Admin_block("kayn", "rakan", "main"));
        list.add(new Admin_block("kayn", "rakan", "main"));
        list.add(new Admin_block("kayn", "rakan", "main"));





    }
}
