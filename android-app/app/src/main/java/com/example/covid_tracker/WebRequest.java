package com.example.covid_tracker;


import android.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class WebRequest {

    public static String username = "";
    public static String password = "";

    public static final String urlbase = "https://hex.cse.kau.se/~charhabo100/vaccine-tracker/";

    public static Map<String, String> credentials(final String username, final String password) {
        HashMap<String, String> parameters = new HashMap<String, String>();

        String login = String.format("%s:%s", username, password);
        String authorization = "Basic " + Base64.encodeToString(login.getBytes(), Base64.DEFAULT);
        parameters.put("Authorization", authorization);

        return parameters;
    }
}