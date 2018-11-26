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
import java.util.*;
import java.util.Timer;
import javax.swing.*;

class Gui {



    // TODO darktheme
    // TODO change lookandfell
    // TODO change button for imagebutton
    private Locale localeDefault = Locale.getDefault();
    private Locale locale_pt_BR = new Locale("pt", "BR");
    private Locale locale_en_US = new Locale("en","US");
    private Locale locale_tlh = new Locale("tlh");
    private ResourceBundle
    resourceBundle = ResourceBundle.getBundle("io.github.reginildo.tomato/Labels",localeDefault);
    private java.util.Timer timer = null;
    private final JFrame jFrameMain = new JFrame(resourceBundle.getString("stringTomatoTitle"));
    private Tomato tomato = new Tomato(25, 5, 15);
    //private String stringTempoPomodoro = "Tempo de pomodoro: ";
    private String stringIntervaloCurto =
            resourceBundle.getString("intervaloCurto");
    private String stringIntervaloLongo = resourceBundle.getString("intervaloLongo");
    private String stringValorShortBreak;
    private Font fontInfoSettings = new Font("Arial",
            Font.BOLD, 26);
    private JButton jButtonStart, jButtonPause,jButtonReset;
    private JMenuItem jMenuItemSettings, jMenuItemQuit;
    private JLabel jLabelTimeCounter, jLabelSettingsTomato,
            jLabelSettingsLongBreak, jLabelSettingsShortBreak;

    private JFrame jFrameSettings;
    private JSlider jSliderTomato, jSliderLongBreak, jSliderShortBreak;
    private String stringValorLongBreak;
    private Calendar timerStart = Calendar.getInstance();
    private Date timerPause;
    final SimpleDateFormat format = new SimpleDateFormat(
            "mm:ss");

