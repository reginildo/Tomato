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
class Gui {
    private final JFrame jFrameMain = new JFrame("Tomato");
    private Tomato tomato = new Tomato(25, 5, 15);
    private String stringTempoPomodoro = "Tempo de pomodoro: ";
    private String stringIntervaloCurto = "Intervalo Curto: ";
    private String stringIntervaloLongo = "Intervalo Longo: ";
    private Font fontInfoSettings = new Font("Arial", Font.BOLD, 26);
    private JSpinner.NumberEditor numberEditorSettings;

    private Font font;
    private JMenuBar jMenuBar;
    private JMenu jMenuSettings;
    private JPanel jPanelTimer;
    private JPanel jPanelButtons;
    private JButton jButtonStart;
    private JButton jButtonPause;
    private JButton jButtonReset;
    private JLabel jLabelTimeCounter;

    private JFrame jFrameSettings;
    private JPanel jPanelSettings;
    private JLabel jLabelSettingsTomato, jLabelSettingsLongBreak,
            jLabelSettingsShortBreak;
    private JLabel jLabelInfo;
    private JButton jButtonSettingSave, jButtonSettingsCancel;
    private JSlider jSliderTomato;
    private JSlider jSliderLongBreak;
    private JSlider jSliderShortBreak;
    private String stringCount;

    private Date dateNow;
    private Date dateFinal;


    void createGui() {

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

            dateNow = new Date();
            dateFinal = new Date();
            Thread thread;
            Runnable runnable = () -> {
                dateFinal.setTime(new Date().getTime() - tomato.getPomodoro());
                while (dateNow.compareTo(dateFinal) > -1) {
                    jLabelTimeCounter.setText(String.valueOf(dateNow.getMinutes() + ":" + dateNow.getSeconds()));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };

            thread = new Thread(runnable);
            thread.start();
        });
        this.jButtonPause = new JButton("Pause");
        this.jButtonReset = new JButton("Reset");

        this.jLabelTimeCounter = new JLabel("00:00");
        this.jLabelTimeCounter.setFont(font);
        this.jPanelTimer.setLayout(new FlowLayout());
        this.jPanelTimer.add(jLabelTimeCounter);

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
        this.jLabelSettingsTomato = new JLabel(stringTempoPomodoro);
        this.jLabelSettingsLongBreak = new JLabel(stringIntervaloLongo);
        this.jLabelSettingsShortBreak = new JLabel(stringIntervaloCurto);
        this.jButtonSettingSave = new JButton();
        this.jButtonSettingsCancel = new JButton();
        this.jLabelInfo = new JLabel("Ajuste os tempos:");
        this.jLabelInfo.setFont(fontInfoSettings);


        // JSlider para configuração do tempo de Pomodoro
        this.jSliderTomato = new JSlider(SwingConstants.HORIZONTAL, 0, 50, 25);
        this.jSliderTomato.setMajorTickSpacing(10);
        this.jSliderTomato.setMinorTickSpacing(1);
        this.jSliderTomato.setPaintTicks(true);
        this.jSliderTomato.setPaintLabels(true);
        this.jSliderTomato.setValueIsAdjusting(true);

        this.jSliderTomato.addChangeListener(changeEvent -> {
            String valorTomato = stringTempoPomodoro
                    + String.valueOf(jSliderTomato.getValue() + " minuto(s)");
            tomato.setPomodoro(jSliderTomato.getValue() * 60 * 1000);
            jLabelSettingsTomato.setText(valorTomato);
        });

        // JSlider para configuração do tempo de short break
        this.jSliderShortBreak = new JSlider(SwingConstants.HORIZONTAL, 0, 50, 5);
        this.jSliderShortBreak.setMajorTickSpacing(10);
        this.jSliderShortBreak.setMinorTickSpacing(1);
        this.jSliderShortBreak.setPaintLabels(true);
        this.jSliderShortBreak.setPaintTicks(true);

        this.jSliderShortBreak.addChangeListener(changeEvent -> {
            String stringValorShortBreak = stringIntervaloCurto
                    + String.valueOf(jSliderShortBreak.getValue() + " minuto(s)");
            tomato.setShortBreak(jSliderShortBreak.getValue() * 60 * 1000);
            jLabelSettingsShortBreak.setText(stringValorShortBreak);
        });

        // JSlider para configuração do tempo de long break
        this.jSliderLongBreak = new JSlider(SwingConstants.HORIZONTAL, 0, 50, 15);
        this.jSliderLongBreak.setMajorTickSpacing(10);
        this.jSliderLongBreak.setMinorTickSpacing(1);
        this.jSliderLongBreak.setPaintTicks(true);
        this.jSliderLongBreak.setPaintLabels(true);

        this.jSliderLongBreak.addChangeListener(changeEvent -> {
            String stringValorLongBreak = stringIntervaloLongo
                    + String.valueOf(jSliderLongBreak.getValue() + " minuto(s)");
            tomato.setLongBreak(jSliderLongBreak.getValue() * 60 * 1000);
            jLabelSettingsLongBreak.setText(stringValorLongBreak);
        });

        this.jPanelSettings.add(jLabelInfo);
        this.jPanelSettings.add(jLabelSettingsTomato);
        this.jPanelSettings.add(jSliderTomato);
        this.jPanelSettings.add(jLabelSettingsShortBreak);
        this.jPanelSettings.add(jSliderShortBreak);
        this.jPanelSettings.add(jLabelSettingsLongBreak);
        this.jPanelSettings.add(jSliderLongBreak);


        this.jFrameSettings.setContentPane(jPanelSettings);
        this.jFrameSettings.setSize(300, 300);
        this.jFrameSettings.setResizable(false);
        this.jFrameSettings.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.jFrameSettings.setLocationRelativeTo(null);
        this.jFrameSettings.setTitle("Tomato - Settings");
        this.jFrameSettings.setVisible(true);

    }


}
