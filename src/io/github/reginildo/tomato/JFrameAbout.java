package io.github.reginildo.tomato;

import javax.swing.*;
import java.awt.*;

class JFrameAbout extends JFrame{
    private JPanel jPanelAboutMain;
    private JLabel jLabelTitle, jLabelName,jLabelEmail,jLabelSite, labelIcon;
    private final Font fontLabelTitle = new Font("Arial", Font.BOLD, 18);
    private final Font fontLabelDetails = new Font("Arial", Font.PLAIN, 12);
    private final ImageIcon icon = new ImageIcon(getClass().getResource(
                "/io/github/reginildo/" +
                        "tomato/images/icon.png"));

    JFrameAbout(){
        setJPanels();
        add(jPanelAboutMain);
        setJLabelIcon();
        setContentPane(jPanelAboutMain);
        add(labelIcon);
        setSize(300, 300);
        setLocation(600, 300);
        setResizable(false);
        setTitle(JFrameTomatoMain.resourceBundle
                .getString("stringTomatoSettingTitle"));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);
    }

    private void setJLabelIcon() {
        labelIcon = new JLabel();
        labelIcon.setIcon(icon);
    }

    private void setJPanels() {
        setJLabels();
        jPanelAboutMain = new JPanel();
        jPanelAboutMain.add(jLabelTitle);
        jPanelAboutMain.add(jLabelName);
        jPanelAboutMain.add(jLabelEmail);
        jPanelAboutMain.add(jLabelSite);
    }

    private void setJLabels() {
        jLabelTitle = new JLabel("Tomato - Pomodoro Timer");
        jLabelTitle.setText("Tomato - Pomodoro Timer");
        jLabelTitle.setFont(fontLabelTitle);
        jLabelName = new JLabel("Developer: Reginildo Sousa");
        jLabelName.setFont(fontLabelDetails);
        jLabelEmail = new JLabel("Email: reginildosousa01@gmail.com");
        jLabelSite = new JLabel("Website: https://reginildo.github.io");
    }
}
