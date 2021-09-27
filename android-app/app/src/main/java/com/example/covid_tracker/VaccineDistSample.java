package com.example.covid_tracker;

public class VaccineDistSample {
    private String region_name;
    private int year;
    private int week;
    private String product;
    private int delivered;

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getDelivered() {
        return delivered;
    }

    public void setDelivered(int delivered) {
        this.delivered = delivered;
    }

    @Override
    public String toString() {
        return "VaccineDistSample{" +
                "region_name='" + region_name + '\'' +
                ", year=" + year +
                ", week=" + week +
                ", product='" + product + '\'' +
                ", delivered=" + delivered +
                '}';
    }
}
