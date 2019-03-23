package com;

public class Patient {

    private String name;
    private String surname;
    private String pesel;
    private Boolean sex; // T = male, F = female
    private String insurance;
    private Boolean examination;

    private Examination examinationResults; //Each patient has their own examination

    public Patient(String name, String surname, String pesel, Boolean sex, String insurance)
    {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.sex = sex;
        this.insurance = insurance;
        this.examination = false; //Patient starts without the examination
    }

    public String getInsurance() { return insurance; }

    public String getName() { return name; }

    public String getSurname() { return surname;}

    public Boolean getSex() { return sex; }

    public String getPesel() { return pesel; }

    public Boolean isExamination() { return examination; }

    public Examination getExaminationResults() { return examinationResults; }

    public void setExaminationResults(Examination examinationResults) //Set examination somewhere along by giving examination object
    {
        this.examinationResults = examinationResults;
    }

    public void setExamination(boolean examination) { this.examination = examination; }
}
