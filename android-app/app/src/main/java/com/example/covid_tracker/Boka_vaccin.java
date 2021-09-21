package com.example.covid_tracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Boka_vaccin extends Fragment implements View.OnClickListener {

    private Button button1boka, button2omboka, button3avboka;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_boka_vaccin, container, false);
        GreedPerson();

        button1boka = (Button) view.findViewById(R.id.button11);
        button2omboka = (Button) view.findViewById(R.id.button22);
        button3avboka = (Button) view.findViewById(R.id.button33);

        button1boka.setOnClickListener(this);
        button2omboka.setOnClickListener(this);
        button3avboka.setOnClickListener(this);


        return view;

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
        Intent intent = new Intent(getActivity(), Boka_vaccin2.class);
        startActivity(intent);
    }

    //
    // gör denna
    //
    public void removeAppointment() {

        //if tid bokad

        System.out.println("Tog bort bokade tid");

        // else
        // print "finns ingen aktuell tid

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.button11:
                //System.out.println("button boka");
                openBooking();
                break;


            case R.id.button22:
                System.out.println("button omboka");
                break;

            case R.id.button33:
                System.out.println("button avboka");
                removeAppointment();
                break;

        }
    }
}