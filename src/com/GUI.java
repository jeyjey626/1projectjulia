package com;


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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Properties;
import java.util.Date;

//TODO: when choosing edit -> disable table, save/abort -> enable table patientTable.setRowSelectionAllowed(false);
//TODO: checking patient pesel with database
//TODO: update existing instead of adding new -> Vector.set on index, replaces old object --> changing name of the button?
//TODO: disabling delete (from list) button if nothing's on the list
//TODO: clear form after adding patient
//TODO: checking input in examP (some norms about height and wait -> Ask if that should be only a warning or an error
//TODO: highlighting BMI result in colors according to bmi norms
//TODO: should "save" instantly clear form or leave it to add the exam? (then make an exceprtion for getselected row -1 -> vector get -1 ???
//TODO: Editing patient -> importing data

public class GUI extends JFrame{

    private int checkR;
    //Declarations
    private Presenter presenter;

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
        peselT = new JTextField(TEXTFIELDCOL); //TODO: Input verifier
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
            int checkAndSave = presenter.savePButton(nameT.getText(), surnameT.getText(), peselT.getText(), sex, String.valueOf(iBox.getSelectedItem()), patientTable);
            if(checkAndSave == 0){
                JOptionPane.showMessageDialog(frame,
                        "Dodano Pacjenta");
                //TODO: Should I automatically enable exam panel?
            }
            else if(checkAndSave == 1){
                JOptionPane.showMessageDialog(frame,
                        "Zła długość numeru PESEL",
                        "Błąd",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if (checkAndSave == 2){
                JOptionPane.showMessageDialog(frame,
                        "PESEL powienien zawierać jedynie cyfry",
                        "Błąd",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if(checkAndSave == 3){
                JOptionPane.showMessageDialog(frame,
                        "Uzupełnij wszystkie pola",
                        "Błąd",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if(checkAndSave == 4){
                JOptionPane.showMessageDialog(frame,
                        "Pacjent o takim numerze PESEL już istnieje w bazie",
                        "Błąd",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        abortPatientButton = new JButton("Anuluj");
        abortPatientButton.setEnabled(false);
        abortPatientButton.addActionListener((ActionEvent e) -> clearPatient());

        aButtonCnt.add(savePatientButton);
        aButtonCnt.add(abortPatientButton);


        //---------------------------------------------------------
        //------------------------ Patient Panel main comp ------------------------
        //---------------------------------------------------------
        JPanel patientPanel = new JPanel();
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

        //------------------------ Height ------------------------
        JPanel heightCnt = new JPanel();
        JLabel heightL = new JLabel("Wzrost [cm]:", SwingConstants.LEFT);
        heightT = new JTextField(TEXTFIELDCOL);
        heightCnt.add(heightL);
        heightCnt.add(heightT);

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
            checkValue = presenter.saveEButton(datePicker.getJFormattedTextField().getText(), (Date) datePicker.getModel().getValue(), weightT.getText(), heightT.getText(), patientTable);
            if(checkValue == 1){
                JOptionPane.showMessageDialog(frame,
                        "Uzupełnij wszystkie pola",
                        "Błąd",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if (checkValue == 2){
                JOptionPane.showMessageDialog(frame,
                        "Nieprawidłowy format liczb \n Wzrost musi być liczbą całkowitą \nWaga musi być liczbą dodatnią",
                        "Błąd",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if(checkValue == 0){

            }
        });

        abortExamButton = new JButton("Anuluj");
        abortExamButton.addActionListener(e-> clearExam());
        examButtons.add(saveExamButton);
        examButtons.add(abortExamButton);

        //---------------------------------------------------------
        //------------------------ Examination Panel main comp  ------------------------
        //---------------------------------------------------------
        JPanel examPanel = new JPanel();
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

            JScrollPane patientTableScroll = new JScrollPane(patientTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //-----------------------add/delete/edit buttons-----------------------------
        JPanel listButtCnt = new JPanel();

        JButton addPatientButton = new JButton("Dodaj");
        addPatientButton.addActionListener(e -> AppUtils.setPanelEdit(patientPanel, true));

        JButton deletePatientButton = new JButton("Usuń");
        deletePatientButton.addActionListener(e -> {
            presenter.deletePButton(patientTable);
        });

        JButton editPatientButton = new JButton("Edytuj");
        editPatientButton.addActionListener((ActionEvent e) ->{
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
            //TODO: update existing instead of adding new
        });
        addPatientButton.addActionListener(e->{});
        listButtCnt.add(addPatientButton);
        listButtCnt.add(deletePatientButton);
        listButtCnt.add(editPatientButton);


        JPanel listPanel = new JPanel();
        title = BorderFactory.createTitledBorder("Lista Pacjentów");
        listPanel.setBorder(title);
        listPanel.add(listButtCnt);
        listPanel.add(patientTableScroll);

        JPanel basePanel = new JPanel();
        basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));
        add(basePanel);
        frame.setContentPane(basePanel);

        basePanel.add(Box.createVerticalGlue());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setAlignmentX(1f);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));



        basePanel.add(bottomPanel);
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

    private void clearPatient(){
        nameT.setText("");
        surnameT.setText("");
        peselT.setText("");
        iBox.setSelectedIndex(0);
    } //TODO: Write that in the library
    private void clearExam(){
        bmiT.setText("");
        heightT.setText("");
        weightT.setText("");
        datePicker.getModel().setDate(yearInit, monthInit, dayInit);
        datePicker.getModel().setValue(null);//resetting focus date and clearing value (nothing chosen)
        datePicker.getJFormattedTextField().setText("");
    } //TODO: same as above


    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run() { new GUI();}});

        /*SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run() {
            JFrame frame = new JFrame("Ahoj Przygodo");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(new JLabel("Błagam zadziałaj"));
            frame.pack();
            frame.setVisible(true);
            }
        }
        );*/

    }


}
