package com;


import org.apache.commons.lang3.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExaminationPresenter {

    private Examination examination;

    public ExaminationPresenter(){}

    public int saveEButton(String date, String mass, String height){
        int check = 0;
        if(StringUtils.isAllEmpty(mass, height, date)){check = 1;}
        else if(StringUtils.isNumeric(height)){check = 2;}
        return check;
    }
}
