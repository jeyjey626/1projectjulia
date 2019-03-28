package com;

import java.util.Date;


public class Examination {

    private Date date;
    private double mass;
    private int height;
    private double bmi;
    private String sDate;

    Examination(Date date, double mass, int height, String sDate)
    {
        this.sDate = sDate;
        this.date = date;
        this.height = height;
        this.mass = mass;
        this.bmi = mass / ((double) height /100)*((double) height /100); //todo: that's not right
    }

    public void setExamination(Date date, double mass, int height, String sDate) //for editing exam purposes, nothing is set separately
    {
        this.sDate = sDate;
        this.date = date;
        this.height = height;
        this.mass = mass;
        this.bmi = mass / ((double) height /100)*((double) height /100); //todo: that's not right
    }

    public Date getDate() { return date; }

    public String getBmi() { return String.valueOf(bmi); }

    public String getsDate() { return sDate; }

    public String  getMass() { return String.valueOf(mass); }

    public String getHeight() { return String.valueOf(height); }


}
