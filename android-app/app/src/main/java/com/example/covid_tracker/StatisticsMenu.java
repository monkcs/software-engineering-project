package com.example.covid_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StatisticsMenu extends AppCompatActivity {

    private Button btn_vacc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_menu);

        btn_vacc = (Button) findViewById(R.id.btn_vacc);

        btn_vacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStatVacc();
            }
        });

    }

    public void openStatVacc(){
        Intent intent = new Intent(this, StatisticsVacc.class);
        startActivity(intent);
    }

}
