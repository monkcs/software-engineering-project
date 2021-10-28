package com.example.covid_tracker;

public class DigitalHealthBlock {
    public String first, second;


    public DigitalHealthBlock(String first, String second){

        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String id) {
        this.first = id;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String namn) {
        this.second = namn;
    }

    @Override
    public String toString() {
        return "DigitalHealthBlock{" +
                "first='" + first + '\'' +
                ", second='" + second + '\'' +
                '}';
    }
}
