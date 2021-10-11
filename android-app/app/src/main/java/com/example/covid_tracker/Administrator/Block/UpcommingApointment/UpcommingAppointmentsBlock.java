package com.example.covid_tracker.Administrator.Block.UpcommingApointment;

public class UpcommingAppointmentsBlock {

    private String date, time, lastname, firstname, phone;
    private int dose;
    private Boolean expandable;

    /*Time is string bc format is hh:mm*/

    public UpcommingAppointmentsBlock(String date, String time, String lastname, String firstname, String phone, Integer dose){
        this.date = date;
        this.time = time;
        this.lastname = lastname;
        this.firstname = firstname;
        this.phone = phone;
        this.dose = dose;

        this.expandable = false;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Boolean getExpandable() {
        return expandable;
    }

    public void setExpandable(Boolean expandable) {
        this.expandable = expandable;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    @Override
    public String toString() {
        return "UpcommingAppointmentsBlock{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", phone='" + phone + '\'' +
                ", dose=" + dose +
                ", expandable=" + expandable +
                '}';
    }
}