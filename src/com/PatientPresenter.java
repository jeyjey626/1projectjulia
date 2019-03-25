package com;



import org.apache.commons.lang3.StringUtils;
import java.util.Date;

public class PatientPresenter {


    private Patient patient;

    public PatientPresenter()
    {

    }

    public int savePButton(String name, String surname, String pesel, boolean sex, String insurance){
        int check = checkP(pesel);
        if(check == 0){
            Patient patient = new Patient(name, surname, pesel, sex, insurance);
            //TODO: send patient to library (here or in Patient class?)
        }
        return check;
    };

    private int checkP(String pesel){
        int check = 0;
        if(pesel.length() != 11) {check = 1;}
        if(!StringUtils.isNumeric(pesel)){check = 2;}
        return check;


    }
    //TODO: checkout delegating stuff
}
