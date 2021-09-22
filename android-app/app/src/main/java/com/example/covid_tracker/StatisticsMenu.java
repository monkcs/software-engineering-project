package com.example.covid_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class StatisticsMenu extends Fragment {

    private Button btn_vacc, btn_cov;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_statistics_menu, container, false);

        btn_vacc = (Button) view.findViewById(R.id.btn_vacc);
        btn_cov = (Button) view.findViewById(R.id.btn_cov);

        btn_vacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStatVacc();
            }
        });

        btn_cov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStatCov();
            }
        });
        
        return view;
    }

    public void openStatVacc(){
        Intent intent = new Intent(getActivity(), StatisticsVacc.class);
        startActivity(intent);
    }

    public void openStatCov(){
        Intent intent = new Intent(this, StatisticsCovid.class);
        startActivity(intent);
    }

}
