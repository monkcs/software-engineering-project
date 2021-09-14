package com.example.covid_tracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StatisticsVacc extends AppCompatActivity {
    Button btn_searchRegion;
    TextView tv_region_name, tv_dist_stats, tv_admin_stats, tv_adminOneDose, tv_adminOneDosePerc, tv_adminTwoDose, tv_adminTwoDosePerc;

    String [] regions = {"Blekinge", "Dalarna", "Gotland", "Gävleborg", "Halland", "Jämtland/Härjedalen", "Jönköping", "Kalmar", "Kronoberg",
                        "Norrbotten", "Skåne", "Stockholm", "Södermanland", "Uppsala", "Värmland", "Västerbotten", "Västernorrland", "Västmanland",
                        "Västra Götaland", "Örebro", "Östergötland"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stat_vaccinations);

        btn_searchRegion = findViewById(R.id.btn_searchRegion);
        tv_region_name = findViewById(R.id.tv_region_name);
        tv_dist_stats = findViewById(R.id.tv_dist_stats);
        tv_admin_stats = findViewById(R.id.tv_admin_stats);
        tv_adminOneDose = findViewById(R.id.tv_admin_oneDose);
        tv_adminOneDosePerc = findViewById(R.id.tv_admin_oneDosePerc);
        tv_adminTwoDose = findViewById(R.id.tv_admin_twoDose);
        tv_adminTwoDosePerc = findViewById(R.id.tv_admin_twoDosePerc);

        String admin_url = "https://www.folkhalsomyndigheten.se/folkhalsorapportering-statistik/statistikdatabaser-och-visualisering/vaccinationsstatistik/statistik-for-vaccination-mot-covid-19/";
        String dist_url = "https://www.folkhalsomyndigheten.se/smittskydd-beredskap/utbrott/aktuella-utbrott/covid-19/statistik-och-analyser/leveranser-av-vaccin/";

        try {
            readVaccineData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GetTotalDistDoses taskGetDistDoses_beforeClick = new GetTotalDistDoses();
        taskGetDistDoses_beforeClick.execute(dist_url, "county", "Totalt i Sverige");

        GetTotalAdminDoses taskGetAdminDoses_beforeClick = new GetTotalAdminDoses();
        taskGetAdminDoses_beforeClick.execute(admin_url);

        GetAdminOneDoses taskGetAdminOneDoses = new GetAdminOneDoses();
        taskGetAdminOneDoses.execute(admin_url);

        GetAdminTwoDoses taskGetAdminTwoDoses = new GetAdminTwoDoses();
        taskGetAdminTwoDoses.execute(admin_url);

        /* source: https://stackoverflow.com/questions/31052436/android-edittext-with-drop-down-list */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, regions);
        //Find TextView control
        AutoCompleteTextView actv_searchRegion = (AutoCompleteTextView) findViewById(R.id.actv_searchRegion);
        //Set the number of characters the user must type before the drop down list is shown
        actv_searchRegion.setThreshold(1);
        //Set the adapter
        actv_searchRegion.setAdapter(adapter);

        btn_searchRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*get input string from user*/
                int oneDose = 0, twoDose = 0, totalDoses = 0, pop = 0;

                String region_input;
                region_input = actv_searchRegion.getText().toString();

                GetTotalDistDoses taskGetDoses_onClick = new GetTotalDistDoses();
                GetAdminOneDoses taskGetAdminOneDoses_onClick = new GetAdminOneDoses();
                GetAdminTwoDoses taskGetAdminTwoDoses_onClick = new GetAdminTwoDoses();
                GetTotalAdminDoses taskGetAdminDoses_onClick = new GetTotalAdminDoses();


                if(region_input.length() == 0)
                {
                    tv_region_name.setText("Sweden");
                    taskGetDoses_onClick.execute(dist_url, "county", "Totalt i Sverige");
                    taskGetAdminOneDoses_onClick.execute(admin_url);
                    taskGetAdminTwoDoses_onClick.execute(admin_url);
                    taskGetAdminDoses_onClick.execute(admin_url);
                }
                else{
                    tv_region_name.setText(region_input);
                    taskGetDoses_onClick.execute(dist_url, "county", region_input);
                    //get admin doses per region by going through "vaccineSamples"
                    for(int i = 0; i < vaccineSamples.size(); i++)
                    {

                        //Log.d("StatisticsVacc", "Curr: " + vaccineSamples.get(i));
                        String currRegion = vaccineSamples.get(i).getRegion_name();
                        if(currRegion.equals(region_input))
                        {
                            oneDose = oneDose + vaccineSamples.get(i).getOne_dose();
                            twoDose = twoDose + vaccineSamples.get(i).getTwo_dose();
                            pop = pop + vaccineSamples.get(i).getPopulation();
                        }
                    }


                    int tempCalc = oneDose - twoDose;
                    int tempCalc2 = twoDose * 2;
                    totalDoses = tempCalc + tempCalc2;

                    tv_adminOneDose.setText("" + oneDose);
                    tv_adminOneDosePerc.setText("" + calcIntPerc(oneDose, pop) + " %");
                    tv_adminTwoDose.setText("" + twoDose);
                    tv_adminTwoDosePerc.setText("" + calcIntPerc(twoDose, pop) + " %");
                    tv_admin_stats.setText("\n" + totalDoses);

                }
            }
        });


    }

    private int calcIntPerc(int a, int b) {
        double c = a;
        double d = b;
        double e = (c/d)*100;
        int f = (int) e;

        return f;
    }

    /*Source: https://www.youtube.com/watch?v=i-TqNzUryn8&ab_channel=BrianFraser*/
    private List<VaccineSample> vaccineSamples = new ArrayList<>();
    private void readVaccineData() throws IOException {
        //VaccineSample sample;
        InputStream is = getResources().openRawResource(R.raw.data_vaccine_fhm_210914);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );

        String line = "";

        reader.readLine();


        while((line = reader.readLine()) != null){
            //split by ',' (.csv file)
            String [] tokens = line.split(",");

            //read data
            VaccineSample sample = new VaccineSample();

            if(!tokens[0].equals(".")) sample.setRegion_id(Integer.parseInt(tokens[0]));
            else{
                sample.setRegion_id(0);
            }
            sample.setRegion_name(tokens[1]);
            if(!tokens[2].equals(".")) sample.setCounty_id(Integer.parseInt(tokens[2]));
            else{
                sample.setCounty_id(0);
            }
            sample.setCounty_name(tokens[3]);
            sample.setAge(tokens[4]);
            if(!tokens[5].equals(".")) sample.setPopulation(Integer.parseInt(tokens[5]));
            else{
                sample.setPopulation(0);
            }
            if(!tokens[6].equals(".")) sample.setOne_dose(Integer.parseInt(tokens[6]));
            else{
                sample.setOne_dose(0);
            }
            if(!tokens[7].equals(".")) sample.setTwo_dose(Integer.parseInt(tokens[7]));
            else{
                sample.setTwo_dose(0);
            }
            if(!tokens[8].equals(".")) sample.setShare_oneDose(Double.parseDouble(tokens[8]));
            else{
                sample.setShare_oneDose(0);
            }
            if(!tokens[9].equals(".")) sample.setShare_twoDose(Double.parseDouble(tokens[9]));
            else{
                sample.setShare_twoDose(0);
            }

            vaccineSamples.add(sample);
        }
    }

    class GetTotalDistDoses extends AsyncTask<String, String, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {

            int totalDoses = 0;

            try {
                /*connect to the url*/
                final Document doc = Jsoup.connect(strings[0]).get();

                /*select elements*/

                Element table = null;
                /*find correct div*/
                Element contentDiv = doc.select("div.article.cf").first(); //get(0)
                if(strings[1] == "total") {
                    /*in that div, find the correct table*/
                    /*possible to use get() instead, but first() works when item is the first on page (= get(0))*/
                    table = contentDiv.select("table").first();

                }
                else if(strings[1] == "county")
                {
                    table = contentDiv.select("table").get(2);
                }

                /*in table, select all rows, identified (in this case) by "tr"*/
                Elements rows = table.select("tr");


                /*only one desired value in this case, located at the bottom of row index 0 (titles excluded)*/
                /*only used for total*/
                int searchedIndex = rows.size() - 1;

                for (int i = 1; i < rows.size(); i++) {
                    /*current row is the i:th row, so we get it*/
                    Element row = rows.get(i);
                    Elements tds, ths = null;
                    if(strings[1] == "total") {
                        /*select the columns found in i:th row*/
                        tds = row.select("td");
                        if(i == searchedIndex)
                        {
                            /*remove spaces from string using replaceAll function*/
                            /*this makes it possible to then parse string to integer*/
                            String tempNoSpace = tds.get(0).text().replaceAll("\\s", "");
                            totalDoses = Integer.parseInt(tempNoSpace);
                        }
                    }
                    else if(strings[1] == "county"){
                        ths = row.select("th");
                        tds = row.select("td");

                        String temp = ths.get(0).text();
                        if(temp.equals(strings[2]))
                        {
                            /*remove spaces from string using replaceAll function*/
                            /*this makes it possible to then parse string to integer*/
                            String tempDosesNoSpace = tds.get(0).text().replaceAll("\\s", "");
                            totalDoses = Integer.parseInt(tempDosesNoSpace);
                        }

                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return totalDoses;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            tv_dist_stats.setText(" " + integer);
        }


    }

    class GetTotalAdminDoses extends AsyncTask<String, String, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {

            int totalDoses = 0;

            try {
                /*connect to the url*/
                final Document doc = Jsoup.connect(strings[0]).get();

                Element contentDiv = doc.select("div.article.cf").first();

                Element table = contentDiv.select("table").first();
                Elements rows = table.select("tr");

                Element row = rows.get(1);
                Elements tds = row.select("td");;

                String tempDosesNoSpace = tds.get(0).text().replaceAll("\\s", "");
                totalDoses = Integer.parseInt(tempDosesNoSpace);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return totalDoses;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            tv_admin_stats.setText("\n" + integer);
        }


    }

    class GetAdminOneDoses extends AsyncTask<String, String, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {

            int totalDoses = 0;

            try {
                /*connect to the url*/
                final Document doc = Jsoup.connect(strings[0]).get();

                Element contentDiv = doc.select("div.article.cf").first();

                Element table = contentDiv.select("table").get(1);
                Elements rows = table.select("tr");

                Element row = rows.get(1);
                Elements tds = row.select("td");;

                String tempOneDosesNoSpace = tds.get(0).text().replaceAll("\\s", "");
                totalDoses = Integer.parseInt(tempOneDosesNoSpace);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return totalDoses;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            int swe_pop = 8544184;

            tv_adminOneDose.setText("" + integer);
            tv_adminOneDosePerc.setText("" + calcIntPerc(integer, swe_pop) + " %");
        }


    }

    class GetAdminTwoDoses extends AsyncTask<String, String, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {

            int totalDoses = 0;

            try {
                /*connect to the url*/
                final Document doc = Jsoup.connect(strings[0]).get();

                Element contentDiv = doc.select("div.article.cf").first();

                Element table = contentDiv.select("table").get(1);
                Elements rows = table.select("tr");

                Element row = rows.get(1);
                Elements tds = row.select("td");;

                String tempOneDosesNoSpace = tds.get(2).text().replaceAll("\\s", "");
                totalDoses = Integer.parseInt(tempOneDosesNoSpace);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return totalDoses;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            int swe_pop = 8544184;

            tv_adminTwoDose.setText("" + integer);
            tv_adminTwoDosePerc.setText("" + calcIntPerc(integer, swe_pop) + " %");
        }


    }


}
