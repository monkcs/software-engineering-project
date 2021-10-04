package com.example.covid_tracker;

public class Admin_block {

    private String Tid, Dos, Plats;
    private Boolean expandable;


    public Admin_block(String Tid, String Dos, String Plats) {
        this.Tid = Tid;
        this.Dos = Dos;
        this.Plats = Plats;

        this.expandable = false;
    }


    public String getTid() {
        return Tid;
    }

    public void setTid(String tid) {
        Tid = tid;
    }

    public String getDos() {
        return Dos;
    }

    public void setDos(String dos) {
        Dos = dos;
    }

    public String getPlats() {
        return Plats;
    }

    public void setPlats(String plats) {
        Plats = plats;
    }

    public Boolean getExpandable() {
        return expandable;
    }

    public void setExpandable(Boolean expandable) {
        this.expandable = expandable;
    }

    @Override
    public String toString() {
        return "Admin_block{" +
                "Tid='" + Tid + '\'' +
                ", Dos='" + Dos + '\'' +
                ", Plats='" + Plats + '\'' +
                ", expandable=" + expandable +
                '}';
    }
}
