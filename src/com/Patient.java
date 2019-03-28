package com;



import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Vector;

public class Patient {

    private String name;
    private String surname;
    private String pesel;
    private String sex; // T = male, F = female
    private String insurance;
    private Boolean examination;

    private Examination examinationResults; //Each patient has their own examination

    Patient(String name, String surname, String pesel, Boolean sex, String insurance)
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

    public Boolean getSexBool(){
        Boolean boolSex;
        if(this.sex == "M")boolSex = true;
        else boolSex = false;
        return boolSex;
    }

    public int getInsuranceInt(){
        int insInt =0;
        if(Objects.equals(this.insurance, "Brak"))insInt = 0;
        else if(Objects.equals(this.insurance, "NFZ"))insInt = 1;
        else if (Objects.equals(this.insurance, "Prywatne"))insInt = 2;
        return insInt;
    }

    public String getInsurance() { return insurance; }

    public String getName() { return name; }

    public String getSurname() { return surname;}

    public String getSex() { return sex; }

    public String getPesel() { return pesel; }

    public Boolean isExamination() { return examination; }

    public Examination getExaminationResults() { return examinationResults; }

    public void setExaminationResults(Date date, double mass, int height, String sDate) //Set examination somewhere along by giving examination object
    {
        if(examination) examinationResults.setExamination(date, mass, height, sDate);
        else examinationResults = new Examination(date, mass, height, sDate); //todo: change here to enable changes, not only new ones
    }

    public void setExamination(boolean examination) { this.examination = examination; }
}
