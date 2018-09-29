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
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

class Gui {
    java.util.Timer timer = null;
    private final JFrame jFrameMain = new JFrame("Tomato");
    private Tomato tomato = new Tomato(25, 5, 15);
    private String stringTempoPomodoro = "Tempo de pomodoro: ";
    private String stringIntervaloCurto = "Intervalo Curto: ";
    private String stringIntervaloLongo = "Intervalo Longo: ";
    private Font fontInfoSettings = new Font("Arial", Font.BOLD, 26);
    private JSpinner.NumberEditor numberEditorSettings;

    private JLabel jLabelTimeCounter;

    private JFrame jFrameSettings;
    private JLabel jLabelSettingsTomato, jLabelSettingsLongBreak,
            jLabelSettingsShortBreak;
    private JSlider jSliderTomato;
    private JSlider jSliderLongBreak;
    private JSlider jSliderShortBreak;
    private String stringCount;

    private Date dateStart;
    private Date dateFinal;
    private Calendar calendarStart;
    private Calendar calendarEnd;

    void createGui() {

        Font font = new Font("Arial", Font.BOLD, 85);
        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenuFile = new JMenu("File");
        JMenuItem jMenuItemSettings = new JMenuItem("Settings");
        JMenuItem jMenuItemQuit = new JMenuItem("Quit");
        jMenuFile.setMnemonic(KeyEvent.VK_F);
        jMenuItemSettings.setMnemonic(KeyEvent.VK_E);
        jMenuItemQuit.setMnemonic(KeyEvent.VK_Q);

        jMenuItemSettings.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                jMenuFile.setToolTipText("Configurar intervalos de tempo");
            }
        });

        jMenuItemSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                createFrameSettings();
            }
        });

        jMenuItemQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        jMenuFile.add(jMenuItemSettings);
        jMenuFile.add(jMenuItemQuit);
        jMenuBar.add(jMenuFile);
        this.jFrameMain.setLayout(new FlowLayout());
        this.jFrameMain.setJMenuBar(jMenuBar);
        JPanel jPanelTimer = new JPanel();
        JButton jButtonStart = new JButton("Start");
        JButton jButtonPause = new JButton("Pause");
        JButton jButtonReset = new JButton("Reset");
        jButtonStart.setMnemonic(KeyEvent.VK_S);
        jButtonReset.setMnemonic(KeyEvent.VK_R);
        jButtonPause.setMnemonic(KeyEvent.VK_P);


        // jbuttonstart action listerner
        jButtonStart.addActionListener(actionEvent -> {
            if (jButtonStart.isEnabled()){
                jButtonStart.setEnabled(false);
                jButtonPause.setEnabled(true);
                iniciarPomodoro();
            }else {
                //timer.schedule();
            }
        });


        jButtonStart.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                jButtonStart.setToolTipText("Iniciar pomodoro");
            }
        });


        jButtonPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (jButtonPause.isEnabled()){
                    jButtonPause.setEnabled(false);
                    jButtonStart.setEnabled(true);
                    jButtonStart.setText("Resume");
                    timer.cancel();
                }
            }
        });
        jButtonPause.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                jButtonPause.setToolTipText("Pausa pomodoro");

            }
        });

        jButtonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jButtonStart.setText("Start");
                iniciarPomodoro();
            }
        });



        jButtonReset.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                jButtonReset.setToolTipText("Reininiando");
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                jButtonReset.setToolTipText("Reiniciar pomodoro");
            }
        });

        this.jLabelTimeCounter = new JLabel("00:00");
        this.jLabelTimeCounter.setFont(font);
        jPanelTimer.setLayout(new FlowLayout());
        jPanelTimer.add(jLabelTimeCounter);

        JPanel jPanelButtons = new JPanel();
        jPanelButtons.add(jButtonStart);
        jPanelButtons.add(jButtonPause);
        jPanelButtons.add(jButtonReset);

        this.jFrameMain.setContentPane(jPanelTimer);
        this.jFrameMain.add(jPanelButtons);

        this.jFrameMain.setSize(300, 300);
        this.jFrameMain.setResizable(false);
        // TODO - configurar para abrir no centro da tela
        this.jFrameMain.setLocationRelativeTo(null);
        this.jFrameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrameMain.setVisible(true);
    }

    private void iniciarPomodoro() {

        timer = null;
        final SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        if (timer == null) {
            timer = new Timer();

            TimerTask tarefa = new TimerTask() {
                /**/
                int contSec = 0;

                public void run() {
                    Date timeStart = new Date();
                    timeStart.setMinutes(0);
                    try {
                        timeStart.setSeconds(contSec++);
                        jLabelTimeCounter.setText(format.format(timeStart.getTime()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            timer.scheduleAtFixedRate(tarefa, 0, 1000);
        }
    }

    private void createFrameSettings() {
        this.jFrameSettings = new JFrame();
        JPanel jPanelSettings = new JPanel();
        jPanelSettings.setLayout(new BoxLayout(jPanelSettings, BoxLayout.Y_AXIS));

        this.jLabelSettingsTomato = new JLabel(stringTempoPomodoro);
        this.jLabelSettingsLongBreak = new JLabel(stringIntervaloLongo);
        this.jLabelSettingsShortBreak = new JLabel(stringIntervaloCurto);
        JButton jButtonSettingSave = new JButton();
        JButton jButtonSettingsCancel = new JButton();
        JLabel jLabelInfo = new JLabel("Ajuste os tempos:");
        jLabelInfo.setFont(fontInfoSettings);
        JButton jButtonSairSettings = new JButton("Sair");
        jButtonSairSettings.setMnemonic(KeyEvent.VK_S);
        jButtonSairSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tomato.setPomodoroTime(jSliderTomato.getValue());
                tomato.setShortBreakTime(jSliderShortBreak.getValue());
                tomato.setLongBreakTime(jSliderLongBreak.getValue());
                jFrameSettings.setVisible(false);
                jFrameSettings.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }
        });
        jButtonSairSettings.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                jButtonSairSettings.setToolTipText("Desejar sair das configurações?");

            }
        });

        configurarJSliders();

        jPanelSettings.add(jLabelInfo);
        jPanelSettings.add(jLabelSettingsTomato);
        jPanelSettings.add(jSliderTomato);
        jPanelSettings.add(jLabelSettingsShortBreak);
        jPanelSettings.add(jSliderShortBreak);
        jPanelSettings.add(jLabelSettingsLongBreak);
        jPanelSettings.add(jSliderLongBreak);
        jPanelSettings.add(jButtonSairSettings);

        this.jFrameSettings.setContentPane(jPanelSettings);
        this.jFrameSettings.setSize(300, 300);
        this.jFrameSettings.setLocation(600, 300);
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
        this.jSliderTomato.setValue(tomato.getPomodoroTime());
        this.jSliderTomato.setValueIsAdjusting(true);

        // JSlider para configuração do tempo de short break
        this.jSliderShortBreak = new JSlider(SwingConstants.HORIZONTAL, 0, 50, 5);
        this.jSliderShortBreak.setMajorTickSpacing(10);
        this.jSliderShortBreak.setMinorTickSpacing(1);
        this.jSliderShortBreak.setPaintLabels(true);
        this.jSliderShortBreak.setPaintTicks(true);
        this.jSliderShortBreak.setValue(tomato.getShortBreakTime());

        // JSlider para configuração do tempo de long break
        this.jSliderLongBreak = new JSlider(SwingConstants.HORIZONTAL, 0, 50, 15);
        this.jSliderLongBreak.setMajorTickSpacing(10);
        this.jSliderLongBreak.setMinorTickSpacing(1);
        this.jSliderLongBreak.setPaintTicks(true);
        this.jSliderLongBreak.setPaintLabels(true);
        this.jSliderLongBreak.setValue(tomato.getLongBreakTime());

        configurarListenersDeJSliders();
    }

    // TODO configurar listener de hover dos JSlides
    private void configurarListenersDeJSliders() {
        this.jSliderTomato.addChangeListener(changeEvent -> {
            String valorTomato = stringTempoPomodoro
                    + String.valueOf(jSliderTomato.getValue() + " minuto(s)");
            tomato.setPomodoroTime(jSliderTomato.getValue());
            jLabelSettingsTomato.setText(valorTomato);
        });

        this.jSliderTomato.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                jSliderTomato.setToolTipText(String.valueOf(jSliderTomato.getValue() + " minutos"));

            }
        });

        this.jSliderShortBreak.addChangeListener(changeEvent -> {
            String stringValorShortBreak = stringIntervaloCurto
                    + String.valueOf(jSliderShortBreak.getValue() + " minuto(s)");
            tomato.setShortBreakTime(jSliderShortBreak.getValue());
            jLabelSettingsShortBreak.setText(stringValorShortBreak);
        });

        this.jSliderShortBreak.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                jSliderShortBreak.setToolTipText(String.valueOf(jSliderShortBreak.getValue() + " minutos"));

            }
        });
        this.jSliderLongBreak.addChangeListener(changeEvent -> {
            String stringValorLongBreak = stringIntervaloLongo
                    + String.valueOf(jSliderLongBreak.getValue() + " minuto(s)");
            tomato.setLongBreakTime(jSliderLongBreak.getValue());
            jLabelSettingsLongBreak.setText(stringValorLongBreak);
        });

        this.jSliderLongBreak.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                jSliderLongBreak.setToolTipText(String.valueOf(jSliderLongBreak.getValue() + " minutos"));

            }
        });
    }
}
