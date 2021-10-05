package com.example.covid_tracker;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationChannelsEnabler extends Application {
        public static final String CHANNEL_1_ID = "Notice second dose";
        public static final String CHANNEL_2_ID = "Got response from administrator";


    @Override
        public void onCreate() {
            super.onCreate();

            createNotificationChannel();
        }

        private void createNotificationChannel() {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name_1 = getString(R.string.channel_name_1);
                String description_1 = getString(R.string.channel_description_1);
                int importance_1 = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel_1 = new NotificationChannel(CHANNEL_1_ID, name_1, importance_1);
                channel_1.setDescription(description_1);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this


                CharSequence name_2 = getString(R.string.channel_name_2);
                String description_2 = getString(R.string.channel_description_2);
                int importance_2 = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel_2 = new NotificationChannel(CHANNEL_2_ID, name_2, importance_2);
                channel_2.setDescription(description_2);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this


                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel_1);
                notificationManager.createNotificationChannel(channel_2);
            }
        }
    }

