package com;

import javax.swing.*;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.*;


public class GUI extends JFrame{

    private PatientPresenter patientPresenter;
    private ExaminationPresenter examinationPresenter;
    private PatientListPresenter patientListPresenter; //adding presenter for each panel

    public GUI()
    {
        initUI();
    }
    private void initUI()
    {
        JFrame frame = new JFrame ("Test");
        frame.setVisible(true);

        //TODO: Draw plan of the GUI to slice them into panels
        //------------------------------------------------------------------
        //----------------------- Patient Data Panel -----------------------
        //------------------------------------------------------------------
        //------------------------ Name ------------------------
        JPanel nameCnt = new JPanel();
        JLabel nameL = new JLabel("Imię:", SwingConstants.LEFT);
        JTextField nameT = new JTextField(15);
        nameCnt.add(nameL);
        nameCnt.add(nameT);

        //------------------------ Surname ------------------------
        JPanel surnameCnt = new JPanel();
        JLabel surnameL = new JLabel("Nazwisko:", SwingConstants.LEFT);
        JTextField surnameT = new JTextField();
        surnameCnt.add(surnameL);
        surnameCnt.add(surnameT);

        //------------------------ PESEL ------------------------
        JPanel peselCnt = new JPanel();
        JLabel peselL = new JLabel("PESEL:", SwingConstants.LEFT);
        JTextField peselT = new JTextField();
        peselCnt.add(peselL);
        peselCnt.add(peselT);

        //------------------------   Sex    ------------------------
        JPanel sexCnt = new JPanel();
        JLabel sexL = new JLabel("Płeć", SwingConstants.LEFT);
        JRadioButton male = new JRadioButton("mężczyzna", true);
        JRadioButton female = new JRadioButton("kobieta", false);
        ButtonGroup group = new ButtonGroup();
        group.add(male);
        group.add(female);
        sexCnt.add(sexL);
        sexCnt.add(male); //buttongroup is not a gui component, have to add buttons separately
        sexCnt.add(female);

        //------------------------ Insurance ------------------------
        JPanel insuranceCnt = new JPanel();
        JLabel insuranceL = new JLabel("Ubezpieczenie:", SwingConstants.LEFT);
        String[] insuranceStrings = {"Brak", "NFZ", "Prywatne"};
        JComboBox iBox = new JComboBox(insuranceStrings); //TODO: Check if it's A-ok
        insuranceCnt.add(insuranceL);
        insuranceCnt.add(iBox);

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

        //------------------------------------------------------------------
        //----------------------- Examination Panel -----------------------
        //------------------------------------------------------------------

        //------------------------ Weight ------------------------
        JPanel weightCnt = new JPanel();
        JLabel weightL = new JLabel("Waga [kg]:", SwingConstants.LEFT);
        JTextField weightT = new JTextField();
        weightCnt.add(weightL);
        weightCnt.add(weightT);

        //------------------------ Height ------------------------
        JPanel heightCnt = new JPanel();
        JLabel heightL = new JLabel("Wzrost [cm]:", SwingConstants.LEFT);
        JTextField heightT = new JTextField();
        heightCnt.add(heightL);
        heightCnt.add(heightT);

        //------------------------ BMI Index ------------------------
        JPanel bmiCnt = new JPanel();
        //---------------------------------------------------------
        //------------------------ Examination Panel main comp  ------------------------
        //---------------------------------------------------------
        JPanel examPanel = new JPanel();
        title = BorderFactory.createTitledBorder("Badanie");
        examPanel.setBorder(title);
        examPanel.add(weightCnt);
        examPanel.add(heightCnt);
        examPanel.add(bmiCnt);



        JPanel basePanel = new JPanel();
        basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));
        add(basePanel);
        frame.setContentPane(basePanel);

        basePanel.add(Box.createVerticalGlue());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setAlignmentX(1f);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        JButton Test1 = new JButton("Ahoj Przygodo");
        bottomPanel.add(Test1);


        basePanel.add(bottomPanel);
        basePanel.add(Box.createRigidArea(new Dimension(15, 15)));
        basePanel.add(patientPanel);
        basePanel.add(examPanel);

        frame.pack();
        frame.setSize(300,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


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
    private void examP()
    {

    }
    private void patientP()
    {







    }
    private void listP()
    {
        JPanel listPanel = new JPanel();
    }

}
