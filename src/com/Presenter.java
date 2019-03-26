package com;



import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.Vector;

public class Presenter {

    public static Vector<Patient> patientVectorList = new Vector<>();
    private Patient patient;

    public int saveEButton(String sDate, Date date, String mass, String height){
        int check = 0;

        boolean weightCh = NumberCheck.isNumber(mass);
        if(StringUtils.isEmpty(mass)||StringUtils.isEmpty(height)||StringUtils.isEmpty(sDate)){check = 1;}
        else if(!StringUtils.isNumeric(height) || !weightCh ){check = 2;}
        else { //TODO: saving exams
            // examination = new Examination()
        }
        return check;
    }

    public int savePButton(String name, String surname, String pesel, boolean sex, String insurance, JTable table){
        int check = checkP(pesel);
        if(check == 0){
            Patient patient = new Patient(name, surname, pesel, sex, insurance);
            patientVectorList.add(patient);

            {
                Object[][] data = new Object[patientVectorList.size()][5];
                for(int i =0; i<patientVectorList.size();i++){
                    data[i] = patientVectorList.get(i).getArray();
                }

                table.setModel(AppUtils.createTableM(data));

            }
            //TODO: send patient to library (here or in Patient class?)
        }
        return check;
    }

    private int checkP(String pesel){
        int check = 0;
        if(pesel.length() != 11) {check = 1;}
        if(!StringUtils.isNumeric(pesel)){check = 2;}
        return check;


    }

}
