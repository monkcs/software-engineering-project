package com.example.covid_tracker;

public class Booking_block {



        private String Tid, Dos, Plats;
        private Boolean expandable;


        public Booking_block(String Tid, String Dos, String Plats) {
            this.Tid = Tid;
            this.Dos = Dos;
            this.Plats = Plats;

            this.expandable = false;
        }


    public String getTid() {
        return Tid;
    }

    public void setTid(String Tid) {
        this.Tid = Tid;
    }

    public String getDos() {
        return Dos;
    }

    public void setDos(String Dos) {
        this.Dos = Dos;
    }



    public Boolean getExpandable() {
        return expandable;
    }

    public void setExpandable(Boolean expandable) {
        this.expandable = expandable;
    }

    public String getPlats() {
        return Plats;
    }

    public void setPlats(String plats) {
        Plats = plats;
    }

    @Override
    public String toString() {
        return "Booking_block{" +
                "Tid='" + Tid + '\'' +
                ", Dos='" + Dos + '\'' +
                ", Plats='" + Plats + '\'' +
                ", expandable=" + expandable +
                '}';
    }
}
