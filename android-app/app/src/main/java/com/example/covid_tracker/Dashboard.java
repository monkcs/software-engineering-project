package com.example.covid_tracker;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard_fragments);

        notificationManager = NotificationManagerCompat.from(this);
        sendOnChannel1();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new StatisticsMenu()).commit();


    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
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
        String message = getString(R.string.noticeseconddose);

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationGenerate.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_schedual_second_dose)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(1, builder.build());

    }





}
  