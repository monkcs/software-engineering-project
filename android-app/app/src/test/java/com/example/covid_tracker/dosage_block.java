package com.example.covid_tracker;

public class dosage_block {

    public int antal;
    public String namn;


    public dosage_block(int antal, String namn){

        this.antal = antal;
        this.namn = namn;
    }

    @Override
    public String toString() {
        return "dosage_block{" +
                "antal=" + antal +
                ", namn='" + namn + '\'' +
                '}';
    }

    public int getAntal() {
        return antal;
    }

    public void setAntal(int antal) {
        this.antal = antal;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }
}
