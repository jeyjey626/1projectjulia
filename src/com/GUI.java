package com;

import javax.swing.*;

public class GUI{

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            JFrame frame = new JFrame("Ahoj Przygodo");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(new JLabel("Hello World"));
            frame.pack();
            frame.setVisible(true);
            }
        });
        System.out.println("Hello World!");
    }
}
