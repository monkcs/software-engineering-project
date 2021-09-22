package com.example.covid_tracker;

public class CovidAgeSample {
    private String age_group;
    private int cases;
    private int intensive;
    private int deaths;

    public String getAge_group() {
        return age_group;
    }

    public void setAge_group(String age_group) {
        this.age_group = age_group;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getIntensive() {
        return intensive;
    }

    public void setIntensive(int intensive) {
        this.intensive = intensive;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    @Override
    public String toString() {
        return "CovidAgeSample{" +
                "age_group='" + age_group + '\'' +
                ", cases=" + cases +
                ", intensive=" + intensive +
                ", deaths=" + deaths +
                '}';
    }
}
