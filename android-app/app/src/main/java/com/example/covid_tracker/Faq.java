package com.example.covid_tracker;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Faq extends Fragment {

    private View view;
    private boolean language = false;
    RecyclerView recyclerView;
    List<FAQ_block> list;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_faq, container, false);
        //toppen
        // tex    button = (Button) view.findViewById(R.id.button); viktigt att det står view. för att komma åt element i fragmented

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_faqis);
        FloatingActionButton buttonFAQ = view.findViewById(R.id.buttonFAQ);

        setAppLocate("en");


        initQuestions();
        setRecyclerView();


        buttonFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language = !language;

                for(int i=0;i<list.size();i++){

                    list.remove(0);

                }

                if(language) {
                    FloatingActionButton buttonFAQ = view.findViewById(R.id.buttonFAQ);
                    buttonFAQ.setImageResource(R.drawable.flaggb_foreground);
                    setAppLocate("sv");

                }
                else{

                    FloatingActionButton buttonFAQ = view.findViewById(R.id.buttonFAQ);
                    buttonFAQ.setImageResource(R.drawable.flagswe_foreground);

                    setAppLocate("en");
                }

                initQuestions();
                setRecyclerView();

                System.out.println("HEJ DU KLICKA PÅ SPRÅK");
            }

        });
        //botten
        return view;
    }


    public void setAppLocate(String language){


        Resources re = getResources();
        DisplayMetrics dm = re.getDisplayMetrics();
        Configuration config = re.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            config.setLocale(new Locale(language.toLowerCase()));
        }
        else{

            config.locale = new Locale(language.toLowerCase());

        }


        re.updateConfiguration(config, dm);
    }

    private void setRecyclerView() {

        FAQ_block_Adapter faq_block_adapter = new FAQ_block_Adapter(list);
        recyclerView.setAdapter(faq_block_adapter);
        recyclerView.setHasFixedSize(true);


    }

    private void initQuestions() {

        list = new ArrayList<>();

        if(language) {
            list.add(new FAQ_block(getString(R.string.Fråga1), "In Karlstad"));
            list.add(new FAQ_block(getString(R.string.Fråga1), "SVENSKA"));
            list.add(new FAQ_block(getString(R.string.Fråga1), "In Karlstad"));
            list.add(new FAQ_block(getString(R.string.Fråga1), "SVENSKA"));
            list.add(new FAQ_block(getString(R.string.Fråga1), "In Karlstad"));
            list.add(new FAQ_block(getString(R.string.Fråga1), "SVENSKA"));
            list.add(new FAQ_block(getString(R.string.Fråga1), "In Karlstad"));
            list.add(new FAQ_block(getString(R.string.Fråga1), "SVENSKA"));
            list.add(new FAQ_block(getString(R.string.Fråga1), "In Karlstad"));
            list.add(new FAQ_block(getString(R.string.Fråga1), "SVENSKA"));
        }
        else{

            list.add(new FAQ_block(getString(R.string.Fråga1), "In Karlstad"));
            list.add(new FAQ_block(getString(R.string.Fråga1), "SVENSKA"));
            list.add(new FAQ_block(getString(R.string.Fråga1), "In Karlstad"));
            list.add(new FAQ_block(getString(R.string.Fråga1), "SVENSKA"));
            list.add(new FAQ_block(getString(R.string.Fråga1), "In Karlstad"));
            list.add(new FAQ_block(getString(R.string.Fråga1), "SVENSKA"));
            list.add(new FAQ_block(getString(R.string.Fråga1), "In Karlstad"));
            list.add(new FAQ_block(getString(R.string.Fråga1), "SVENSKA"));
            list.add(new FAQ_block(getString(R.string.Fråga1), "In Karlstad"));
            list.add(new FAQ_block(getString(R.string.Fråga1), "SVENSKA"));
        }


    }
}