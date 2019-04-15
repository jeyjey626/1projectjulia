package com;



import Utilities.AppUtils;
import Utilities.NumberCheck;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.util.Date;
import java.util.Vector;

public class Presenter {

    public Vector<Patient> patientVectorList = new Vector<>();

    //check&save Exams for patients
    public int saveExamination(String sDate, Date date, String mass, String height, JTable table){
        boolean weightCh = NumberCheck.isNumber(mass);
        if(StringUtils.isEmpty(mass)||StringUtils.isEmpty(height)||StringUtils.isEmpty(sDate)){return 1;}
        else if(!StringUtils.isNumeric(height) || !weightCh ){return 2;}
        else if (Double.parseDouble(mass) < 30 || Double.parseDouble(mass) > 300)return 3;
        else if (Double.parseDouble(height) <100 || Double.parseDouble(height) > 250) return 4;
        else {
            patientVectorList.get(table.getSelectedRow()).setExaminationResults(date, Double.parseDouble(mass), Integer.parseInt(height), sDate);
            patientVectorList.get(table.getSelectedRow()).setExamination(true);
            AppUtils.tableUpdate(patientVectorList,table);
        }
        return 0;
    }


    //Sending data to patient panel to enable edit
    public int editPatient(String name, String surname, String pesel, boolean sex, String insurance, JTable table){
        int check = checkPatientInput(name, surname, pesel, table);
        //when patient is already in database
        if(check == 5 || check == 0){

            Patient patient = new Patient(name, surname, pesel, sex, insurance);
            if(patientVectorList.get(table.getSelectedRow()).isExamination()){
                Examination examRes = patientVectorList.get(table.getSelectedRow()).getExaminationResults();
                patient.setExamination(true);
                patient.setExamFromObject(examRes);
            }
            patientVectorList.set(table.getSelectedRow(), patient);
            AppUtils.tableUpdate(patientVectorList, table);
            check = 0; //all ok, changing check for the right window
        }
        return check;
    }

    //Saving new Patient
    public int savePatient(String name, String surname, String pesel, boolean sex, String insurance, JTable table){
        int check = checkPatientInput(name, surname, pesel, table);
        if(check == 0){
            Patient patient = new Patient(name, surname, pesel, sex, insurance);
            patientVectorList.add(patient);
            AppUtils.tableUpdate(patientVectorList, table);
            ListSelectionModel selectionModel =
                    table.getSelectionModel();
            selectionModel.setSelectionInterval(patientVectorList.size()-1, patientVectorList.size()-1);
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
    private int checkPatientInput(String name, String surname, String pesel, JTable table){

        if(StringUtils.isEmpty(pesel)||StringUtils.isEmpty(name)||StringUtils.isEmpty(surname))return 3;
        if(!StringUtils.isAlpha(name) || (!StringUtils.isAlpha(surname)&&!AppUtils.isDoubleSurname(surname))) return 5; //checking if only letters in name & surname + checking double surname
        if(!StringUtils.isNumeric(pesel))return 2;
        if(pesel.length() != 11) return 1;
        int i = 0;
        for (Patient aPatientVectorList : patientVectorList) {
            if (aPatientVectorList.getPesel().equals(pesel)) {
                if(!table.getSelectionModel().isSelectionEmpty()){
                    if (table.getSelectedRow() == i) return 5; // Ok to edit pesel of an existing patient, but it cannot match another's patient pesel
                    // 5 = changed pesel, but unique in database
                }
            return 4; // new or changed pesel not unique in database
            }
            i++;
        }
        return 0;
    }

}
