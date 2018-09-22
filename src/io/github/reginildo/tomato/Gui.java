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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
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
    private JButton jButtonSairSettings;
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

    private Date dateStart;
    private Date dateFinal;
    private Calendar calendarStart;
    private Calendar calendarEnd;

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
            iniciarPomodoro();
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
        this.jFrameMain.setVisible(true);
    }

    private void iniciarPomodoro() {
        dateStart = new Date();
        dateStart.setHours(0);
        dateStart.setMinutes(0);
        calendarStart = Calendar.getInstance();
        calendarStart.setTime(dateStart);

        dateFinal = new Date();
        dateFinal = dateStart;
        dateFinal.setTime(dateFinal.getTime() + tomato.getPomodoroTime());

        calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(dateFinal);

        Thread thread;
        Runnable runnable = () -> {

            while (calendarStart.before(dateFinal)) {
                jLabelTimeCounter.setText(String.valueOf(dateStart.getMinutes() + ":" + dateStart.getSeconds()));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread = new Thread(runnable);
        thread.start();
    }

    private void createFrameSettings() {
        this.jFrameSettings = new JFrame();
        this.jPanelSettings = new JPanel();
        this.jPanelSettings.setLayout(new BoxLayout(this.jPanelSettings, BoxLayout.Y_AXIS ));

        this.jLabelSettingsTomato = new JLabel(stringTempoPomodoro);
        this.jLabelSettingsLongBreak = new JLabel(stringIntervaloLongo);
        this.jLabelSettingsShortBreak = new JLabel(stringIntervaloCurto);
        this.jButtonSettingSave = new JButton();
        this.jButtonSettingsCancel = new JButton();
        this.jLabelInfo = new JLabel("Ajuste os tempos:");
        this.jLabelInfo.setFont(fontInfoSettings);
        this.jButtonSairSettings = new JButton("Sair");
        this.jButtonSairSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tomato.setPomodoroTime(jSliderTomato.getValue() * 60 * 1000);
                tomato.setShortBreakTime(jSliderShortBreak.getValue() * 60 * 1000);
                tomato.setLongBreakTime(jSliderLongBreak.getValue() * 60 * 1000);
                jFrameSettings.setVisible(false);
                jFrameSettings.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }
        });

        configurarJSliders();

        this.jPanelSettings.add(jLabelInfo);
        this.jPanelSettings.add(jLabelSettingsTomato);
        this.jPanelSettings.add(jSliderTomato);
        this.jPanelSettings.add(jLabelSettingsShortBreak);
        this.jPanelSettings.add(jSliderShortBreak);
        this.jPanelSettings.add(jLabelSettingsLongBreak);
        this.jPanelSettings.add(jSliderLongBreak);
        this.jPanelSettings.add(jButtonSairSettings);

        this.jFrameSettings.setContentPane(jPanelSettings);
        this.jFrameSettings.setSize(300, 300);
        this.jFrameSettings.setLocation(600,300);
        this.jFrameSettings.setResizable(false);
        this.jFrameSettings.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.jFrameSettings.setTitle("Tomato - Settings");
        this.jFrameSettings.setVisible(true);
    }

    private void configurarJSliders() {

        // JSlider para configuração do tempo de Pomodoro
        this.jSliderTomato = new JSlider(SwingConstants.HORIZONTAL, 0, 50, 25);
        this.jSliderTomato.setMajorTickSpacing(10);
        this.jSliderTomato.setMinorTickSpacing(1);
        this.jSliderTomato.setPaintTicks(true);
        this.jSliderTomato.setPaintLabels(true);
        this.jSliderTomato.setValue(tomato.getPomodoroTime() / 60 / 1000);
        this.jSliderTomato.setValueIsAdjusting(true);

        // JSlider para configuração do tempo de short break
        this.jSliderShortBreak = new JSlider(SwingConstants.HORIZONTAL, 0, 50, 5);
        this.jSliderShortBreak.setMajorTickSpacing(10);
        this.jSliderShortBreak.setMinorTickSpacing(1);
        this.jSliderShortBreak.setPaintLabels(true);
        this.jSliderShortBreak.setPaintTicks(true);
        this.jSliderShortBreak.setValue(tomato.getShortBreakTime() / 60 / 1000);

        // JSlider para configuração do tempo de long break
        this.jSliderLongBreak = new JSlider(SwingConstants.HORIZONTAL, 0, 50, 15);
        this.jSliderLongBreak.setMajorTickSpacing(10);
        this.jSliderLongBreak.setMinorTickSpacing(1);
        this.jSliderLongBreak.setPaintTicks(true);
        this.jSliderLongBreak.setPaintLabels(true);
        this.jSliderLongBreak.setValue(tomato.getLongBreakTime() / 60 / 1000);

        configurarListenersDeJSliders();
    }

    private void configurarListenersDeJSliders() {
        this.jSliderTomato.addChangeListener(changeEvent -> {
            String valorTomato = stringTempoPomodoro
                    + String.valueOf(jSliderTomato.getValue() + " minuto(s)");
            tomato.setPomodoroTime(jSliderTomato.getValue() * 60 * 1000);
            jLabelSettingsTomato.setText(valorTomato);
        });

        this.jSliderShortBreak.addChangeListener(changeEvent -> {
            String stringValorShortBreak = stringIntervaloCurto
                    + String.valueOf(jSliderShortBreak.getValue() + " minuto(s)");
            tomato.setShortBreakTime(jSliderShortBreak.getValue() * 60 * 1000);
            jLabelSettingsShortBreak.setText(stringValorShortBreak);
        });
        this.jSliderLongBreak.addChangeListener(changeEvent -> {
            String stringValorLongBreak = stringIntervaloLongo
                    + String.valueOf(jSliderLongBreak.getValue() + " minuto(s)");
            tomato.setLongBreakTime(jSliderLongBreak.getValue() * 60 * 1000);
            jLabelSettingsLongBreak.setText(stringValorLongBreak);
        });
    }
}
