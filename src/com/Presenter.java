package com;



import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.Vector;

public class Presenter {

    public Vector<Patient> patientVectorList = new Vector<>();
    private Patient patient;

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
        return check;
    }

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

    public int savePButton(String name, String surname, String pesel, boolean sex, String insurance, JTable table){
        int check = checkP(name, surname, pesel);
        if(check == 0){

            Patient patient = new Patient(name, surname, pesel, sex, insurance);
            patientVectorList.add(patient);
            //table.clearSelection();
            //patientVectorList.get(-1);
            AppUtils.tableUpdate(patientVectorList, table);
            //TODO: send patient to library (here or in Patient class?)
        }
        return check;
    }



    public void deletePButton(JTable table){
        if(table.getSelectedRow() != -1) {
            patientVectorList.remove(table.getSelectedRow());
            AppUtils.tableUpdate(patientVectorList, table);
        }
    }

    private int checkP(String name, String surname, String pesel){
        int check = 0;
        if(StringUtils.isEmpty(pesel)||StringUtils.isEmpty(name)||StringUtils.isEmpty(surname)){check = 3;}
        else if(pesel.length() != 11) {check = 1;}
        else if(!StringUtils.isNumeric(pesel)){check = 2;}
        //TODO: Checking pesel in database
        return check;


    }

}
