package com;


import org.apache.commons.lang3.StringUtils;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


import javax.swing.*;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Formatter;
import java.util.Properties;
import java.util.Date;


//checklist

//TODO: checking input in examP (some norms about height and weight -> Ask if that should be only a warning or an error -> error
//todo layout


public class GUI extends JFrame{


    //Declarations
    private Presenter presenter;

    private JPanel examPanel;
    private JPanel patientPanel;
    private JPanel listPanel;

    private JTable patientTable;
    private JFrame frame;
    private JMenuItem closeApp;

    private JButton savePatientButton, abortPatientButton;
    private JTextField nameT, surnameT, peselT;
    private JComboBox iBox;
    private ButtonGroup group;
    private JRadioButton male, female;

    private JTextField weightT, bmiT, heightT;
    private JDatePickerImpl datePicker;
    private JButton saveExamButton, abortExamButton;
    private int patientCurrentlyEditedIndex; //todo delete that (not useful
    private JButton deletePatientButton;

    private int yearInit, monthInit, dayInit;

    private static final int TEXTFIELDCOL = 15;

    private GUI()
    {
        initUI();
    }
    private void initUI()
    {

        presenter = new Presenter();
        frame = new JFrame ("Rejestracja Wyników Badań");
        frame.setVisible(true);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Aplikacja");
        menu.setMnemonic(KeyEvent.VK_A);

        closeApp = new JMenuItem("Zamknij ALT+F4");
        closeApp.addActionListener( e -> frame.dispose());
        menu.add(closeApp);
        menuBar.add(menu);

        //------------------------------------------------------------------
        //----------------------- Patient Data Panel -----------------------
        //------------------------------------------------------------------
        patientPanel = new JPanel();
        //------------------------ Name ------------------------
        JPanel nameCnt = new JPanel();
        JLabel nameL = new JLabel("Imię:", SwingConstants.LEFT);
        nameT = new JTextField(TEXTFIELDCOL);
        nameCnt.add(nameL);
        nameCnt.add(nameT);

        //------------------------ Surname ------------------------
        JPanel surnameCnt = new JPanel();
        JLabel surnameL = new JLabel("Nazwisko:", SwingConstants.LEFT);
        surnameT = new JTextField(TEXTFIELDCOL);
        surnameCnt.add(surnameL);
        surnameCnt.add(surnameT);

        //------------------------ PESEL ------------------------
        JPanel peselCnt = new JPanel();
        JLabel peselL = new JLabel("PESEL:", SwingConstants.LEFT);
        peselT = new JTextField(TEXTFIELDCOL);
        peselCnt.add(peselL);
        peselCnt.add(peselT);

        //------------------------   Sex    ------------------------
        JPanel sexCnt = new JPanel();
        JLabel sexL = new JLabel("Płeć", SwingConstants.LEFT);
        male = new JRadioButton("mężczyzna", true);
        female = new JRadioButton("kobieta", false);
        group = new ButtonGroup();
        group.add(male);
        group.add(female);
        sexCnt.add(sexL);
        sexCnt.add(male); //button group is not a gui component, have to add buttons separately
        sexCnt.add(female);

        //------------------------ Insurance ------------------------
        JPanel insuranceCnt = new JPanel();
        JLabel insuranceL = new JLabel("Ubezpieczenie:", SwingConstants.LEFT);
        String[] insuranceStrings = {"Brak", "NFZ", "Prywatne"};
        iBox = new JComboBox(insuranceStrings); //TODO: Check if it's A-ok
        insuranceCnt.add(insuranceL);
        insuranceCnt.add(iBox);

        //---------------------- Add/Abort Buttons --------------------------
        JPanel aButtonCnt = new JPanel();

        savePatientButton = new JButton("Zapisz");
        savePatientButton.setEnabled(false);
        savePatientButton.addActionListener(e -> {
            boolean sex;
            sex = male.isSelected();
            int checkAndSave;
            if(patientTable.getSelectionModel().isSelectionEmpty()) {
                checkAndSave = presenter.savePatient(nameT.getText(), surnameT.getText(), peselT.getText(), sex, String.valueOf(iBox.getSelectedItem()), patientTable);
                if (checkAndSave == 0) {
                    JOptionPane.showMessageDialog(frame,
                            "Dodano Pacjenta");
                    clearPatient(patientPanel);
                    AppUtils.setPanelEdit(patientPanel,false);
                    AppUtils.setPanelEdit(examPanel, false);
                }
                else AppUtils.dialogsPatientDataErrors(checkAndSave, frame);
            }
            else{
                int checkAndEdit = presenter.editPatient(nameT.getText(), surnameT.getText(), peselT.getText(), sex, String.valueOf(iBox.getSelectedItem()), patientTable, patientCurrentlyEditedIndex);
                if(checkAndEdit == 0){
                    JOptionPane.showMessageDialog(frame,
                            "Edytowano Pacjenta");
                    savePatientButton.setText("Zapisz");
                    clearPatient(patientPanel);
                    clearExam(examPanel);
                    AppUtils.setPanelEdit(patientPanel, false);
                    AppUtils.setPanelEdit(examPanel, false);
                }
                else AppUtils.dialogsPatientDataErrors(checkAndEdit, frame);
            }



        });

        abortPatientButton = new JButton("Anuluj");
        abortPatientButton.setEnabled(false);
        abortPatientButton.addActionListener((ActionEvent e) -> {
            clearPatient(patientPanel);
            patientTable.clearSelection();
        });

        aButtonCnt.add(savePatientButton);
        aButtonCnt.add(abortPatientButton);


        //---------------------------------------------------------
        //------------------------ Patient Panel main comp ------------------------
        //---------------------------------------------------------
        
        TitledBorder title;
        title = BorderFactory.createTitledBorder("Dane Pacjenta");
        patientPanel.setBorder(title); //Setting titled border
        patientPanel.add(nameCnt);
        patientPanel.add(surnameCnt);
        patientPanel.add(peselCnt);
        patientPanel.add(sexCnt);
        patientPanel.add(insuranceCnt);
        patientPanel.add(aButtonCnt);
        AppUtils.setPanelEdit(patientPanel, false);

        //------------------------------------------------------------------
        //----------------------- Examination Panel -----------------------
        //------------------------------------------------------------------
        examPanel = new JPanel();
        //------------------------ Date  -------------------------
        JPanel dateCnt = new JPanel();
        JLabel dateL = new JLabel("Data", SwingConstants.LEFT);
        Properties p = new Properties();
        UtilDateModel model = new UtilDateModel();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.getJFormattedTextField().setColumns(TEXTFIELDCOL);
        yearInit = datePicker.getModel().getYear();
        monthInit = datePicker.getModel().getMonth();
        dayInit = datePicker.getModel().getDay();
        datePicker.getJFormattedTextField().setText("");

        dateCnt.add(dateL);
        dateCnt.add(datePicker);

        //------------------------ Weight ------------------------
        JPanel weightCnt = new JPanel();
        JLabel weightL = new JLabel("Waga [kg]:", SwingConstants.LEFT);
        weightT = new JTextField(TEXTFIELDCOL);
        weightCnt.add(weightL);
        weightCnt.add(weightT);
        weightT.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                countBmi(weightT.getText(), heightT.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                countBmi(weightT.getText(), heightT.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                countBmi(weightT.getText(), heightT.getText());
            }
            private void countBmi(String weight, String height){
                boolean weightCh = NumberCheck.isNumber(weight);

                if(StringUtils.isNumeric(height) && weightCh && !StringUtils.isEmpty(weight) && !StringUtils.isEmpty(height)){
                    String bmi = AppUtils.countBmiDisplay(Double.parseDouble(weight), Double.parseDouble(height));
                    bmiT.setText(bmi);
                }
            }
        });
        //------------------------ Height ------------------------
        JPanel heightCnt = new JPanel();
        JLabel heightL = new JLabel("Wzrost [cm]:", SwingConstants.LEFT);
        heightT = new JTextField(TEXTFIELDCOL);
        heightCnt.add(heightL);
        heightCnt.add(heightT);

