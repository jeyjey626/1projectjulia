package com;

import java.util.Date;
import java.util.Objects;

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


    public Object [] getArray(){
        return new Object[]{getName()+" "+getSurname(),getSex(),getPesel(),getInsurance(),isExamination()};
    }

    public Boolean getSexBool(){
        Boolean boolSex;
        boolSex = this.sex.equals("M");
        return boolSex;
    }

    public int getInsuranceInt(){
        int insInt =0;
        if(Objects.equals(this.insurance, "Brak"))insInt = 0;
        else if(Objects.equals(this.insurance, "NFZ"))insInt = 1;
        else if (Objects.equals(this.insurance, "Prywatne"))insInt = 2;
        return insInt;
    }

    private String getInsurance() { return insurance; }

    public String getName() { return name; }

    public String getSurname() { return surname;}

    private String getSex() { return sex; }

    public String getPesel() { return pesel; }

    public Boolean isExamination() { return examination; }

    public Examination getExaminationResults() { return examinationResults; }

    public void setExaminationResults(Date date, double mass, int height, String sDate) //Set examination somewhere along by giving examination object
    {
        if(examination) examinationResults.setExamination(date, mass, height, sDate);
        else examinationResults = new Examination(date, mass, height, sDate);
    }

    public void setExamination(boolean examination) { this.examination = examination; }
}
