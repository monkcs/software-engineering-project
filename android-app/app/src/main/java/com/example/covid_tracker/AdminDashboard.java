package com.example.covid_tracker;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class AdminDashboard extends AppCompatActivity {

    public BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle extras = getIntent().getExtras();

        setContentView(R.layout.activity_dashboard_admin_fragments);


        bottomNav = findViewById(R.id.admin_bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new AdminLandingFragment()).commit();


      ifBackButtonUpcommingApp();

    }







    private void ifBackButtonUpcommingApp() {

        try {
            System.out.println(getIntent().getExtras().getInt("fragment"));


            Fragment selectedFragment;
            selectedFragment = new UpcomingAppointments();



            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();

            bottomNav.setOnNavigationItemSelectedListener(navListener);
            bottomNav.setSelectedItemId(R.id.admin_upcommingAppoint);

        } catch (Exception e) {
            //do nothing
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.item2_menu2:
                System.out.println("hejsan du logga");
                loginScreen();
                return true;


            default:
                return super.onOptionsItemSelected(item);


        }



    }

    private void loginScreen() {

        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        AdminDashboard.this.finish();

    }


    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment;
                switch (item.getItemId()) {
                    case R.id.admin_start:
                        selectedFragment = new AdminLandingFragment();
                        break;

                    case R.id.admin_upcommingAppoint:
                        selectedFragment = new UpcomingAppointments();
                        break;

                    case R.id.admin_inbox:
                        selectedFragment = new inboxAdmin();
                        break;

                    case R.id.admin_statistic:
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, CameraScannerActivity.class));

        }
        else {
            Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this, Dashboard.class));

        }
    }
}
