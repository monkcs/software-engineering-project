package com.example.covid_tracker;


import org.json.JSONException;
import org.json.JSONObject;

public class Admin_block {

    private int Appointment;
    public int ID, available;
    public String personen, personen2, svaret, telenmr, datumTid;
    private Boolean expandable;


    public Admin_block(JSONObject serialized, String questions) throws JSONException {

        this.personen = serialized.getString("firstname");
        this.personen2 = serialized.getString("surname");
        this.telenmr = serialized.getString("telephone");
        this.datumTid = serialized.getString("datetime");
        this.ID = serialized.getInt("account");
        this.available = serialized.getInt("available");

        this.svaret = questions;

        this.expandable = false;
    }

    public int getID() {

        return this.ID;

    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDatumTid() {
        return datumTid;
    }

    public void setDatumTid(String datumTid) {
        this.datumTid = datumTid;
    }

    public String getPersonen() {
        return personen;
    }

    public void setPersonen(String personen) {
        this.personen = personen;
    }

    public String getSvaret() {
        return svaret;
    }

    public void setSvaret(String svaret) {
        this.svaret = svaret;
    }

    public String getTelenmr() {
        return telenmr;
    }

    public void setTelenmr(String telenmr) {
        this.telenmr = telenmr;
    }

    public Boolean getExpandable() {
        return expandable;
    }

    public void setExpandable(Boolean expandable) {
        this.expandable = expandable;
    }

    public int getAppointment() {
        return Appointment;
    }

    public void setAppointment(int appointment) {
        Appointment = appointment;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getPersonen2() {
        return personen2;
    }

    public void setPersonen2(String personen2) {
        this.personen2 = personen2;
    }

    @Override
    public String toString() {
        return "Admin_block{" +
                "Appointment=" + Appointment +
                ", ID=" + ID +
                ", available=" + available +
                ", personen='" + personen + '\'' +
                ", personen2='" + personen2 + '\'' +
                ", svaret='" + svaret + '\'' +
                ", telenmr='" + telenmr + '\'' +
                ", datumTid='" + datumTid + '\'' +
                ", expandable=" + expandable +
                '}';
    }
}
