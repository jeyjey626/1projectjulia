package com;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

public class Patient {

    private String name;
    private String surname;
    private String pesel;
    private String sex; // T = male, F = female
    private String insurance;
    private Boolean examination;

    private Examination examinationResults; //Each patient has their own examination

    public Patient(String name, String surname, String pesel, Boolean sex, String insurance)
    {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        if(sex)this.sex = "M"; else this.sex = "K";
        this.insurance = insurance;
        this.examination = false; //Patient starts without the examination
    }

    public Vector<Object> getVector(){
        return new Vector<>(Arrays.asList(getName()+" "+getSurname(),getSex(),getPesel(),getInsurance(),isExamination()));
    }
    public Object [] getArray(){
        Object[] object = {getName()+" "+getSurname(),getSex(),getPesel(),getInsurance(),isExamination()};
        return object;
    }


    public String getInsurance() { return insurance; }

    public String getName() { return name; }

    public String getSurname() { return surname;}

    public String getSex() { return sex; }

    public String getPesel() { return pesel; }

    public Boolean isExamination() { return examination; }

    public Examination getExaminationResults() { return examinationResults; }

    public void setExaminationResults(Date date, double mass, int height) //Set examination somewhere along by giving examination object
    {
        Examination examination = new Examination(date, mass, height);
        this.examinationResults = examination;
    }

    public void setExamination(boolean examination) { this.examination = examination; }
}
