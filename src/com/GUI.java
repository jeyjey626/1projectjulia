package com;

import javax.swing.*;

public class GUI extends JFrame{

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            JFrame frame = new JFrame("Ahoj Przygodo");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(new JLabel("Błagam zadziałaj"));
            frame.pack();
            frame.setVisible(true);
            }
        });
    }
}
