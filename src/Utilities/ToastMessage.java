package Utilities;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public final class ToastMessage extends JDialog {
    private int miliseconds;

   public ToastMessage(String toastString, int time, JFrame frame, Color backColor) {
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
        Point dim = frame.getLocationOnScreen();
        int y = dim.y;
        int half = frame.getSize().height / 2;
        setLocation(dim.x + frame.getSize().width / 2 - getWidth()/2, y + half);
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

