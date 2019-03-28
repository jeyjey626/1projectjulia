package com;

import java.util.Date;
import java.util.function.DoubleToIntFunction;

public class Examination {

    private Date date;
    private double mass;
    private int height;
    private double bmi;

    public  Examination(Date date, double mass, int height)
    {
        this.date = date;
        this.height = height;
        this.mass = mass;
        this.bmi = mass / ((double) height /100)*((double) height /100); //todo: that's not right
    }

    public void setDate(Date date) { this.date = date; }

    public Date getDate() { return date; }

    public String getBmi() { return String.valueOf(bmi); }

    public String  getMass() { return String.valueOf(mass); }

    public String getHeight() { return String.valueOf(height); }
}
