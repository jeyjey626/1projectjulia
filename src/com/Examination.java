package com;

import java.util.Date;
import java.util.Formatter;


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
        double heightPerc = (double)height/100;
        double heightDoub = heightPerc*heightPerc;
        this.bmi = mass/heightDoub;
    }
    /*public void setFromObject(Examination examination){
        this.date = examination.date;
        this.mass = examination.mass;
        this.height = examination.height;
        this.bmi = examination.bmi;
        this.sDate = examination.sDate;
    }*/
    public void setExamination(Date date, double mass, int height, String sDate) //for editing exam purposes, nothing is set separately
    {
        this.sDate = sDate;
        this.date = date;
        this.height = height;
        double heightPerc = height/100;
        double heightDoub = heightPerc*heightPerc;
        this.bmi = mass/heightDoub;
        this.mass = mass;
    }

    public Date getDate() { return date; }

    public String getBmiString() {
        Formatter formatter = new Formatter();
        formatter.format("%.2f", this.bmi);
        return String.valueOf(formatter);
    }

    public String getsDate() { return sDate; }

    public String  getMass() {return String.valueOf(mass); }

    public String getHeight() { return String.valueOf(height); }


}
