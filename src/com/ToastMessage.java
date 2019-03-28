package com;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

class ToastMessage extends JDialog {
    private int miliseconds;

    ToastMessage(String toastString, int time, JFrame frame, Color backColor) {
        this.miliseconds = time;
        setUndecorated(true);
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(backColor);
        panel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
        getContentPane().add(panel, BorderLayout.CENTER);

        JLabel toastLabel = new JLabel("");
        toastLabel.setText(toastString);
        toastLabel.setFont(new Font("Dialog", Font.BOLD, 12));
        toastLabel.setForeground(Color.WHITE);

        setBounds(100, 100, toastLabel.getPreferredSize().width + 20, 31);


        setAlwaysOnTop(true);
        Dimension dim = frame.getSize();
        int y = dim.height / 2 - getSize().height / 2;
        int half = y / 2;
        setLocation(dim.width / 2 - getSize().width / 2, y -half);
        panel.add(toastLabel);
        setVisible(true);

        new Thread(() -> {
            try {
                Thread.sleep(miliseconds);
                for (double d = 1.0; d > 0.2; d -= 0.1) {
                    Thread.sleep(100);
                    setOpacity((float)d);
                }
                dispose();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

/*import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

class ToastMessage extends JFrame {
    public ToastMessage(final String message) {
        setUndecorated(true);
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 115, 78, 250));
        setLocationRelativeTo(null);

        setSize(300, 50);
        add(new JLabel(message));
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(),
                getHeight(), 20, 20));

    }

    public void display() {
        try {
            setOpacity(1);
            setVisible(true);
            Thread.sleep(1000);

            //hide the toast message in slow motion
            for (double d = 1.0; d > 0.2; d -= 0.1) {
                Thread.sleep(100);
                setOpacity((float)d);
            }

            // set the visibility to false
            setVisible(false);
            dispose();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}*/
