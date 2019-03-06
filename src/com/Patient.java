package com;

public class Patient {

    private String name;
    private String surname;
    private String pesel;
    private String sex;
    private String insurance;
    private boolean examination;

    public Patient(String name, String surname, String pesel, String sex, String insurance)
    {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.sex = sex;
        this.insurance = insurance;
        this.examination = false; //Najpierw tworzymy pacjenta do bazy, badanie jeszcze nie istnieje
    }

    public String getInsurance() { return insurance; }

    public String getName() { return name; }

    public String getSurname() { return surname;}

    public String getSex() { return sex; }

    public String getPesel() { return pesel; }

    public boolean isExamination() { return examination; }

    public void setExamination(boolean examination) { this.examination = examination; }
}
