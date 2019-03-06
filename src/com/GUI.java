package com;

import javax.swing.*;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.EventQueue;


public class GUI extends JFrame{

    public GUI()
    /*{
        initUI();
    }
    private void initUI()*/
    {
        JFrame frame = new JFrame ("Test");
        frame.setVisible(true);



        JPanel basePanel = new JPanel();


        basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));
        add(basePanel);
        frame.setContentPane(basePanel);

        basePanel.add(Box.createVerticalGlue());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setAlignmentX(1f);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        JButton Test1 = new JButton();
        bottomPanel.add(Test1);


        basePanel.add(bottomPanel);
        basePanel.add(Box.createRigidArea(new Dimension(0, 15)));


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
}
