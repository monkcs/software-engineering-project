package com.example.covid_tracker;

public class age_change_block {

    private String datumText, listaageText;
    private Boolean expandable;

    public age_change_block(String datumText, String listaageText) {
        this.datumText = datumText;

        this.listaageText = listaageText;

        this.expandable = false;
    }

    @Override
    public String toString() {
        return "age_change_block{" +
                "datumText='" + datumText + '\'' +
                ", listaageText='" + listaageText + '\'' +
                ", expandable=" + expandable +
                '}';
    }

    public String getDatumText() {
        return datumText;
    }

    public void setDatumText(String datumText) {
        this.datumText = datumText;
    }

    public String getListaageText() {
        return listaageText;
    }

    public void setListaageText(String listaageText) {
        this.listaageText = listaageText;
    }

    public Boolean getExpandable() {
        return expandable;
    }

    public void setExpandable(Boolean expandable) {
        this.expandable = expandable;
    }
}
