package io.github.reginildo.tomato.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

@SuppressWarnings("serial")
public class SplashScreen extends JWindow {

    private int duration;

    public SplashScreen(int d) {
        duration = d;
    }

    public void showSplash() {
        JPanel content = (JPanel)getContentPane();
        content.setBackground(Color.white);

        int width = 519;
        int height =520;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width-width)/2;
        int y = (screen.height-height)/2;
        setBounds(x,y,width,height);

        JLabel label = new JLabel(new ImageIcon(getClass().getResource("/io/github/reginildo/tomato/images/devcat.gif")));
        JLabel copyrt = new JLabel
                ("Copyright 2019, Reginildo Sousa", JLabel.CENTER);
        copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        content.add(label, BorderLayout.CENTER);
        content.add(copyrt, BorderLayout.SOUTH);
        Color oraRed = new Color(156, 20, 20,  255);
        content.setBorder(BorderFactory.createLineBorder(oraRed, 10));

        setVisible(true);

        // Espera ate que os recursos estejam carregados
        try { Thread.sleep(duration); } catch (Exception e) {}
        setVisible(false);
    }

    public void showSplashAndExit() {
        showSplash();
    }
}
