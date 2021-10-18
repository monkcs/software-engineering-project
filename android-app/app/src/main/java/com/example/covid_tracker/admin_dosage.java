package com.example.covid_tracker;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class admin_dosage extends AppCompatActivity {

    private RecyclerView recyclerview;
    public List<Dosage_block> list;
    public ArrayList<String> lista_spinner;
    private TextView rubrik;
    private Spinner spinner;
    private EditText edit_dosage;
    private Button addknapp;

    private int place_spinner;
    private String antal_doser_som_add;
    private Integer antal_convert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dosage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerview = (RecyclerView) findViewById(R.id.recyclerView_dosage);
        rubrik = (TextView) findViewById(R.id.lager);

        GetQuestions();
        setRecyclerView();

        add_spinner();


        spinner = this.findViewById(R.id.spinnerVaccine_dosage);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        edit_dosage = (EditText) findViewById(R.id.edit_amount);

        addknapp = (Button) findViewById(R.id.addknapp);
        addknapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                place_spinner = spinner.getSelectedItemPosition();
                antal_doser_som_add = edit_dosage.getText().toString();
                antal_convert = Integer.parseInt(antal_doser_som_add);
                System.out.println("---Add---");
                System.out.println("Position: " + place_spinner);
                System.out.println("Antal: " + antal_convert);
                edit_dosage.onEditorAction(EditorInfo.IME_ACTION_DONE);
                edit_dosage.setText("");
                System.out.println("-----------");

                //uppdatera listan med getQuestions (update kommando)


            }
        });

    }

    private void add_spinner() {

        lista_spinner = new ArrayList<>();

        for(int i=0;i<list.size();i++){

            lista_spinner.add(list.get(i).getNamn());

        }

    }


    //detta ska bytas ut mot att hämta från databasen
    //glöm inte att calla set Recyclerview
    private void GetQuestions() {

        list = new ArrayList<>();
        list.add(new Dosage_block(32, "Calle"));
        list.add(new Dosage_block(322, "Moderna"));
    }




    private void setRecyclerView() {

        Dosage_block_adapter Dosage_block_adapter = new Dosage_block_adapter(list);
        recyclerview.setAdapter(Dosage_block_adapter);
        recyclerview.setHasFixedSize(true);
    }

}