        heightT.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                countBmi(weightT.getText(), heightT.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                countBmi(weightT.getText(), heightT.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                countBmi(weightT.getText(), heightT.getText());
            }
            private void countBmi(String weight, String height){
                boolean weightCh = NumberCheck.isNumber(weight);

                if(StringUtils.isNumeric(height) && weightCh && !StringUtils.isEmpty(weight) && !StringUtils.isEmpty(height)){
                    String bmi = AppUtils.countBmiDisplay(Double.parseDouble(weight), Double.parseDouble(height));
                    bmiT.setText(bmi);
                }
            }
        });

        //------------------------ BMI Index ------------------------
        JPanel bmiCnt = new JPanel();
        JLabel bmiL = new JLabel("BMI", SwingConstants.LEFT);
        bmiT = new JTextField(TEXTFIELDCOL);
        bmiT.setEditable(false);
        bmiCnt.add(bmiL);
        bmiCnt.add(bmiT);

        //-----------------------Button Panel ------------------------
        JPanel examButtons = new JPanel();

        saveExamButton = new JButton("Zapisz");
        saveExamButton.addActionListener(e -> {
            int checkValue;
            checkValue = presenter.saveExamination(datePicker.getJFormattedTextField().getText(), (Date) datePicker.getModel().getValue(), weightT.getText(), heightT.getText(), patientTable, patientCurrentlyEditedIndex);
            if(checkValue != 0)AppUtils.dialogsExamInpErrors(checkValue, frame);
            else {
                clearExam(examPanel);
                clearPatient(patientPanel);
                savePatientButton.setText("Zapisz");
                AppUtils.setPanelEdit(examPanel,false);
                AppUtils.setPanelEdit(patientPanel,false);
                //todo: dialogs about edit/save

            }
        });

        abortExamButton = new JButton("Anuluj");
        abortExamButton.addActionListener(e-> clearExam(examPanel));
        examButtons.add(saveExamButton);
        examButtons.add(abortExamButton);

        //---------------------------------------------------------
        //------------------------ Examination Panel main comp  ------------------------
        //---------------------------------------------------------
        
        title = BorderFactory.createTitledBorder("Badanie");
        examPanel.setBorder(title);
        examPanel.add(weightCnt);
        examPanel.add(heightCnt);
        examPanel.add(bmiCnt);
        examPanel.add(dateCnt);
        examPanel.add(examButtons);
        AppUtils.setPanelEdit(examPanel, false);


        //---------------------------------------------------------
        //------------------------ Patient List Panel  ------------------------
        //---------------------------------------------------------

        //--------------------Table------------------------------

        patientTable = new JTable(){


                    private static final long serialVersionUID = 1L;
                    @Override
                    public Class getColumnClass(int column) {
                        switch (column) {
                            case 4:
                                return Boolean.class;
                            default:
                                return String.class;
                        }
                    }
                };
                DefaultTableModel defaultTableModel = new DefaultTableModel();
                defaultTableModel.setColumnIdentifiers(AppUtils.columns);

                patientTable.setModel(defaultTableModel);
                patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                patientTable.getSelectionModel().addListSelectionListener(e -> {
                    // if(patientList.getSelectedRow() != -1)
                });
                patientTable.getSelectionModel().addListSelectionListener(e -> {
                    if(patientTable.getSelectedRow()!= -1) {
                        deletePatientButton.setEnabled(true);

                        patientCurrentlyEditedIndex = patientTable.getSelectedRow();
                        AppUtils.setPanelEdit(examPanel, true);
                        datePicker.getComponent(1).setEnabled(true);
                        AppUtils.setPanelEdit(patientPanel, true);

                        nameT.setText(presenter.patientVectorList.get(patientTable.getSelectedRow()).getName());
                        surnameT.setText(presenter.patientVectorList.get(patientTable.getSelectedRow()).getSurname());
                        peselT.setText(presenter.patientVectorList.get(patientTable.getSelectedRow()).getPesel());
                        male.setSelected(presenter.patientVectorList.get(patientTable.getSelectedRow()).getSexBool());
                        female.setSelected(!presenter.patientVectorList.get(patientTable.getSelectedRow()).getSexBool());
                        iBox.setSelectedIndex(presenter.patientVectorList.get(patientTable.getSelectedRow()).getInsuranceInt());
                        savePatientButton.setText("Edytuj");

                        if (presenter.patientVectorList.get(patientTable.getSelectedRow()).isExamination()) {
                            heightT.setText(presenter.patientVectorList.get(patientTable.getSelectedRow()).getExaminationResults().getHeight());
                            weightT.setText(presenter.patientVectorList.get(patientTable.getSelectedRow()).getExaminationResults().getMass());
                            bmiT.setText(presenter.patientVectorList.get(patientTable.getSelectedRow()).getExaminationResults().getBmiString());
                            datePicker.getJFormattedTextField().setText(presenter.patientVectorList.get(patientTable.getSelectedRow()).getExaminationResults().getsDate());
                            LocalDate localDate = presenter.patientVectorList.get(patientTable.getSelectedRow()).getExaminationResults().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            int year  = localDate.getYear();
                            int month = localDate.getMonthValue();
                            int day   = localDate.getDayOfMonth();
                            datePicker.getModel().setDate(year, month-1, day);
                            datePicker.getModel().setSelected(true);
                        }
                        else clearExam(examPanel);
                    }
                    else deletePatientButton.setEnabled(false);
                } );

            JScrollPane patientTableScroll = new JScrollPane(patientTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        //-----------------------add/delete/edit buttons-----------------------------
        JPanel listButtCnt = new JPanel();

        JButton addPatientButton = new JButton("Dodaj");
        addPatientButton.addActionListener(e -> {
            AppUtils.setPanelEdit(patientPanel, true);
            AppUtils.setPanelEdit(examPanel,false);
            clearPatient(patientPanel);
            clearExam(examPanel);
            patientTable.clearSelection();
            savePatientButton.setText("Zapisz");
        });

        deletePatientButton = new JButton("Usuń");
        deletePatientButton.setEnabled(false);
        deletePatientButton.addActionListener(e -> {
            presenter.deletePatient(patientTable);
            clearExam(examPanel);
            clearPatient(patientPanel);
        });

        listButtCnt.add(addPatientButton);
        listButtCnt.add(deletePatientButton);

        listPanel = new JPanel();
        title = BorderFactory.createTitledBorder("Lista Pacjentów");
        listPanel.setBorder(title);
        listPanel.add(listButtCnt);
        listPanel.add(patientTableScroll);

        JPanel basePanel = new JPanel();
        basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));
        add(basePanel);
        frame.setContentPane(basePanel);

        basePanel.add(Box.createVerticalGlue());

        /*JPanel bottomPanel = new JPanel();
        bottomPanel.setAlignmentX(1f);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));*/

        basePanel.add(Box.createRigidArea(new Dimension(15, 15)));
        basePanel.add(patientPanel);
        basePanel.add(examPanel);
        basePanel.add(listPanel);

        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setSize(1000,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        AppUtils.setPanelEdit(examPanel, false);
        datePicker.getComponent(1).setEnabled(false);

    }

    private void clearPatient(JPanel panel){
        AppUtils.clearTextFields(panel);
        iBox.setSelectedIndex(0);
    } 
    
    private void clearExam(JPanel panel){
        AppUtils.clearTextFields(panel);
        datePicker.getModel().setDate(yearInit, monthInit, dayInit);
        datePicker.getModel().setValue(null);//resetting focus date and clearing value (nothing chosen)
        datePicker.getJFormattedTextField().setText("");
    }
    
    public static void main(String[] args){ SwingUtilities.invokeLater(GUI::new); }
}
