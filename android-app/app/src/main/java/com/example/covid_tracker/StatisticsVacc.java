package com.example.covid_tracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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
import java.util.Objects;


public class StatisticsVacc extends AppCompatActivity {
    Button btn_searchRegion;
    TextView tv_region_name, tv_dist_stats, tv_admin_stats, tv_admin_perc, tv_admin_perc_title, tv_clearfilters;
    LineChart lc_distperweek, lc_adminperweek;

    Spinner spinner_searchRegion, spinner_age_vac, spinner_noDoses, spinner_product, spinner_weekmonth;

    String[] regions = {"(Region) Sweden", "Blekinge", "Dalarna", "Gotland", "Gävleborg", "Halland", "Jämtland", "Jönköping", "Kalmar", "Kronoberg",
            "Norrbotten", "Skåne", "Stockholm", "Södermanland", "Uppsala", "Värmland", "Västerbotten", "Västernorrland", "Västmanland",
            "Västra Götaland", "Örebro", "Östergötland"};

    String[] ages = {"(Age Group) All", "16-17", "18-29", "30-39", "40-49", "50-59", "60-69", "70-79", "80-89", "90+"};

    String[] products = {"(Product) All", "Pfizer/BioNTech", "Moderna", "AstraZeneca"};

    String[] doses = {"(Doses) Total", "1 (at least)", "2"};

    String[] weekmonth = {"(Month/Week) All", "Jan/1", "Jan/2", "Jan/3", "Jan/4", "Feb/5", "Feb/6", "Feb/7", "Feb/8", "Mar/9", "Mar/10", "Mar/11",
                            "Mar/12", "Mar/13", "Apr/14", "Apr/15", "Apr/16", "Apr/17", "May/18", "May/19", "May/20", "May/21", "May/22", "Jun/23",
                            "Jun/24", "Jun/25", "Jun/26", "Jul/27", "Jul/28", "Jul/29", "Jul/30", "Aug/31", "Aug/32", "Aug/33", "Aug/34", "Aug/35",
                            "Sep/36", "Sep/37", "Sep/38", "Sep/39"};

