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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;


public class GUI extends JFrame implements ActionListener {

    //Declarations
    private PatientPresenter patientPresenter;
    private ExaminationPresenter examinationPresenter;
    private PatientListPresenter patientListPresenter; //adding presenter for each panel
    private JButton savePatientButton;
    private JButton abortPatientButton;
    private JTextField nameT;
    private JTextField surnameT;
    private JTextField peselT;
    private JComboBox iBox;
    private ButtonGroup group;
    private JTextField weightT;
    private JTextField bmiT;
    private JTextField heightT;
    private JDatePickerImpl datePicker;
    private JButton saveExamButton;
    private JButton abortExamButton;
    private int yearInit;
    private int monthInit;
    private int dayInit;

    private GUI()
    {
        initUI();
        abortExamButton.setEnabled(true);
    }
    private void initUI()
    {
        int textFieldWidth = 15;
        JFrame frame = new JFrame ("Rejestracja Wyników Badań");
        frame.setVisible(true);



        //------------------------------------------------------------------
        //----------------------- Patient Data Panel -----------------------
        //------------------------------------------------------------------
        //------------------------ Name ------------------------
        JPanel nameCnt = new JPanel();
        JLabel nameL = new JLabel("Imię:", SwingConstants.LEFT);
        nameT = new JTextField(textFieldWidth);
        nameCnt.add(nameL);
        nameCnt.add(nameT);

        //------------------------ Surname ------------------------
        JPanel surnameCnt = new JPanel();
        JLabel surnameL = new JLabel("Nazwisko:", SwingConstants.LEFT);
        surnameT = new JTextField(textFieldWidth);
        surnameCnt.add(surnameL);
        surnameCnt.add(surnameT);

        //------------------------ PESEL ------------------------
        JPanel peselCnt = new JPanel();
        JLabel peselL = new JLabel("PESEL:", SwingConstants.LEFT);
        peselT = new JTextField(textFieldWidth); //TODO: Input verifier
        peselCnt.add(peselL);
        peselCnt.add(peselT);

        //------------------------   Sex    ------------------------
        JPanel sexCnt = new JPanel();
        JLabel sexL = new JLabel("Płeć", SwingConstants.LEFT);
        JRadioButton male = new JRadioButton("mężczyzna", true);
        JRadioButton female = new JRadioButton("kobieta", false);
        group = new ButtonGroup();
        group.add(male);
        group.add(female);
        group.clearSelection();
        sexCnt.add(sexL);
        sexCnt.add(male); //buttongroup is not a gui component, have to add buttons separately
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
        savePatientButton.addActionListener(this);

        abortPatientButton = new JButton("Anuluj");
        abortPatientButton.setEnabled(false);
        abortPatientButton.addActionListener(this);

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
        datePicker.getJFormattedTextField().setColumns(textFieldWidth);
        yearInit = datePicker.getModel().getYear();
        monthInit = datePicker.getModel().getMonth();
        dayInit = datePicker.getModel().getDay();


        dateCnt.add(dateL);
        dateCnt.add(datePicker);

        //------------------------ Weight ------------------------
        JPanel weightCnt = new JPanel();
        JLabel weightL = new JLabel("Waga [kg]:", SwingConstants.LEFT);
        weightT = new JTextField(textFieldWidth);
        weightCnt.add(weightL);
        weightCnt.add(weightT);

        //------------------------ Height ------------------------
        JPanel heightCnt = new JPanel();
        JLabel heightL = new JLabel("Wzrost [cm]:", SwingConstants.LEFT);
        heightT = new JTextField(textFieldWidth);
        heightCnt.add(heightL);
        heightCnt.add(heightT);

        //------------------------ BMI Index ------------------------
        JPanel bmiCnt = new JPanel();
        JLabel bmiL = new JLabel("BMI", SwingConstants.LEFT);
        bmiT = new JTextField(textFieldWidth);
        bmiT.setEditable(false);
        bmiCnt.add(bmiL);
        bmiCnt.add(bmiT);

        //-----------------------Button Panel ------------------------
        JPanel examButtons = new JPanel();

        saveExamButton = new JButton("Zapisz");
        saveExamButton.setEnabled(false);
        saveExamButton.addActionListener(this);

        abortExamButton = new JButton("Anuluj");
        abortExamButton.setEnabled(false);
        abortExamButton.addActionListener(this);
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



        frame.pack();
        frame.setSize(600,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        if(source == abortPatientButton){
            nameT.setText("");
            surnameT.setText("");
            peselT.setText("");
            iBox.setSelectedIndex(0);
            group.clearSelection();
        }
        else if(source == savePatientButton){
            //TODO: Check input and create Patient
        }
        else if(source == saveExamButton) {//TODO: Check input and create exam for patient
        }
        else if(source == abortExamButton){
            bmiT.setText("");
            heightT.setText("");
            weightT.setText("");
            datePicker.getModel().setDate(yearInit, monthInit, dayInit);
            datePicker.getModel().setValue(null);//resetting focus date and clearing value (nothing chosen)
            datePicker.getJFormattedTextField().setText("");


        }
    }

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
