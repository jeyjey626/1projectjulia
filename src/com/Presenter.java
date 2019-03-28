package com;



import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.util.Date;
import java.util.Vector;

public class Presenter {

    public Vector<Patient> patientVectorList = new Vector<>();

    //check&save Exams for patients
    public int saveExamination(String sDate, Date date, String mass, String height, JTable table, Integer currentEditIndex){
        boolean weightCh = NumberCheck.isNumber(mass);
        if(StringUtils.isEmpty(mass)||StringUtils.isEmpty(height)||StringUtils.isEmpty(sDate)){return 1;}
        else if(!StringUtils.isNumeric(height) || !weightCh ){return 2;}
        else {
            patientVectorList.get(currentEditIndex).setExaminationResults(date, Double.parseDouble(mass), Integer.parseInt(height), sDate);
            patientVectorList.get(currentEditIndex).setExamination(true);
            AppUtils.tableUpdate(patientVectorList,table);
        }
        return 0;
    }


    //Sending data to patient panel to enable edit
    public int editPatient(String name, String surname, String pesel, boolean sex, String insurance, JTable table, int rowEditIndex){
        int check = checkPatientInput(name, surname, pesel);
        //when patient is already in database
        if(check == 4 || check == 0){
            Patient patient = new Patient(name, surname, pesel, sex, insurance);
            patientVectorList.set(rowEditIndex, patient);
            AppUtils.tableUpdate(patientVectorList, table);
            check = 0; //all ok, changing check for the right window
        }
        return check;
    }

    //Saving new Patient
    public int savePatient(String name, String surname, String pesel, boolean sex, String insurance, JTable table){
        int check = checkPatientInput(name, surname, pesel);
        if(check == 0){
            Patient patient = new Patient(name, surname, pesel, sex, insurance);
            patientVectorList.add(patient);
            AppUtils.tableUpdate(patientVectorList, table);
            //TODO: should I send patient to library (here or in Patient class?)
        }
        return check;
    }


    // Deleting Patient
    public void deletePatient(JTable table){
        if(table.getSelectedRow() != -1) {
            patientVectorList.remove(table.getSelectedRow());
            AppUtils.tableUpdate(patientVectorList, table);
        }
    }

    //Checking patient data input
    private int checkPatientInput(String name, String surname, String pesel){
        if(!StringUtils.isAlpha(name) || !StringUtils.isAlpha(surname)) return 5;
        if(StringUtils.isEmpty(pesel)||StringUtils.isEmpty(name)||StringUtils.isEmpty(surname))return 3;
        if(!StringUtils.isNumeric(pesel))return 2;
        if(pesel.length() != 11) return 1;
        for (Patient aPatientVectorList : patientVectorList) { if (aPatientVectorList.getPesel().equals(pesel)) {return 4;} }
        return 0;
    }

}
