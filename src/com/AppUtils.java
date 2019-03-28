package com;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Formatter;
import java.util.Vector;
import javax.swing.JTextField;

public final class AppUtils {

    //Column names
   public static String[] columns = {"Imię i nazwisko", "Płeć", "PESEL","Ubezpieczenie","Badanie"};

   //Dialog text models
   private static final String[] errorPatientText = new String[]{
           "Zła długość numeru PESEL",
           "PESEL powienien zawierać jedynie cyfry",
           "Uzupełnij wszystkie pola",
           "Pacjent o takim numerze PESEL już istnieje w bazie",
           "Imię i Nazwisko powinno składać się jedynie z liter"
   };
   private static final String[] errorExamText = new String[]{
           "Uzupełnij wszystkie pola",
           "Nieprawidłowy format liczb \n Wzrost musi być liczbą całkowitą \nWaga musi być liczbą dodatnią, dopuszczalne jedynie użycie .",
           "Waga poza zakresem - dopuszczalny zakres wagi to 30 - 300kg",
           "Wzrost poza zakresem - dopuszczalny zakres wzrostu to 100 - 250cm"
   };

   //default model of a Table with uneditable cells
   private static DefaultTableModel createTableM(Object[][] data){
    return new DefaultTableModel(data, columns){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
   }

   public static void dialogsPatientDataErrors(int check, JFrame frame) throws InterruptedException {
       //JOptionPane.showMessageDialog(frame, errorPatientText[check-1],"Błąd", JOptionPane.ERROR_MESSAGE);
       new ToastMessage(errorPatientText[check-1], 2000, frame, Color.LIGHT_GRAY);
   }

   public static void dialogsExamInpErrors(int check, JFrame frame){
      // JOptionPane.showMessageDialog(frame, errorExamText[check-1],"Błąd", JOptionPane.ERROR_MESSAGE);
       new ToastMessage(errorExamText[check-1], 2000, frame, Color.LIGHT_GRAY);
   }

   //Clearing text fields of a panel
  public static void clearTextFields(JPanel panel){
       Component[] components = panel.getComponents();
       for (Component component : components) {
           if (component instanceof JPanel) clearTextFields((JPanel) component);
           if(component instanceof JTextField)((JTextField) component).setText("");
       }
   }

   //Updating table with new data
  public static void tableUpdate(Vector<Patient> patientVectorList, JTable table){
      Object[][] data = new Object[patientVectorList.size()][5];
      for(int i =0; i<patientVectorList.size();i++) data[i] = patientVectorList.get(i).getArray();
      table.setModel(AppUtils.createTableM(data));
  }

    //Turning editing panels on/off
   public static void setPanelEdit(JPanel panel, Boolean isEditable){
       panel.setEnabled(isEditable);

       Component[] components = panel.getComponents();

       for (Component component : components) {
           if (component.getClass().getName().equals("javax.swing.JPanel")) {
               setPanelEdit((JPanel) component, isEditable); //going inside the panel if needed
           }

           component.setEnabled(isEditable);
       }
   }

   public static String countBmiDisplay(double mass, double height){
        double heightPerc = height/100;
        double heightDoub = heightPerc*heightPerc;
        double finalBmi = mass/heightDoub;
       Formatter formatter = new Formatter();
       formatter.format("%.2f", finalBmi);
      // DecimalFormat bmiFormat = new DecimalFormat("#.00");
       //bmiFormat.format(finalBmi);
       return String.valueOf(formatter);
    }
}
