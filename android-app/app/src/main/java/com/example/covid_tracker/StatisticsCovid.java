package com.example.covid_tracker;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StatisticsCovid extends AppCompatActivity {
    Button btn_cov_searchRegion;

    TextView tv_region_name_cov, tv_case_stats_cov, tv_deaths_stats_cov, tv_clearfilters;

    Spinner spinner_cov_searchRegion, spinner_cov_age;

    LineChart lc_casesperweek, lc_deathsperweek;


    String[] regions = {"(Region) Sweden", "Blekinge", "Dalarna", "Gotland", "Gävleborg", "Halland", "Jämtland/Härjedalen", "Jönköping", "Kalmar", "Kronoberg",
            "Norrbotten", "Skåne", "Stockholm", "Södermanland", "Uppsala", "Värmland", "Västerbotten", "Västernorrland", "Västmanland",
            "Västra Götaland", "Örebro", "Östergötland"};
    String[] ages = {"(Age Group) All", "0-9", "10-19", "20-29", "30-39", "40-49", "50-59", "60-69", "70-79", "80-89", "90+"};

    private final List<CovidAgeSample> covidAgeSamples = new ArrayList<>();
    private final List<CovidWeeklySample> covidWeeklySamples = new ArrayList<>();
    private final List<CovidWeeklyCountrySample> covidWeeklyCountrySamples = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stat_covid);

        btn_cov_searchRegion = findViewById(R.id.btn_cov_searchRegion);
        tv_region_name_cov = findViewById(R.id.tv_region_name_cov);
        tv_case_stats_cov = findViewById(R.id.tv_case_stats_cov);
        tv_deaths_stats_cov = findViewById(R.id.tv_deaths_stats_cov);
        tv_clearfilters = findViewById(R.id.tv_clearfilters_cov);

        spinner_cov_searchRegion = findViewById(R.id.spinner_cov_searchRegion);
        spinner_cov_age = findViewById(R.id.spinner_cov_age);

        lc_casesperweek = findViewById(R.id.lc_casesperweek);
        lc_deathsperweek = findViewById(R.id.lc_deathsperweek);

        ArrayAdapter<String> region_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, regions);
        spinner_cov_searchRegion.setAdapter(region_adapter);

        ArrayAdapter<String> age_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ages);
        spinner_cov_age.setAdapter(age_adapter);

        //read data from csv files into samples
        try {
            readCovidAgeData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            readCovidWeeklyData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            readCovidWeeklyCountryData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getDeathsCases("");
        onClickSetGraph("");

        btn_cov_searchRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input_age = spinner_cov_age.getSelectedItem().toString();
                String input_region = spinner_cov_searchRegion.getSelectedItem().toString();


                onClickDeathsCases(input_region, input_age);
                onClickSetGraph(input_region);
                //notify graph about new data
                lc_casesperweek.notifyDataSetChanged();
                lc_casesperweek.invalidate();
                lc_deathsperweek.notifyDataSetChanged();
                lc_deathsperweek.invalidate();
            }
        });

        tv_clearfilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_cov_age.setSelection(0);
                spinner_cov_searchRegion.setSelection(0);

                btn_cov_searchRegion.performClick();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar

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

    private void onClickDeathsCases(String region, String age) {
        if(!age.equals("(Age Group) All"))
        {
            tv_region_name_cov.setText("Sweden");
            spinner_cov_searchRegion.setSelection(0);

            String search_string = search_csv_valid(age);
            int cases = 0, deaths = 0;
            for(int i = 0; i < covidAgeSamples.size(); i++)
            {
                String currAge = covidAgeSamples.get(i).getAge_group();
                if(currAge.equals(search_string))
                {
                    cases = covidAgeSamples.get(i).getCases();
                    deaths = covidAgeSamples.get(i).getDeaths();
                    break;
                }
            }
            tv_case_stats_cov.setText("" + cases);
            tv_deaths_stats_cov.setText("" + deaths);
            spinner_cov_age.setSelection(0);
        }
        else {
            if (!region.equals("(Region) Sweden")) {
                tv_region_name_cov.setText(region);
                getDeathsCases(region);
            } else {
                tv_region_name_cov.setText("Sweden");
                getDeathsCases("");
            }
        }
    }

    private void getDeathsCases(String region){
        int deaths = 0, cases = 0;

        if(region.equals("") || region.equals("(Region) Sweden)")) {
            for(int i = 0; i < covidAgeSamples.size(); i++){
                deaths += covidAgeSamples.get(i).getDeaths();
                cases += covidAgeSamples.get(i).getCases();
            }
        }
        else{
            for(int i = 0; i < covidWeeklySamples.size(); i++){
                String currRegion = covidWeeklySamples.get(i).getRegion();

                if(region.equals(currRegion))
                {
                    deaths += covidWeeklySamples.get(i).getDeaths();
                    cases += covidWeeklySamples.get(i).getCases();
                }
            }
        }


        tv_deaths_stats_cov.setText("" + deaths);
        tv_case_stats_cov.setText("" + cases);

    }

    /*Source: https://www.youtube.com/watch?v=i-TqNzUryn8&ab_channel=BrianFraser*/
    private void readCovidAgeData() throws IOException {

        InputStream is = getResources().openRawResource(R.raw.data_covid_age_fhm_210920);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );

        String line = "";

        reader.readLine();

        while((line = reader.readLine()) != null){
            //split by ',' (.csv file)
            String [] tokens = line.split(",");

            //read data
            CovidAgeSample sample = new CovidAgeSample();

            sample.setAge_group(tokens[0]);
            if(validElement(tokens[1])) sample.setCases(Integer.parseInt(tokens[1]));
            else{
                sample.setCases(0);
            }
            if(validElement(tokens[2])) sample.setIntensive(Integer.parseInt(tokens[2]));
            else{
                sample.setIntensive(0);
            }
            if(validElement(tokens[3])) sample.setDeaths(Integer.parseInt(tokens[3]));
            else{
                sample.setDeaths(0);
            }

            covidAgeSamples.add(sample);
        }
    }

    private void readCovidWeeklyData() throws IOException {

        InputStream is = getResources().openRawResource(R.raw.data_covid_weekly_fhm_210922);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );

        String line = "";

        reader.readLine();

        while((line = reader.readLine()) != null){
            //split by ',' (.csv file)
            String [] tokens = line.split(",");

            //read data
            CovidWeeklySample sample = new CovidWeeklySample();

            if(validElement(tokens[0]))
                sample.setYear(Integer.parseInt(tokens[0]));
            else{
                sample.setYear(0);
            }
            if(validElement(tokens[1]))
                sample.setWeek(Integer.parseInt(tokens[1]));
            else{
                sample.setWeek(0);
            }
            sample.setRegion(tokens[2]);
            if(validElement(tokens[3]))
                sample.setCases(Integer.parseInt(tokens[3]));
            else{
                sample.setCases(0);
            }
            if(validElement(tokens[7]))
                sample.setDeaths(Integer.parseInt(tokens[7]));
            else{
                sample.setDeaths(0);
            }

            covidWeeklySamples.add(sample);
        }
    }

    private void readCovidWeeklyCountryData() throws IOException {

        InputStream is = getResources().openRawResource(R.raw.data_covid_weekly_country_fhm_210922);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );

        String line = "";

        reader.readLine();

        while((line = reader.readLine()) != null){
            //split by ',' (.csv file)
            String [] tokens = line.split(",");

            //read data
            CovidWeeklyCountrySample sample = new CovidWeeklyCountrySample();

            if(validElement(tokens[0]))
                sample.setYear(Integer.parseInt(tokens[0]));
            else{
                sample.setYear(0);
            }
            if(validElement(tokens[1]))
                sample.setWeek(Integer.parseInt(tokens[1]));
            else{
                sample.setWeek(0);
            }
            if(validElement(tokens[2]))
                sample.setCases(Integer.parseInt(tokens[2]));
            else{
                sample.setCases(0);
            }
            if(validElement(tokens[9]))
                sample.setDeaths(Integer.parseInt(tokens[9]));
            else{
                sample.setDeaths(0);
            }

            covidWeeklyCountrySamples.add(sample);
        }
    }

    private boolean validElement(String string) {
        if(string.equals(".") || string.length() < 1) return false;
        return true;
    }

    private String search_csv_valid(String s){
        if(s.equals("0-9"))
            return "Ålder_0_9";
        if(s.equals("10-19"))
            return "Ålder_10_19";
        if(s.equals("20-29"))
            return "Ålder_20_29";
        if(s.equals("30-39"))
            return "Ålder_30_39";
        if(s.equals("40-49"))
            return "Ålder_40_49";
        if(s.equals("50-59"))
            return "Ålder_50_59";
        if(s.equals("60-69"))
            return "Ålder_60_69";
        if(s.equals("70-79"))
            return "Ålder_70_79";
        if(s.equals("80-89"))
            return "Ålder_80_89";
        if(s.equals("90+"))
            return "Ålder_90_plus";

        return null;
    }

    private void onClickSetGraph(String region){
        int deaths = 0, cases = 0, week = 0;

        ArrayList<Entry> yValues_lcCases = new ArrayList<>();
        ArrayList<Entry> yValues_lcDeaths = new ArrayList<>();

        if(region.equals("(Region) Sweden") || region.equals("")) {
            for(int i = 0; i < covidWeeklyCountrySamples.size(); i++) {
                deaths = deaths + covidWeeklyCountrySamples.get(i).getDeaths();
                cases = cases + covidWeeklyCountrySamples.get(i).getCases();

                week = covidWeeklyCountrySamples.get(i).getWeek();

                if (covidWeeklyCountrySamples.get(i).getYear() == 2021)
                    week = week + 52;

                yValues_lcDeaths.add(new Entry(week, deaths));
                yValues_lcCases.add(new Entry(week, cases));
            }
        }
        else{
            for (int i = 0; i < covidWeeklySamples.size(); i++) {
                String currRegion = covidWeeklySamples.get(i).getRegion();

                if (region.equals(currRegion)) {
                    deaths = deaths + covidWeeklySamples.get(i).getDeaths();
                    cases = cases + covidWeeklySamples.get(i).getCases();

                    week = covidWeeklySamples.get(i).getWeek();

                    if (covidWeeklySamples.get(i).getYear() == 2021)
                        week = week + 52;

                    yValues_lcDeaths.add(new Entry(week, deaths));
                    yValues_lcCases.add(new Entry(week, cases));

                }
            }
        }

        lc_casesperweek.setData(getLineChart(yValues_lcCases, "Cases"));
        lc_casesperweek.getDescription().setText("Cases per week");
        lc_deathsperweek.setData(getLineChart(yValues_lcDeaths, "Deaths"));
        lc_deathsperweek.getDescription().setText("Deaths per week");
    }

    private LineData getLineChart(ArrayList<Entry> values1, String s1){

        LineDataSet set1 = new LineDataSet(values1, s1);

        set1.setDrawValues(false);
        set1.setDrawCircles(false);
        set1.setLineWidth(4);
        set1.setFillAlpha(255);
        if(s1.equals("Deaths"))
            set1.setColor(R.color.red);

        ArrayList<ILineDataSet> dataSets_lcDist = new ArrayList<>();
        dataSets_lcDist.add(set1);

        LineData data = new LineData(dataSets_lcDist);
        return data;
    }
}
