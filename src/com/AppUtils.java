package com;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;
import javax.swing.JTextField;

public final class AppUtils {

   public static String[] columns = {"Imię i nazwisko", "Płeć", "PESEL","Ubezpieczenie","Badanie"};

   public  static DefaultTableModel createTableM(Object[][] data){
    return new DefaultTableModel(data, columns){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
   }

   public static void dialogs(int check, JFrame frame){
       if (check == 1) {
           JOptionPane.showMessageDialog(frame,
                   "Zła długość numeru PESEL",
                   "Błąd",
                   JOptionPane.ERROR_MESSAGE);
       } else if (check == 2) {
           JOptionPane.showMessageDialog(frame,
                   "PESEL powienien zawierać jedynie cyfry",
                   "Błąd",
                   JOptionPane.ERROR_MESSAGE);
       } else if (check == 3) {
           JOptionPane.showMessageDialog(frame,
                   "Uzupełnij wszystkie pola",
                   "Błąd",
                   JOptionPane.ERROR_MESSAGE);
       } else if (check == 4) {
           JOptionPane.showMessageDialog(frame,
                   "Pacjent o takim numerze PESEL już istnieje w bazie",
                   "Błąd",
                   JOptionPane.ERROR_MESSAGE);
       }
   }

  /*public static void clearPanel(JPanel panel){
       Component[] components = panel.getComponents();
       for (Component component : components) {
           if (component.getClass().getName().equals("javax.swing.JPanel")) clearPanel((JPanel) panel);
           if(component.getClass().getName().equals("javax.swing.JTextField")) {
               JTextField jTextField = null;
              // jTextField.setText("");
               //component = jTextField;
           }

       }
   }*/

  public static void tableUpdate(Vector<Patient> patientVectorList, JTable table){
      Object[][] data = new Object[patientVectorList.size()][5];
      for(int i =0; i<patientVectorList.size();i++){
          data[i] = patientVectorList.get(i).getArray();
      }

      table.setModel(AppUtils.createTableM(data));
  }

   public static void setPanelEdit(JPanel panel, Boolean isEditable){
       panel.setEnabled(isEditable);

       Component[] components = panel.getComponents();

       for (Component component : components) {
           if (component.getClass().getName().equals("javax.swing.JPanel")) {
               setPanelEdit((JPanel) component, isEditable); //going inside the panel if needed
           }

           component.setEnabled(isEditable);
       }
   } //Turning editing panels on

}
