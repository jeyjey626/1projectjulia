package com;



import org.apache.commons.lang3.StringUtils;
import java.util.Date;

public class Presenter {


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

    public int savePButton(String name, String surname, String pesel, boolean sex, String insurance){
        int check = checkP(pesel);
        if(check == 0){
            Patient patient = new Patient(name, surname, pesel, sex, insurance);
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
