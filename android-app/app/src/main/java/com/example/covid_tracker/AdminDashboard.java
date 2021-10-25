package com.example.covid_tracker;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class AdminDashboard extends AppCompatActivity {

    boolean extra_info = false;
    String currFragment;
    RelativeLayout buttonis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        setContentView(R.layout.activity_dashboard_admin_fragments);


        BottomNavigationView bottomNav = findViewById(R.id.admin_bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new AdminLandingFragment()).commit();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment;
                switch (item.getItemId()) {
                    case R.id.admin_statistic:
                        selectedFragment = new AdminLandingFragment();
                        break;

                    case R.id.admin_upcommingAppoint:
                        selectedFragment = new UpcomingAppointments();
                        break;

                    case R.id.admin_inbox:
                        selectedFragment = new inboxAdmin();
                        break;

                    case R.id.admin_settings:
                        selectedFragment = new StatisticsMenu();
                        break;

                    default:
                        selectedFragment = new AdminLandingFragment();
                        break;
                }


                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            };
}
