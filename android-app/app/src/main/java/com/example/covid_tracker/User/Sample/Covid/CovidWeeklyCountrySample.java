package com.example.covid_tracker.User.Sample.Covid;

public class CovidWeeklyCountrySample {
    private int year;
    private int week;
    private int cases;
    private int deaths;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    @Override
    public String toString() {
        return "CovidWeeklyCountrySample{" +
                "year=" + year +
                ", week=" + week +
                ", cases=" + cases +
                ", deaths=" + deaths +
                '}';
    }
}