package com.example.covid_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class age_change extends AppCompatActivity {

    private Button update, update2;
    private EditText age_change;
    private CalendarView calv;
    private String reader;
    private String dateis;
    private int updateint;

    public List<age_change_block> list;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_change);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerView_age_age_age);

        calv = (CalendarView) findViewById(R.id.calender_agechange);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dateis = sdf.format(new Date(calv.getDate()));

        getTimesforDatabase();
        setRecyclerView();


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



                if(age_change.length() != 0) {
                    reader = age_change.getText().toString();
                    updateint = Integer.parseInt(reader);

                    age_change.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    age_change.setText("");

                    System.out.println("---Update---");
                    System.out.println("detta är ålder: " + updateint);
                    System.out.println("detta är datum: " + dateis);
                }
                else{
                    //do nothing
                }

            }
        });

        update2 = (Button) findViewById(R.id.update_agechange2);
        update2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t1;
                t1 = Toast.makeText(age_change.this, "The update button takes the marked date and update the age-group on all the appointment on that day", Toast.LENGTH_LONG);
                t1.show();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem i) {
        switch (i.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, AdminDashboard.class);
                intent.putExtra("fragment", 12345);


                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(i);
        }
    }

    public void displayToast(View v){

        Toast.makeText(this, "helpis", Toast.LENGTH_LONG);
        System.out.println("Hej i toast");
    }

    private void getTimesforDatabase() {

        list = new ArrayList<>();

        list.add(new age_change_block("a", "b"+ "\n" + "at" + "\n" + "bas", "c"));

    }

    private void setRecyclerView() {

        age_change_block_Adapter adp = new age_change_block_Adapter(list);
        recyclerview.setAdapter(adp);
        recyclerview.setHasFixedSize(true);

    }
}