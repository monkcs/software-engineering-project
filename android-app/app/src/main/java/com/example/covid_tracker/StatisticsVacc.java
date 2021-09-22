package com.example.covid_tracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class StatisticsVacc extends AppCompatActivity {
    Button btn_searchRegion;
    TextView tv_region_name, tv_dist_stats, tv_admin_stats, tv_adminOneDose, tv_adminOneDosePerc, tv_adminTwoDose, tv_adminTwoDosePerc;
    LineChart lc_distperweek;

    Spinner spinner_searchRegion, spinner_age_vac, spinner_noDoses, spinner_product;

    String[] regions = {"(Region) Sweden", "Blekinge", "Dalarna", "Gotland", "Gävleborg", "Halland", "Jämtland", "Jönköping", "Kalmar", "Kronoberg",
            "Norrbotten", "Skåne", "Stockholm", "Södermanland", "Uppsala", "Värmland", "Västerbotten", "Västernorrland", "Västmanland",
            "Västra Götaland", "Örebro", "Östergötland"};

    String[] ages = {"(Age Group) All", "16-17", "18-29", "30-39", "40-49", "50-59", "60-69", "70-79", "80-89", "90+"};

    String[] products = {"(Product) All", "Pfizer/BioNTech", "Moderna", "AstraZeneca"};

    String[] doses = {"(Doses) All", "1 (at least)", "2"};

    private final List<VaccineSample> vaccineSamples = new ArrayList<>();
    private final List<VaccineDistSample> vaccineDistSamples = new ArrayList<>();

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

        spinner_searchRegion = findViewById(R.id.spinner_searchRegion);
        spinner_age_vac = findViewById(R.id.spinner_age_vac);
        spinner_noDoses = findViewById(R.id.spinner_noDoses);
        spinner_product = findViewById(R.id.spinner_product);

        lc_distperweek = findViewById(R.id.lc_distperweek);

        ArrayAdapter<String> region_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, regions);
        spinner_searchRegion.setAdapter(region_adapter);

        ArrayAdapter<String> age_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ages);
        spinner_age_vac.setAdapter(age_adapter);

        ArrayAdapter<String> product_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, products);
        spinner_product.setAdapter(product_adapter);

        ArrayAdapter<String> dose_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, doses);
        spinner_noDoses.setAdapter(dose_adapter);

        try {
            readVaccineAdminData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            readVaccineDistData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        beforeClick();
        onClickDistDoses("(Region) Sweden", "(Product) All");

        btn_searchRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String region_input, age_input, product_input;
                region_input = spinner_searchRegion.getSelectedItem().toString();
                product_input = spinner_product.getSelectedItem().toString();
                age_input = spinner_age_vac.getSelectedItem().toString();

                onClickDistDoses(region_input, product_input);
                //notify graph about new data
                lc_distperweek.notifyDataSetChanged();
                lc_distperweek.invalidate();

                onClickAdminDoses(region_input, age_input);
            }
        });
    }

    private int calcIntPercent(int a, int b) {
        double e = ((double) a / (double) b) * 100;
        return (int) e;
    }

    /*Source: https://www.youtube.com/watch?v=i-TqNzUryn8&ab_channel=BrianFraser*/
    private void readVaccineAdminData() throws IOException {
        InputStream is = getResources().openRawResource(R.raw.data_vaccine_uptake_fhm_210914);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );

        String line = "";

        reader.readLine();

        while ((line = reader.readLine()) != null) {
            //split by ',' (.csv file)
            String[] tokens = line.split(",");

            //read data
            VaccineSample sample = new VaccineSample();

            if (validElement_vacc(tokens[0])) sample.setRegion_id(Integer.parseInt(tokens[0]));
            else {
                sample.setRegion_id(0);
            }
            sample.setRegion_name(tokens[1]);
            if (validElement_vacc(tokens[2])) sample.setCounty_id(Integer.parseInt(tokens[2]));
            else {
                sample.setCounty_id(0);
            }
            sample.setCounty_name(tokens[3]);
            sample.setAge(tokens[4]);
            if (validElement_vacc(tokens[5])) sample.setPopulation(Integer.parseInt(tokens[5]));
            else {
                sample.setPopulation(0);
            }
            if (validElement_vacc(tokens[6])) sample.setOne_dose(Integer.parseInt(tokens[6]));
            else {
                sample.setOne_dose(0);
            }
            if (validElement_vacc(tokens[7])) sample.setTwo_dose(Integer.parseInt(tokens[7]));
            else {
                sample.setTwo_dose(0);
            }
            if (validElement_vacc(tokens[8])) sample.setShare_oneDose(Double.parseDouble(tokens[8]));
            else {
                sample.setShare_oneDose(0);
            }
            if (validElement_vacc(tokens[9])) sample.setShare_twoDose(Double.parseDouble(tokens[9]));
            else {
                sample.setShare_twoDose(0);
            }

            vaccineSamples.add(sample);
        }
    }

    private void readVaccineDistData() throws IOException {

        InputStream is = getResources().openRawResource(R.raw.data_vaccine_dist_fhm_210921);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );

        String line = "";

        reader.readLine();

        line = reader.readLine();
        String[] tokens_year = line.split(",");

        line = reader.readLine();
        String[] tokens_week = line.split(",");

        line = reader.readLine();
        String[] tokens_product = line.split(",");

        while((line = reader.readLine()) != null)
        {
            //split by "," but ignore values inside quotation
            String tokens[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            String regionName = "";

            if(tokens[0].equals(" Fohm lager* "))
                break;

            //read region_name
            if(removeSpaces(tokens[0]).equals("AntaldosertillSverige"))
                regionName = "Sweden";
            else {
                regionName = removeSpaces(tokens[0]);
            }

            //iterate through every token in the line
            //every column will be a sample, therefore find the corresponding value
            //for year, week and product
            for(int i = 1; i < tokens.length - 4; i++)
            {
                VaccineDistSample sample = new VaccineDistSample();
                sample.setRegion_name(regionName);

                sample.setYear(Integer.parseInt(removeSpaces(tokens_year[i])));
                sample.setWeek(Integer.parseInt(removeSpaces(tokens_week[i])));
                sample.setProduct(removeSpaces(tokens_product[i]));

                if(validElement_vacc(removeSpaces(tokens[i])))
                    sample.setDelivered(Integer.parseInt(removeSpacesQuotesCommas(tokens[i])));
                else
                {
                    sample.setDelivered(0);
                }

                vaccineDistSamples.add(sample);
            }
        }
    }

    private boolean validElement_vacc(String string) {
        if (string.equals(".") || string.length() < 1 || string.equals("-")) return false;
        return true;
    }

    private String removeSpaces(String s){
        return s.replaceAll("\\s", "");
    }

    private String removeSpacesQuotesCommas(String s){
        return s.replaceAll("[ ,\"]", "");
    }

    private void beforeClick(){
        int oneDose = 0, twoDose = 0, pop = 0;

        for (int i = 0; i < vaccineSamples.size(); i++) {
            oneDose = oneDose + vaccineSamples.get(i).getOne_dose();
            twoDose = twoDose + vaccineSamples.get(i).getTwo_dose();
            pop = pop + vaccineSamples.get(i).getPopulation();
        }

        int tempCalc = oneDose - twoDose;
        int tempCalc2 = twoDose * 2;
        int totalDoses = tempCalc + tempCalc2;

        tv_adminOneDose.setText("" + oneDose);
        tv_adminOneDosePerc.setText("" + calcIntPercent(oneDose, pop) + " %");
        tv_adminTwoDose.setText("" + twoDose);
        tv_adminTwoDosePerc.setText("" + calcIntPercent(twoDose, pop) + " %");
        tv_admin_stats.setText("\n" + totalDoses);

        int delivDoses = 0;
        for(int i = 0; i < vaccineDistSamples.size(); i++)
        {
            String currRegion = vaccineDistSamples.get(i).getRegion_name();

            if(currRegion.equals("Sweden"))
                delivDoses = delivDoses + vaccineDistSamples.get(i).getDelivered();
        }

        tv_dist_stats.setText(" " + delivDoses);
    }

    private void onClickAdminDoses(String region, String age) {
        int oneDose = 0, twoDose = 0, totalDoses = 0, pop = 0;

        if (age.equals("90+"))
            age = "90 eller äldre";


        if (region.equals("(Region) Sweden")) {
            tv_region_name.setText("Sweden");

            for (int i = 0; i < vaccineSamples.size(); i++) {
                String currAge = vaccineSamples.get(i).getAge();

                if (!age.equals("(Age Group) All")) {
                    if (currAge.equals(age)) {
                        oneDose = oneDose + vaccineSamples.get(i).getOne_dose();
                        twoDose = twoDose + vaccineSamples.get(i).getTwo_dose();
                        pop = pop + vaccineSamples.get(i).getPopulation();
                    }
                } else {
                    oneDose = oneDose + vaccineSamples.get(i).getOne_dose();
                    twoDose = twoDose + vaccineSamples.get(i).getTwo_dose();
                    pop = pop + vaccineSamples.get(i).getPopulation();
                }
            }
        } else {
            tv_region_name.setText(region);
            String into_async = "";
            if (region.equals("Jämtland"))
                into_async = "Jämtland/ Härjedalen";
            //get admin doses per region by going through "vaccineSamples"
            for (int i = 0; i < vaccineSamples.size(); i++) {

                String currRegion = vaccineSamples.get(i).getRegion_name();
                String currAge = vaccineSamples.get(i).getAge();
                if (age.equals("(Age Group) All")) {
                    if (currRegion.equals(region)) {
                        oneDose = oneDose + vaccineSamples.get(i).getOne_dose();
                        twoDose = twoDose + vaccineSamples.get(i).getTwo_dose();
                        pop = pop + vaccineSamples.get(i).getPopulation();
                    }
                } else {
                    if (currRegion.equals(region) && currAge.equals(age)) {
                        oneDose = oneDose + vaccineSamples.get(i).getOne_dose();
                        twoDose = twoDose + vaccineSamples.get(i).getTwo_dose();
                        pop = pop + vaccineSamples.get(i).getPopulation();
                    }
                }
            }
        }


        int tempCalc = oneDose - twoDose;
        int tempCalc2 = twoDose * 2;
        totalDoses = tempCalc + tempCalc2;

        tv_adminOneDose.setText("" + oneDose);
        tv_adminOneDosePerc.setText("" + calcIntPercent(oneDose, pop) + " %");
        tv_adminTwoDose.setText("" + twoDose);
        tv_adminTwoDosePerc.setText("" + calcIntPercent(twoDose, pop) + " %");
        tv_admin_stats.setText("\n" + totalDoses);
    }

    private void onClickDistDoses(String region, String product){
        int delivDoses = 0;
        String currRegion = "", currProduct = "";
        ArrayList<Entry> yValues_lcDist = new ArrayList<>();

        if(region.equals("(Region) Sweden"))
            region = "Sweden";
        else if(region.equals("Jämtland"))
            region = "Jämtland/Härjedalen";

        for(int i = 0; i < vaccineDistSamples.size(); i++)
        {
            currRegion = vaccineDistSamples.get(i).getRegion_name();
            currProduct = vaccineDistSamples.get(i).getProduct();
            if(product.equals("(Product) All")){
                //product is not a factor
                if(currRegion.equals(region)) {
                    delivDoses = delivDoses + vaccineDistSamples.get(i).getDelivered();
                    if(vaccineDistSamples.get(i).getWeek() < 40)
                        yValues_lcDist.add(new Entry(vaccineDistSamples.get(i).getWeek(), delivDoses));
                }

            }
            else{
                //product is also a factor
                if(currRegion.equals(region) && currProduct.equals(product)) {
                    delivDoses = delivDoses + vaccineDistSamples.get(i).getDelivered();
                    if(vaccineDistSamples.get(i).getWeek() < 40)
                        yValues_lcDist.add(new Entry(vaccineDistSamples.get(i).getWeek(), delivDoses));
                }
            }
        }

        lc_distperweek.setData(getLineChart(yValues_lcDist, "Doses delivered"));
        lc_distperweek.getDescription().setText("Doses delivered per week");
        tv_dist_stats.setText("  " + delivDoses);
    }

    private LineData getLineChart(ArrayList<Entry> values1, String s1){

        LineDataSet set1 = new LineDataSet(values1, s1);

        set1.setDrawValues(false);
        set1.setDrawCircles(false);
        set1.setLineWidth(4);
        set1.setFillAlpha(255);

        ArrayList<ILineDataSet> dataSets_lcDist = new ArrayList<>();
        dataSets_lcDist.add(set1);

        LineData data = new LineData(dataSets_lcDist);
        return data;
    }

}
