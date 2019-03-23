package com;

public class PatientList {

    private Examination examination;
    private Patient patient;

    public PatientList(Examination examination, Patient patient)
    {
        this.examination = examination;
        this.patient = patient;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Examination getExamination() {
        return examination;
    }

    public Patient getPatient() {
        return patient;
    }
}
