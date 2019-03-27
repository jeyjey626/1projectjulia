package com;



import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.Vector;

public class Presenter {

    public Vector<Patient> patientVectorList = new Vector<>();
    private Patient patient;

    //check&save Exams for patients
    public int saveEButton(String sDate, Date date, String mass, String height, JTable table){
        int check = 0;

        boolean weightCh = NumberCheck.isNumber(mass);
        if(StringUtils.isEmpty(mass)||StringUtils.isEmpty(height)||StringUtils.isEmpty(sDate)){check = 1;}
        else if(!StringUtils.isNumeric(height) || !weightCh ){check = 2;}
        else {
            patientVectorList.get(table.getSelectedRow()).setExamination(true);
            patientVectorList.get(table.getSelectedRow()).setExaminationResults(date, Double.parseDouble(mass), Integer.parseInt(height));
            AppUtils.tableUpdate(patientVectorList,table);
        }
        //TODO: Disable table when editing, or get selected row straight after clicking edit panel
        return check;
    }

    //Sending data to patient panel to enable edit
    public int editPatient(String name, String surname, String pesel, boolean sex, String insurance, JTable table){
        int check = checkP(name, surname, pesel);
        if(check == 0){
            Patient patient = new Patient(name, surname, pesel, sex, insurance);
            patientVectorList.set(table.getSelectedRow(), patient);
            //table.clearSelection();
            //patientVectorList.get(-1);
            AppUtils.tableUpdate(patientVectorList, table);
        }
        return check;
    }

    //Saving new Patient
    public int savePButton(String name, String surname, String pesel, boolean sex, String insurance, JTable table){
        int check = checkP(name, surname, pesel);
        if(check == 0){

            Patient patient = new Patient(name, surname, pesel, sex, insurance);
            patientVectorList.add(patient);
            //table.clearSelection();
            //patientVectorList.get(-1);
            AppUtils.tableUpdate(patientVectorList, table);
            //TODO: should I send patient to library (here or in Patient class?)
        }
        return check;
    }


    // Deleting Patient
    public void deletePButton(JTable table){
        if(table.getSelectedRow() != -1) {
            patientVectorList.remove(table.getSelectedRow());
            AppUtils.tableUpdate(patientVectorList, table);
        }
    }

    //Checking patient data input
    private int checkP(String name, String surname, String pesel){
        int check = 0;
        if(StringUtils.isEmpty(pesel)||StringUtils.isEmpty(name)||StringUtils.isEmpty(surname)){check = 3;}
        else if(pesel.length() != 11) {check = 1;}
        else if(!StringUtils.isNumeric(pesel)){check = 2;}
        else {
            for (Patient aPatientVectorList : patientVectorList) {
                if (aPatientVectorList.getPesel().equals(pesel)) {
                    check = 4;
                    break;
                }
            }
        }
        return check;


    }

}