    /**
    * Método responsavel pela criação da GUI principal
    * */
    void createGui() {
        try {
            for (UIManager.LookAndFeelInfo info :
                    UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
            e.printStackTrace();
        }

        Font font = new Font("Arial", Font.BOLD, 85);
        JMenuBar jMenuBar = new JMenuBar();
        JPanel jPanelButtons = new JPanel();
        JMenu jMenuFile = new JMenu(resourceBundle
                .getString("menuFile"));
        JMenu jMenuLanguage = new JMenu(resourceBundle
                .getString("menuLanguage"));
        ButtonGroup buttonGroupLanguages = new ButtonGroup();
        JRadioButtonMenuItem radioButtonMenuItemPT_BR =
                new JRadioButtonMenuItem(resourceBundle
                        .getString("radioButtonMenuItemPT_BR"));
        radioButtonMenuItemPT_BR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resourceBundle = ResourceBundle.
                        getBundle("io.github.reginildo.tomato/Labels",
                                locale_pt_BR);
                resourceBundle.keySet();

                //Gui.this.jFrameMain.dispose();
                Gui.this.jFrameMain.setVisible(false);
                jFrameMain.repaint();

                Gui.this.jFrameMain.setVisible(true);
            }
        });
        JRadioButtonMenuItem radioButtonMenuItemEN_US =
                new JRadioButtonMenuItem(resourceBundle
                        .getString("radioButtonMenuItemEN_US"));
        radioButtonMenuItemEN_US.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resourceBundle = ResourceBundle.
                        getBundle("io.github.reginildo.tomato/Labels",
                                locale_en_US);
            }
        });
        JRadioButtonMenuItem radioButtonMenuItemKlingon =
                new JRadioButtonMenuItem(resourceBundle
                        .getString("radioButtonMenuItemKlingon"));
        radioButtonMenuItemKlingon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resourceBundle = ResourceBundle.
                        getBundle("io.github.reginildo.tomato/Labels",
                                locale_tlh);
            }
        });
        buttonGroupLanguages.add(radioButtonMenuItemPT_BR);
        buttonGroupLanguages.add(radioButtonMenuItemEN_US);
        buttonGroupLanguages.add(radioButtonMenuItemKlingon);
        jMenuLanguage.add(radioButtonMenuItemPT_BR);
        jMenuLanguage.add(radioButtonMenuItemEN_US);
        jMenuLanguage.add(radioButtonMenuItemKlingon);



        jMenuItemSettings = new JMenuItem(resourceBundle
                .getString("menuItemSetting"));
        jMenuItemQuit = new JMenuItem("Quit");
        jMenuFile.setMnemonic(KeyEvent.VK_F);
        jMenuItemSettings.setMnemonic(KeyEvent.VK_E);
        jMenuItemQuit.setMnemonic(KeyEvent.VK_Q);

        jMenuItemSettings.addMouseMotionListener(
                new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
            }
            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                jMenuFile.setToolTipText("Configurar intervalos de tempo");
            }
        });

        setAllMenuComponentsActionListeners();
        jMenuFile.add(jMenuItemSettings);
        jMenuFile.add(jMenuItemQuit);
        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuLanguage);
        this.jFrameMain.setLayout(new FlowLayout());
        this.jFrameMain.setJMenuBar(jMenuBar);

        JPanel jPanelTimer = new JPanel();
        jButtonStart = new JButton(resourceBundle.getString("buttonStart"));
        jButtonPause = new JButton(resourceBundle.getString("buttonPause"));
        jButtonReset = new JButton(resourceBundle.getString("buttonReset"));
        jButtonStart.setMnemonic(KeyEvent.VK_S);

        jButtonReset.setMnemonic(KeyEvent.VK_R);
        jButtonPause.setMnemonic(KeyEvent.VK_P);
        jButtonPause.setEnabled(false);
        jButtonReset.setEnabled(false);

        setButtonsActionListeners();
        setAllMotionListeners();

        this.jLabelTimeCounter = new JLabel("00:00");
        this.jLabelTimeCounter.setFont(font);
        jPanelTimer.setLayout(new FlowLayout());
        jPanelTimer.add(jLabelTimeCounter);

        jPanelButtons.add(jButtonStart);
        jPanelButtons.add(jButtonPause);
        jPanelButtons.add(jButtonReset);

        this.jFrameMain.setContentPane(jPanelTimer);
        this.jFrameMain.add(jPanelButtons);

        this.jFrameMain.setSize(300, 300);
        this.jFrameMain.setResizable(false);
        this.jFrameMain.setLocationRelativeTo(null);
        this.jFrameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrameMain.setVisible(true);
    }

    private void setAllMotionListeners() {
        jButtonStart.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
            }
            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                jButtonStart.setToolTipText("Iniciar pomodoro");
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

        jMenuItemSettings.addMouseMotionListener(
                new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                jMenuItemSettings.setToolTipText("Enter the " +
                        "pomodoro settings");
            }
        });

        jMenuItemQuit.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                jMenuItemQuit.setToolTipText("Quit the application");
            }
        });
    }

    /**
     * método que inicia o contador pomodoro
     * */
    private void iniciarPomodoro() {
        timer = null;

        if (timer == null) {
            timer = new Timer();
            TimerTask tarefa = new TimerTask() {
                /**/
                int contSec = 0;
                public void run() {

                    //Date timerStart = calendar.getTime();
                    //timerStart.setMinutes(0);
                    if(timerPause== null){
                        timerStart.set(Calendar.MINUTE,0);
                        timerStart.set(Calendar.SECOND,0);
                    }else{
                        timerStart.setTime(timerPause);
                    }
                    try {
                        //timerStart.setSeconds(contSec++);
                        timerStart.set(Calendar.SECOND,
                                timerStart.get(Calendar.SECOND) + contSec++);
                        jLabelTimeCounter.setText(format.format(
                                timerStart.getTime()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            timer.scheduleAtFixedRate(tarefa, 0, 1000);
        }
    }


    /**
     * metodo para criar a tela de configurações do pomodoro
     * */
    private void createFrameSettings() {
        this.jFrameSettings = new JFrame();
        JPanel jPanelSettings = new JPanel();
        jPanelSettings.setLayout(new BoxLayout(jPanelSettings,
                BoxLayout.Y_AXIS));
        this.jLabelSettingsTomato = new JLabel(resourceBundle.getString("stringTempoPomodoro"));
        this.jLabelSettingsLongBreak = new JLabel(
                resourceBundle.getString("intervaloLongo"));
        this.jLabelSettingsShortBreak = new JLabel(
                resourceBundle.getString("intervaloCurto"));
        //JButton jButtonSettingSave = new JButton();
        //JButton jButtonSettingsCancel = new JButton();
        JLabel jLabelInfo = new JLabel("Ajuste os tempos:");
        jLabelInfo.setFont(fontInfoSettings);
        JButton jButtonSairSettings = new JButton(resourceBundle.getString("buttonExit"));
        jButtonSairSettings.setMnemonic(KeyEvent.VK_S);
        jButtonSairSettings.addActionListener(actionEvent -> {
            tomato.setPomodoroTime(jSliderTomato.getValue());
            tomato.setShortBreakTime(jSliderShortBreak.getValue());
            tomato.setLongBreakTime(jSliderLongBreak.getValue());
            jFrameSettings.setVisible(false);
            jFrameSettings.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        });
        jButtonSairSettings.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
            }
            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                jButtonSairSettings.setToolTipText(
                        "Desejar sair das configurações?");
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
        this.jFrameSettings.setTitle(resourceBundle
                .getString("stringTomatoSettingTitle"));
        this.jFrameSettings.setVisible(true);
    }

    private void configurarJSliders() {

        // JSlider para configuração do tempo de Pomodoro
        this.jSliderTomato = new JSlider(
                SwingConstants.HORIZONTAL, 0, 50, 25);
        this.jSliderTomato.setMajorTickSpacing(10);
        this.jSliderTomato.setMinorTickSpacing(1);
        this.jSliderTomato.setPaintTicks(true);
        this.jSliderTomato.setPaintLabels(true);
        this.jSliderTomato.setValue(tomato.getPomodoroTime());
        this.jSliderTomato.setValueIsAdjusting(true);

        // JSlider para configuração do tempo de short break
        this.jSliderShortBreak = new JSlider(
                SwingConstants.HORIZONTAL, 0, 50, 5);
        this.jSliderShortBreak.setMajorTickSpacing(10);
        this.jSliderShortBreak.setMinorTickSpacing(1);
        this.jSliderShortBreak.setPaintLabels(true);
        this.jSliderShortBreak.setPaintTicks(true);
        this.jSliderShortBreak.setValue(tomato.getShortBreakTime());

        // JSlider para configuração do tempo de long break
        this.jSliderLongBreak = new JSlider(
                SwingConstants.HORIZONTAL, 0, 50, 15);
        this.jSliderLongBreak.setMajorTickSpacing(10);
        this.jSliderLongBreak.setMinorTickSpacing(1);
        this.jSliderLongBreak.setPaintTicks(true);
        this.jSliderLongBreak.setPaintLabels(true);
        this.jSliderLongBreak.setValue(tomato.getLongBreakTime());

        configurarListenersDeJSliders();
    }

    // TODO configurar listener de hover dos JSlides..
    private void configurarListenersDeJSliders() {
        this.jSliderTomato.addChangeListener(changeEvent -> {
            String valorTomato = resourceBundle.getString("stringTempoPomodoro")
                    + String.valueOf(jSliderTomato.getValue()+ " " +
                    resourceBundle.getString("minutos"));
            tomato.setPomodoroTime(jSliderTomato.getValue());
            jLabelSettingsTomato.setText(valorTomato);
        });

        this.jSliderTomato.addMouseMotionListener(
                new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
            }
            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                jSliderTomato.setToolTipText(String.valueOf(
                        jSliderTomato.getValue() + " " +
                                resourceBundle.getString("minutos")));
            }
        });

        this.jSliderShortBreak.addChangeListener(changeEvent -> {
            stringValorShortBreak = stringIntervaloCurto
                    + String.valueOf(jSliderShortBreak.getValue() + " " +
                    resourceBundle.getString("minutos"));
            tomato.setShortBreakTime(jSliderShortBreak.getValue());
            jLabelSettingsShortBreak.setText(stringValorShortBreak);
        });

        this.jSliderShortBreak.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
            }
            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                jSliderShortBreak.setToolTipText(
                        String.valueOf(jSliderShortBreak.getValue() +" " +
                                resourceBundle.getString("minutos")));
            }
        });
        this.jSliderLongBreak.addChangeListener(changeEvent -> {
            stringValorLongBreak = stringIntervaloLongo
                    + String.valueOf(jSliderLongBreak.getValue()
                    + " " + resourceBundle.getString("minutos"));
            tomato.setLongBreakTime(jSliderLongBreak.getValue());
            jLabelSettingsLongBreak.setText(stringValorLongBreak);
        });

        this.jSliderLongBreak.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
            }
            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                jSliderLongBreak.setToolTipText(
                        String.valueOf(jSliderLongBreak.getValue()
                                + " " + resourceBundle.getString("minutos")));
            }
        });
    }

    private void setButtonsActionListeners(){
        jButtonStart.addActionListener(actionEvent -> {
            if (jButtonStart.isEnabled()){
                jButtonStart.setEnabled(false);
                jButtonPause.setEnabled(true);
                iniciarPomodoro();
            }
        });
        jButtonPause.addActionListener(actionEvent -> {
            if (jButtonPause.isEnabled()){
                timerPause = timerStart.getTime();
                timer.cancel();
                jButtonPause.setEnabled(false);
                jButtonReset.setEnabled(true);
                jButtonStart.setEnabled(true);
            }
        });
        jButtonReset.addActionListener(actionEvent -> {
            timerStart.set(Calendar.MINUTE, 0);
            timerStart.set(Calendar.SECOND,0);
            timerPause = null;
            timer.cancel();
            jButtonStart.setText(resourceBundle.getString("buttonStart"));
            jButtonPause.setEnabled(true);
            jLabelTimeCounter.setText(format.format(
                    timerStart.getTime()));
            //iniciarPomodoro();
        });
    }

    private void setAllMenuComponentsActionListeners() {
        jMenuItemSettings.addActionListener(actionEvent -> createFrameSettings());
        jMenuItemQuit.addActionListener(actionEvent -> System.exit(0));
    }
}
