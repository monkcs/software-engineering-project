package com.example.covid_tracker.User.Sample.Vaccine;

public class VaccineWeeklySample {
    private int year;
    private int week;
    private String region;
    private int vaccinated;
    private double share;
    private int status;

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(int vaccinated) {
        this.vaccinated = vaccinated;
    }

    public double getShare() {
        return share;
    }

    public void setShare(double share) {
        this.share = share;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "VaccineWeeklySample{" +
                "year=" + year +
                ", week=" + week +
                ", region='" + region + '\'' +
                ", vaccinated=" + vaccinated +
                ", share=" + share +
                ", status='" + status + '\'' +
                '}';
    }
}
