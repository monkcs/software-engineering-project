package com.example.covid_tracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Faq extends Fragment {

    private View view;
    RecyclerView recyclerView;
    List<FAQ_block> list;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_faq, container, false);
        //toppen
        // tex    button = (Button) view.findViewById(R.id.button); viktigt att det står view. för att komma åt element i fragmented

        view = inflater.inflate(R.layout.fragment_faq, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_faqis);


        initQuestions();
        setRecyclerView();

        //botten
        return view;
    }

    private void setRecyclerView() {

        FAQ_block_Adapter faq_block_adapter = new FAQ_block_Adapter(list);
        recyclerView.setAdapter(faq_block_adapter);
        recyclerView.setHasFixedSize(true);


    }

    private void initQuestions() {

        list = new ArrayList<>();

        list.add(new FAQ_block("Calle", "balle"));
        list.add(new FAQ_block("Har du ätit idag?", "ja det har jag"));

    }
}