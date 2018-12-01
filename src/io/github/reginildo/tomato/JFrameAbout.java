package io.github.reginildo.tomato;

import javax.swing.*;
import java.awt.*;

public class JFrameAbout extends JFrame{
    JPanel jPanelAboutMain;
    JLabel jLabelTitle, jLabelName,jLabelEmail,jLabelSite;
    Font fontLabelTitle = new Font("Arial", Font.BOLD, 18);
    Font fontLabelDetails = new Font("Arial", Font.PLAIN, 12);
    ImageIcon icon;
    JLabel labelIcon;

    JFrameAbout(){

        setJPanelAboutMain();
        addComponentsToJFrameAbout();
        setJFrameAbout();
    }

    private void setJLabelIcon() {
        createIcon();
        labelIcon = new JLabel();
        labelIcon.setIcon(icon);
    }

    private void createIcon() {
        icon = new ImageIcon(getClass().getResource(
                "/io/github/reginildo/" +
                        "tomato/images/icon.png"));
    }

    private void addComponentsToJFrameAbout() {
        add(jPanelAboutMain);
    }

    private void setJFrameAbout() {
        setJLabelIcon();
        setContentPane(jPanelAboutMain);
        add(labelIcon);
        setSize(300, 300);
        setLocation(600, 300);
        setResizable(false);
        setTitle(JFrameGui.resourceBundle
                .getString("stringTomatoSettingTitle"));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);
    }

    private void addComponentsToJPanelAboutMain() {
        jPanelAboutMain.add(jLabelTitle);
        jPanelAboutMain.add(jLabelName);
        jPanelAboutMain.add(jLabelEmail);
        jPanelAboutMain.add(jLabelSite);
    }

    private void setJLabelDetails() {
        jLabelName.setFont(fontLabelDetails);
    }

    private void setJLabelTitle() {
        createJLabels();
        jLabelTitle.setText("Tomato - Pomodoro Timer");
        jLabelTitle.setFont(fontLabelTitle);
    }

    private void createJLabels() {
        jLabelTitle = new JLabel("Tomato - Pomodoro Timer");
        jLabelName = new JLabel("Developer: Reginildo Sousa");
        jLabelEmail = new JLabel("Email: reginildosousa01@gmail.com");
        jLabelSite = new JLabel("Website: https://reginildo.github.io");
    }

    private void setJPanelAboutMain() {

        setJLabelTitle();
        setJLabelDetails();
        jPanelAboutMain = new JPanel();
        addComponentsToJPanelAboutMain();
    }

}
