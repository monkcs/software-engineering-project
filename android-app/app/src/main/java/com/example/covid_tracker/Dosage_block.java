package com.example.covid_tracker;

public class Dosage_block {

    public int antal;
    public String id, namn;


    public Dosage_block(String id, int antal, String namn){

        this.id = id;
        this.antal = antal;
        this.namn = namn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Dosage_block{" +
                "antal=" + antal +
                ", id='" + id + '\'' +
                ", namn='" + namn + '\'' +
                '}';
    }
}
