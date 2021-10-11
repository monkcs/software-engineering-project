package com.example.covid_tracker.User.Activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_tracker.NotificationChannelsEnabler;
import com.example.covid_tracker.R;
import com.example.covid_tracker.User.Fragment.Dashboard.Boka_vaccin;
import com.example.covid_tracker.User.Fragment.Dashboard.DigitalHealth;
import com.example.covid_tracker.User.Fragment.Dashboard.Faq;
import com.example.covid_tracker.User.Fragment.Dashboard.StatisticsMenu;
import com.example.covid_tracker.WebRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Map;


public class Dashboard extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard_fragments);

        notificationManager = NotificationManagerCompat.from(this);
        queue = Volley.newRequestQueue(this);


        //kör notice
        getDateForAppointmentNotice();


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new StatisticsMenu()).commit();

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment;
                switch (item.getItemId()) {
                    case R.id.statistic:
                        selectedFragment = new StatisticsMenu();
                        break;

                    case R.id.boka_vaccinicon:
                        selectedFragment = new Boka_vaccin();
                        break;

                    case R.id.digitalHealth:
                        selectedFragment = new DigitalHealth();
                        break;

                    case R.id.faq:
                        selectedFragment = new Faq();
                        break;

                    default:
                        selectedFragment = new StatisticsMenu();
                        break;
                }


                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            };

    public void sendOnChannel1() {

        String title = getString(R.string.app_name);
        String message = "Time to get your second dose!";
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationChannelsEnabler.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_schedual_second_dose)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(1, builder.build());

    }


    public void getDateForAppointmentNotice(){
        StringRequest request = new StringRequest(Request.Method.GET, WebRequest.urlbase + "user/appointment/new_check.php",
                response -> {

                    Log.i("gDFA", "response: " + response);

                    if (response.equals("true")){
                        sendOnChannel1();
                    }


                }, error -> Log.i("gDFA", "Något blev fel"))
        {
            @Override
            public Map<String, String> getHeaders() {
                return WebRequest.credentials(WebRequest.User.username, WebRequest.User.password);
            }
        };

        queue.add(request);
    }

}
  