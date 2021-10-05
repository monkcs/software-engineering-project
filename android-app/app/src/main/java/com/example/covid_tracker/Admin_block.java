package com.example.covid_tracker;

public class Admin_block {

    private String personen, svaret, telenmr, datumTid;
    private Boolean expandable;


    public Admin_block(String personen, String svaret, String telenmr, String datumTid) {
        this.personen = personen;
        this.svaret = svaret;
        this.telenmr = telenmr;
        this.datumTid = datumTid;

        this.expandable = false;
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

    @Override
    public String toString() {
        return "Admin_block{" +
                "personen='" + personen + '\'' +
                ", svaret='" + svaret + '\'' +
                ", telenmr='" + telenmr + '\'' +
                ", datumTid='" + datumTid + '\'' +
                ", expandable=" + expandable +
                '}';
    }
}
