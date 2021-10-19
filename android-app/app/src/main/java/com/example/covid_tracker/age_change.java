package com.example.covid_tracker;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class age_change extends AppCompatActivity {

    private Button update;
    private EditText age_change;
    private CalendarView calv;
    private String reader;
    private String dateis;
    private int updateint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_change);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


        calv = (CalendarView) findViewById(R.id.calender_agechange);
        calv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                i1 = i1 + 1;

                dateis = sdf.format(new Date(calv.getDate()));

            }
        });

        age_change = (EditText) findViewById(R.id.edittext_changeage);

        update = (Button) findViewById(R.id.update_agechange);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                reader = age_change.getText().toString();
                updateint = Integer.parseInt(reader);

                age_change.onEditorAction(EditorInfo.IME_ACTION_DONE);
                age_change.setText("");

                System.out.println("---Update---");
                System.out.println("detta är ålder: " + updateint);
                System.out.println("detta är datum: " + dateis);


            }
        });




    }
}