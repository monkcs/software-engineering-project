package com.example.covid_tracker;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class HandlePerson extends AppCompatActivity {

    TextView tv_personFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_person);

        Bundle extras = getIntent().getExtras();

        tv_personFullName = findViewById(R.id.tv_personFullName);

        if (extras != null) {
            String fullName = extras.getString("key");
            tv_personFullName.setText(fullName);
            //The key argument here must match that used in the other activity
        }





        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
