package com;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class patientList extends JTable {
public static JTable patientList;

//todo use this or just presenter methods?

    public patientList(){
        patientList =  new JTable(){

            private static final long serialVersionUID = 1L;
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.setColumnIdentifiers(AppUtils.columns);

        patientList.setModel(defaultTableModel);
        patientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patientList.getSelectionModel().addListSelectionListener(e -> {
           // if(patientList.getSelectedRow() != -1)
        });

    }

    public void addPatient(){

    }

    public void deletePatient(){}
    public void editPatient(){}
}