    private final List<VaccineSample> vaccineSamples = new ArrayList<>();
    private final List<VaccineWeeklySample> vaccineWeeklySamples = new ArrayList<>();
    private final List<VaccineDistSample> vaccineDistSamples = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stat_vaccinations);

        btn_searchRegion = findViewById(R.id.btn_searchRegion);
        tv_region_name = findViewById(R.id.tv_region_name);
        tv_dist_stats = findViewById(R.id.tv_dist_stats);
        tv_admin_stats = findViewById(R.id.tv_admin_stats);
        tv_clearfilters = findViewById(R.id.tv_clearfilters_vacc);
        tv_admin_perc = findViewById(R.id.tv_admin_perc);
        tv_admin_perc_title = findViewById(R.id.tv_admin_perc_title);

        spinner_searchRegion = findViewById(R.id.spinner_searchRegion);
        spinner_age_vac = findViewById(R.id.spinner_age_vac);
        spinner_noDoses = findViewById(R.id.spinner_noDoses);
        spinner_product = findViewById(R.id.spinner_product);
        spinner_weekmonth = findViewById(R.id.spinner_weekmonth);

        lc_distperweek = findViewById(R.id.lc_distperweek);
        lc_adminperweek = findViewById(R.id.lc_adminperweek);

        ArrayAdapter<String> region_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, regions);
        spinner_searchRegion.setAdapter(region_adapter);

        ArrayAdapter<String> age_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ages);
        spinner_age_vac.setAdapter(age_adapter);

        ArrayAdapter<String> product_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, products);
        spinner_product.setAdapter(product_adapter);

        ArrayAdapter<String> dose_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, doses);
        spinner_noDoses.setAdapter(dose_adapter);

        ArrayAdapter<String> weekmonth_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, weekmonth);
        spinner_weekmonth.setAdapter(weekmonth_adapter);

        try {
            readVaccineAdminData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            readWeeklyVaccineData();
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
        setGraphAdminDoses("(Region) Sweden", "(Month/Week) All");

        btn_searchRegion.setOnClickListener(view -> {

            String region_input, age_input, doses_input, product_input, monthweek_input;
            region_input = spinner_searchRegion.getSelectedItem().toString();
            product_input = spinner_product.getSelectedItem().toString();
            age_input = spinner_age_vac.getSelectedItem().toString();
            doses_input = spinner_noDoses.getSelectedItem().toString();
            monthweek_input = spinner_weekmonth.getSelectedItem().toString();

            onClickDistDoses(region_input, product_input);
            //notify graph about new data
            lc_distperweek.notifyDataSetChanged();
            lc_distperweek.invalidate();

            onClickAdminDoses(region_input, age_input, doses_input);
            setGraphAdminDoses(region_input, monthweek_input);
            lc_adminperweek.notifyDataSetChanged();
            lc_adminperweek.invalidate();
        });

        tv_clearfilters.setOnClickListener(view -> {
            spinner_searchRegion.setSelection(0);
            spinner_product.setSelection(0);
            spinner_age_vac.setSelection(0);
            spinner_noDoses.setSelection(0);
            spinner_weekmonth.setSelection(0);

            btn_searchRegion.performClick();
        });

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

        String line;

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

    private void readWeeklyVaccineData() throws IOException{
        InputStream is = getResources().openRawResource(R.raw.data_vaccine_weekly_fhm_210927);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );

        String line;

        reader.readLine();

        while ((line = reader.readLine()) != null) {
            //split by ',' (.csv file)
            String[] tokens = line.split(",");

            //read data
            VaccineWeeklySample sample = new VaccineWeeklySample();

            if (validElement_vacc(tokens[0])) sample.setWeek(Integer.parseInt(tokens[0]));
            else {
                sample.setWeek(0);
            }
            if (validElement_vacc(tokens[1])) sample.setYear(Integer.parseInt(tokens[1]));
            else {
                sample.setYear(0);
            }
            if(tokens[2].equals("| Sverige |"))
                sample.setRegion("Sweden");
            else {
                sample.setRegion(tokens[2]);
            }
            if (validElement_vacc(tokens[3])) sample.setVaccinated(Integer.parseInt(tokens[3]));
            else {
                sample.setVaccinated(0);
            }
            if (validElement_vacc(tokens[4])) sample.setShare(Double.parseDouble(tokens[4]));
            else {
                sample.setShare(0);
            }
            if(tokens[5].equals("Minst 1 dos"))
                sample.setStatus(1);
            else if(tokens[5].equals("Färdigvaccinerade"))
                sample.setStatus(2);
            else{
                sample.setStatus(0);
            }

            vaccineWeeklySamples.add(sample);
        }

    }

    private void readVaccineDistData() throws IOException {

        InputStream is = getResources().openRawResource(R.raw.data_vaccine_dist_fhm_210921);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );

        String line;

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
            String[] tokens = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            String regionName;

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
        return !string.equals(".") && string.length() >= 1 && !string.equals("-");
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

        tv_admin_stats.setText("\n " + totalDoses);
        tv_admin_perc_title.setText("Fully vaccinated(%): ");
        tv_admin_perc.setText(" " + calcIntPercent(twoDose, pop) + " %");

        int delivDoses = 0;
        for(int i = 0; i < vaccineDistSamples.size(); i++)
        {
            String currRegion = vaccineDistSamples.get(i).getRegion_name();

            if(currRegion.equals("Sweden"))
                delivDoses = delivDoses + vaccineDistSamples.get(i).getDelivered();
        }

        tv_dist_stats.setText(" " + delivDoses);
    }

    private void onClickAdminDoses(String region, String age, String doses) {
        int oneDose = 0, twoDose = 0, totalDoses, pop = 0;

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

        switch (doses) {
            case "(Doses) Total":
                tv_admin_stats.setText("\n " + totalDoses);
                tv_admin_perc_title.setText("Fully vaccinated(%): ");
                tv_admin_perc.setText(" " + calcIntPercent(twoDose, pop) + " %");
                break;
            case "1 (at least)":
                tv_admin_stats.setText("\n " + oneDose);
                tv_admin_perc_title.setText("One dose(%): ");
                tv_admin_perc.setText(" " + calcIntPercent(oneDose, pop) + " %");
                break;
            case "2":
                tv_admin_stats.setText("\n " + twoDose);
                tv_admin_perc_title.setText("Fully vaccinated(%): ");
                tv_admin_perc.setText(" " + calcIntPercent(twoDose, pop) + " %");
                break;
        }
    }

    private void onClickDistDoses(String region, String product){
        int delivDoses = 0, week;
        String currRegion, currProduct;
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
                    week = vaccineDistSamples.get(i).getWeek();
                    if(vaccineDistSamples.get(i).getYear() == 2021)
                        yValues_lcDist.add(new Entry(week, delivDoses));
                }

            }
            else{
                //product is also a factor
                if(currRegion.equals(region) && currProduct.equals(product)) {
                    delivDoses = delivDoses + vaccineDistSamples.get(i).getDelivered();
                    week = vaccineDistSamples.get(i).getWeek();
                    if(vaccineDistSamples.get(i).getYear() == 2021)
                        yValues_lcDist.add(new Entry(week, delivDoses));
                }
            }
        }

        lc_distperweek.setData(getLineChart(yValues_lcDist));
        lc_distperweek.getDescription().setText("Doses delivered per week");
        tv_dist_stats.setText("  " + delivDoses);
    }

    private LineData getLineChart(ArrayList<Entry> values1){

        LineDataSet set1 = new LineDataSet(values1, "Doses delivered");

        set1.setDrawValues(false);
        set1.setDrawCircles(false);
        set1.setLineWidth(4);
        set1.setFillAlpha(255);

        ArrayList<ILineDataSet> dataSets_lcDist = new ArrayList<>();
        dataSets_lcDist.add(set1);

        return new LineData(dataSets_lcDist);
    }

    private void setGraphAdminDoses(String region, String weekmonth){
        int week, selected_week = 0;
        boolean should_break = false;

        ArrayList<Entry> values1 = new ArrayList<>();
        ArrayList<Entry> values2 = new ArrayList<>();

        if(!weekmonth.equals("(Month/Week) All")) {
            selected_week = getWeek(weekmonth);
            should_break = true;
        }

        if(region.equals("(Region) Sweden"))
            region = "Sweden";

        for(int i = 0; i < vaccineWeeklySamples.size(); i++) {
            if(region.equals(vaccineWeeklySamples.get(i).getRegion()))
            {
                if(vaccineWeeklySamples.get(i).getStatus() == 1)
                {
                    week = vaccineWeeklySamples.get(i).getWeek();
                    if(vaccineWeeklySamples.get(i).getYear() == 2021)
                        values1.add(new Entry(week, (float) vaccineWeeklySamples.get(i).getShare() * 100));
                }
                else if(vaccineWeeklySamples.get(i).getStatus() == 2)
                {
                    week = vaccineWeeklySamples.get(i).getWeek();
                    if(vaccineWeeklySamples.get(i).getYear() == 2021)
                        values2.add(new Entry(week, (float) vaccineWeeklySamples.get(i).getShare() * 100));
                    if(should_break && week == selected_week)
                        break;
                }
            }
        }

        LineDataSet set1 = new LineDataSet(values1, "1 dose (at least)");
        LineDataSet set2 = new LineDataSet(values2, "Fully vaccinated");

        set1.setDrawValues(false);
        set1.setDrawCircles(false);
        set1.setLineWidth(4);
        set1.setFillAlpha(255);

        set2.setDrawValues(false);
        set2.setDrawCircles(false);
        set2.setLineWidth(4);
        set2.setFillAlpha(255);
        set2.setColor(R.color.red);

        ArrayList<ILineDataSet> dataSets_lcDist = new ArrayList<>();
        dataSets_lcDist.add(set1);
        dataSets_lcDist.add(set2);

        LineData data = new LineData(dataSets_lcDist);

        lc_adminperweek.setData(data);
        lc_adminperweek.getDescription().setText("Vaccinations per week (%)");
    }

    private int getWeek(String weekmonth) {
        int week;
        /* Source: https://stackoverflow.com/questions/17076030/how-can-i-find-int-values-within-a-string/17084935*/
        String temp = weekmonth.replaceAll("\\D+",""); //remove non-digits
        week = Integer.parseInt(temp);
        return week;
    }

}
