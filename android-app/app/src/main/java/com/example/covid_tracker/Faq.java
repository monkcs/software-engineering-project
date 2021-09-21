package com.example.covid_tracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Faq extends Fragment {

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_faq, container, false);
        //toppen
        // tex    button = (Button) view.findViewById(R.id.button); viktigt att det står view. för att komma åt element i fragmented



        //botten
        return view;
    }
}