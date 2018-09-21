/*
 * Copyright (C) 2018 Reginildo Sousa
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.reginildo.tomato;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 * @author reginildo
 */
public class Gui {

    private final JFrame jFrameMain = new JFrame("Tomato");
    private JSpinner.NumberEditor numberEditorSettings;

    private Font font;
    private JMenuBar jMenuBar;
    private JMenu jMenuSettings;
    private JPanel jPanelTimer;
    private JPanel jPanelButtons;
    private JButton jButtonStart;
    private JButton jButtonPause;
    private JButton jButtonReset;
    private JLabel jLabelSettings;

    private JFrame jFrameSettings;
    private JPanel jPanelSettings;
    private JLabel jLabelSettingsTomato, jLabelSettingsLongBreak,
            jLabelSettingsShortBreak;
    private JButton jButtonSettingSave, jButtonSettingsCancel;
    private JSlider jSliderTomato;
    private JSlider jSliderLongBreak;
    private JSlider jSliderShortBreak;
    private String stringCount;


    public void createGui() {

        this.font = new Font("Arial", Font.BOLD, 85);
        this.jMenuBar = new JMenuBar();
        this.jMenuSettings = new JMenu("Settings");

        this.jMenuSettings.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                createFrameSettings();
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });

        this.jMenuBar.add(jMenuSettings);
        this.jFrameMain.setLayout(new FlowLayout());
        this.jFrameMain.setJMenuBar(jMenuBar);
        this.jPanelTimer = new JPanel();
        this.jButtonStart = new JButton("Start");
        // actionlistener de jbuttonstart
        this.jButtonStart.addActionListener(actionEvent -> {

            /*
            * */
            Thread thread;
            thread = new Thread(() -> {
                Date dateNow = new Date();
                Date dateFinal = new Date();
                dateFinal.setTime(new Date().getTime() - 1500000);
                while (dateFinal.compareTo(dateNow) < -1) {
                    try {

                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
            thread.start();
        });
        this.jButtonPause = new JButton("Pause");
        this.jButtonReset = new JButton("Reset");

        this.jLabelSettings = new JLabel("00:00");
        this.jLabelSettings.setFont(font);
        this.jPanelTimer.setLayout(new FlowLayout());
        this.jPanelTimer.add(jLabelSettings);

        this.jPanelButtons = new JPanel();
        this.jPanelButtons.add(jButtonStart);
        this.jPanelButtons.add(jButtonPause);
        this.jPanelButtons.add(jButtonReset);

        this.jFrameMain.setContentPane(jPanelTimer);
        this.jFrameMain.add(jPanelButtons);

        this.jFrameMain.setSize(300, 300);
        this.jFrameMain.setResizable(false);
        this.jFrameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrameMain.setLocationRelativeTo(null);

        this.jFrameMain.setVisible(true);
    }

    private void createFrameSettings() {
        this.jFrameSettings = new JFrame();
        this.jPanelSettings = new JPanel();
        this.jLabelSettingsTomato = new JLabel();
        this.jLabelSettingsLongBreak = new JLabel();
        this.jLabelSettingsShortBreak = new JLabel();
        this.jButtonSettingSave = new JButton();
        this.jButtonSettingsCancel = new JButton();

        this.jPanelSettings.add(jLabelSettingsTomato);

        this.jPanelSettings.add(jLabelSettingsLongBreak);
        this.jPanelSettings.add(jLabelSettingsShortBreak);

        // JSlider para configuração do tempo de Pomodoro
        this.jSliderTomato = new JSlider(SwingConstants.HORIZONTAL, 0, 50, 25);
        this.jSliderTomato.setMajorTickSpacing(10);
        this.jSliderTomato.setPaintTicks(true);

        // JSlider para configuração do tempo de long break
        this.jSliderLongBreak = new JSlider(SwingConstants.HORIZONTAL, 0, 50, 25);
        this.jSliderLongBreak.setMajorTickSpacing(10);
        this.jSliderLongBreak.setPaintTicks(true);

        // JSlider para configuração do tempo de short break
        this.jSliderShortBreak = new JSlider(SwingConstants.HORIZONTAL, 0, 50, 25);
        this.jSliderShortBreak.setMajorTickSpacing(10);
        this.jSliderShortBreak.setPaintTicks(true);

        this.jPanelSettings.add(jSliderTomato);
        this.jPanelSettings.add(jSliderLongBreak);
        this.jPanelSettings.add(jSliderShortBreak);

        this.jFrameSettings.setContentPane(jPanelSettings);
        this.jFrameSettings.setSize(300, 300);
        this.jFrameSettings.setResizable(false);
        this.jFrameSettings.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.jFrameSettings.setLocationRelativeTo(null);
        this.jFrameSettings.setTitle("Tomato - Settings");
        this.jFrameSettings.setVisible(true);

    }


}
