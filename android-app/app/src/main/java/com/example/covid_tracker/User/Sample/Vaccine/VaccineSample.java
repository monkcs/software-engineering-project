package com.example.covid_tracker.User.Sample.Vaccine;

public class VaccineSample {
    private int region_id;
    private String region_name;
    private int county_id;
    private String county_name;
    private String age;
    private int population;
    private int one_dose;
    private int two_dose;
    private double share_oneDose;
    private double share_twoDose;

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public int getCounty_id() {
        return county_id;
    }

    public void setCounty_id(int county_id) {
        this.county_id = county_id;
    }

    public String getCounty_name() {
        return county_name;
    }

    public void setCounty_name(String county_name) {
        this.county_name = county_name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getOne_dose() {
        return one_dose;
    }

    public void setOne_dose(int one_dose) {
        this.one_dose = one_dose;
    }

    public int getTwo_dose() {
        return two_dose;
    }

    public void setTwo_dose(int two_dose) {
        this.two_dose = two_dose;
    }

    public double getShare_oneDose() {
        return share_oneDose;
    }

    public void setShare_oneDose(double share_oneDose) {
        this.share_oneDose = share_oneDose;
    }

    public double getShare_twoDose() {
        return share_twoDose;
    }

    public void setShare_twoDose(double share_twoDose) {
        this.share_twoDose = share_twoDose;
    }

    @Override
    public String toString() {
        return "VaccineSample{" +
                "region_id=" + region_id +
                ", region_name='" + region_name + '\'' +
                ", county_id=" + county_id +
                ", county_name='" + county_name + '\'' +
                ", age='" + age + '\'' +
                ", population=" + population +
                ", one_dose=" + one_dose +
                ", two_dose=" + two_dose +
                ", share_oneDose=" + share_oneDose +
                ", share_twoDose=" + share_twoDose +
                '}';
    }
}

