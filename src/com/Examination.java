package com;

public class Examination {

    private Date date; //todo: biblioteka gdzie jest data
    private double mass;
    private int height;
    private double bmi;

    public  Examination(Date date, double mass, int height)
    {
        this.date = date;
        this.height = height;
        this.mass = mass;
        double b = mass / (height/100)*(height/100); //todo: Zdecydować czy to powinno być tutaj?? Czy są dobre typy zmiennych
        this.bmi = b;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public double getBmi() {
        return bmi;
    }

    public double getMass() {
        return mass;
    }

    public int getHeight() {
        return height;
    }
}
