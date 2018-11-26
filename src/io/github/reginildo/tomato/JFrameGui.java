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

class JFrameGui extends JFrame {
    private Locale localeDefault = Locale.getDefault();
    private Locale locale_pt_BR = new Locale("pt", "BR");
    private Locale locale_en_US = new Locale("en", "US");
    private Locale locale_tlh = new Locale("tlh");
    private ResourceBundle resourceBundle = ResourceBundle
            .getBundle("io.github.reginildo.tomato/Labels", localeDefault);
    JRadioButtonMenuItem radioButtonMenuItemPT_BR,radioButtonMenuItemEN_US, radioButtonMenuItemKlingon;
    JPanel jPanelSettings;
    JButton jButtonSairSettings = new JButton(resourceBundle.getString("buttonExit"));
    JLabel jLabelInfo = new JLabel("Ajuste os tempos:");


    private java.util.Timer timer = null;
    private final JFrame jFrameMain = new JFrame(resourceBundle.
            getString("stringTomatoTitle"));
    private Tomato tomato = new Tomato(25, 5, 15);
    private String stringIntervaloCurto =
            resourceBundle.getString("intervaloCurto");
    private String stringIntervaloLongo = resourceBundle.getString("intervaloLongo");
    private String stringValorShortBreak;
    private Font fontInfoSettings = new Font("Arial",
            Font.BOLD, 26);
    private JButton jButtonStart, jButtonPause, jButtonReset;
    private JMenuItem jMenuItemSettings, jMenuItemQuit;
    private JLabel jLabelTimeCounter, jLabelSettingsTomato,
            jLabelSettingsLongBreak, jLabelSettingsShortBreak;
    private JFrame jFrameSettings;
    private JSlider jSliderTomato, jSliderLongBreak, jSliderShortBreak;
    private String stringValorLongBreak;
    private Calendar timerStart = Calendar.getInstance();
    private Date timerPause;
    private final SimpleDateFormat format = new SimpleDateFormat(
            "mm:ss");
    private JMenu jMenuFile, jMenuLanguage;
    Font font = new Font("Arial", Font.BOLD, 85);
    ButtonGroup buttonGroupLanguages = new ButtonGroup();

    JMenuBar jMenuBar = new JMenuBar();
    JPanel jPanelButtons;
    JPanel jPanelTimer;

    /**
     * Método responsavel pela criação da GUI principal
     */
    JFrameGui() {
        setLookAndFeel();
        setJSliders();
        createJPanelSettings();
        createJMenuItens();
        createJMenus();
        createRadioButtonMenus();
        createJButtons();
        createLabelsForJFrameMain();
        createLabelsForJFrameSettings();
        createPanels();
        createJFrameSettings();
        addComponentsToJMenuLanguague();
        setActionListenersToRadioButtonMenuItens();
        setAllMenus();
        setMouseMotionListenersToMenuItens();
        setAllMenuComponentsActionListeners();
        addRadioButtonMenusToButtonGroupLanguages();
        addComponentsToMenuFile();
        addComponentsToMenuBar();
        setJFrameMain();
        setAllButtons();

        setAllMotionListeners();
        setJLabelTimerCounter();
        setJPanelTimer();
        addComponentsToJPanelTimer();
        addComponentsToJPanelButton();
        addComponentsToJFrameMain();
        setButtonsActionListeners();
    }

    private void createJFrameSettings() {
        jFrameSettings = new JFrame();
    }

    private void createJPanelSettings() {
        jPanelSettings = new JPanel();
        jPanelSettings.setLayout(new BoxLayout(jPanelSettings,
                BoxLayout.Y_AXIS));
    }

    private void setJLabelTimerCounter() {
        jLabelTimeCounter.setFont(font);
    }

    private void addComponentsToJPanelTimer() {
        jPanelTimer.add(jLabelTimeCounter);
    }

    private void setJPanelTimer() {
        jPanelTimer.setLayout(new FlowLayout());

    }

    private void addComponentsToJFrameMain() {
        add(jPanelButtons);
    }

    private void addComponentsToJPanelButton() {
        jPanelButtons.add(jButtonStart);
        jPanelButtons.add(jButtonPause);
        jPanelButtons.add(jButtonReset);
    }

    private void createLabelsForJFrameSettings() {
        jLabelSettingsTomato = new JLabel(resourceBundle.getString("stringTempoPomodoro"));
        jLabelSettingsLongBreak = new JLabel(
                resourceBundle.getString("intervaloLongo"));
        jLabelSettingsShortBreak = new JLabel(
                resourceBundle.getString("intervaloCurto"));
    }

    private void createLabelsForJFrameMain() {
        jLabelTimeCounter = new JLabel("00:00");
    }

    private void createPanels() {
        jPanelButtons = new JPanel();
        jPanelTimer = new JPanel();
    }

    private void setAllButtons() {
        jButtonStart.setMnemonic(KeyEvent.VK_S);
        jButtonReset.setMnemonic(KeyEvent.VK_R);
        jButtonPause.setMnemonic(KeyEvent.VK_P);
        jButtonPause.setEnabled(false);
        jButtonReset.setEnabled(false);
    }

    private void createJButtons() {
        jButtonStart = new JButton(resourceBundle.getString("buttonStart"));
        jButtonPause = new JButton(resourceBundle.getString("buttonPause"));
        jButtonReset = new JButton(resourceBundle.getString("buttonReset"));
    }

    private void setJFrameMain() {
        setContentPane(jPanelTimer);
        setLayout(new FlowLayout());
        setJMenuBar(jMenuBar);
        setSize(300, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addComponentsToMenuBar() {
        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuLanguage);
    }

    private void addComponentsToMenuFile() {
        jMenuFile.add(jMenuItemSettings);
        jMenuFile.add(jMenuItemQuit);
    }

    private void setMouseMotionListenersToMenuItens() {
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
    }

    private void setAllMenus() {
        jMenuFile.setMnemonic(KeyEvent.VK_F);
        jMenuItemSettings.setMnemonic(KeyEvent.VK_E);
        jMenuItemQuit.setMnemonic(KeyEvent.VK_Q);
    }

    private void createJMenuItens() {
        jMenuItemSettings = new JMenuItem(resourceBundle
                .getString("menuItemSetting"));
        jMenuItemQuit = new JMenuItem("Quit");
    }

    private void setActionListenersToRadioButtonMenuItens() {
        radioButtonMenuItemPT_BR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resourceBundle = ResourceBundle.
                        getBundle("io.github.reginildo.tomato/Labels",
                                locale_pt_BR);
                resourceBundle.keySet();

                //JFrameGui.this.jFrameMain.dispose();
                setVisible(false);
                repaint();
                setVisible(true);
            }
        });
        radioButtonMenuItemEN_US.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resourceBundle = ResourceBundle.
                        getBundle("io.github.reginildo.tomato/Labels",
                                locale_en_US);
            }
        });
        radioButtonMenuItemKlingon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resourceBundle = ResourceBundle.
                        getBundle("io.github.reginildo.tomato/Labels",
                                locale_tlh);
            }
        });
    }

    private void addRadioButtonMenusToButtonGroupLanguages() {
        buttonGroupLanguages.add(radioButtonMenuItemPT_BR);
        buttonGroupLanguages.add(radioButtonMenuItemEN_US);
        buttonGroupLanguages.add(radioButtonMenuItemKlingon);
    }

    private void setLookAndFeel() {
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
    }

    private void createRadioButtonMenus() {
        radioButtonMenuItemPT_BR =
                new JRadioButtonMenuItem(resourceBundle
                        .getString("radioButtonMenuItemPT_BR"));
        radioButtonMenuItemEN_US =
                new JRadioButtonMenuItem(resourceBundle
                        .getString("radioButtonMenuItemEN_US"));
        radioButtonMenuItemKlingon =
                new JRadioButtonMenuItem(resourceBundle
                        .getString("radioButtonMenuItemKlingon"));
    }

    private void addComponentsToJMenuLanguague() {
        jMenuLanguage.add(radioButtonMenuItemPT_BR);
        jMenuLanguage.add(radioButtonMenuItemEN_US);
        jMenuLanguage.add(radioButtonMenuItemKlingon);
    }

    private void createJMenus() {
        jMenuFile = new JMenu(resourceBundle
                .getString("menuFile"));
        jMenuLanguage = new JMenu(resourceBundle
                .getString("menuLanguage"));
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
     */
    private void iniciarPomodoro() {
        timer = null;
        if (timerPause == null) {
            timerStart.set(Calendar.MINUTE, jSliderTomato.getValue());
            timerStart.set(Calendar.SECOND, 0);
        } else {
            timerStart.setTime(timerPause);
        }
        if (timer == null) {
            timer = new Timer();
            TimerTask tarefa = new TimerTask() {
                public void run() {
                    try {
                        timerStart.set(Calendar.SECOND, (timerStart.get(Calendar.SECOND) - 1));
                        jLabelTimeCounter.setText(format.format(timerStart.getTime()));
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
     */
    private void createFrameSettings() {


        //JButton jButtonSettingSave = new JButton();
        //JButton jButtonSettingsCancel = new JButton();

        jLabelInfo.setFont(fontInfoSettings);

        jButtonSairSettings.setMnemonic(KeyEvent.VK_S);
        jButtonSairSettings.addActionListener(actionEvent -> {
            tomato.setPomodoroTime(jSliderTomato.getValue());
            tomato.setShortBreakTime(jSliderShortBreak.getValue());
            tomato.setLongBreakTime(jSliderLongBreak.getValue());
            jFrameSettings.setVisible(false);
            timerStart.set(Calendar.MINUTE, 0);
            timerStart.set(Calendar.SECOND, 0);
            timerPause = null;
            timer.cancel();
            jButtonStart.setText(resourceBundle.getString("buttonStart"));
            jButtonPause.setEnabled(false);
            jButtonStart.setEnabled(true);
            jButtonReset.setEnabled(false);
            jLabelTimeCounter.setText(format.format(timerStart.getTime()));
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



        addComponentsToJPanelSettings();

        this.jFrameSettings.setContentPane(jPanelSettings);
        this.jFrameSettings.setSize(300, 300);
        this.jFrameSettings.setLocation(600, 300);
        this.jFrameSettings.setResizable(false);
        this.jFrameSettings.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.jFrameSettings.setTitle(resourceBundle
                .getString("stringTomatoSettingTitle"));
        this.jFrameSettings.setVisible(true);
    }

    private void addComponentsToJPanelSettings() {
        jPanelSettings.add(jLabelInfo);
        jPanelSettings.add(jLabelSettingsTomato);
        jPanelSettings.add(jSliderTomato);
        jPanelSettings.add(jLabelSettingsShortBreak);
        jPanelSettings.add(jSliderShortBreak);
        jPanelSettings.add(jLabelSettingsLongBreak);
        jPanelSettings.add(jSliderLongBreak);
        jPanelSettings.add(jButtonSairSettings);
    }

    private void setJSliders() {

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


    private void configurarListenersDeJSliders() {
        this.jSliderTomato.addChangeListener(changeEvent -> {
            String valorTomato = resourceBundle.getString("stringTempoPomodoro")
                    + String.valueOf(jSliderTomato.getValue() + " " +
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
                        String.valueOf(jSliderShortBreak.getValue() + " " +
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

    private void setButtonsActionListeners() {
        jButtonStart.addActionListener(actionEvent -> {
            if (jButtonStart.isEnabled()) {
                jButtonStart.setEnabled(false);
                jButtonPause.setEnabled(true);
                iniciarPomodoro();
            }
        });
        jButtonPause.addActionListener(actionEvent -> {
            if (jButtonPause.isEnabled()) {
                timerPause = timerStart.getTime();
                timer.cancel();
                jButtonPause.setEnabled(false);
                jButtonReset.setEnabled(true);
                jButtonStart.setEnabled(true);
            }
        });
        jButtonReset.addActionListener(actionEvent -> {
            timerStart.set(Calendar.MINUTE, 0);
            timerStart.set(Calendar.SECOND, 0);
            timerPause = null;
            timer.cancel();
            jButtonStart.setText(resourceBundle.getString("buttonStart"));
            jButtonPause.setEnabled(false);
            jButtonStart.setEnabled(true);
            jButtonReset.setEnabled(false);
            jLabelTimeCounter.setText(format.format(
                    timerStart.getTime()));
        });
    }

    private void setAllMenuComponentsActionListeners() {
        jMenuItemSettings.addActionListener(actionEvent -> {
            createFrameSettings();
        });
        jMenuItemQuit.addActionListener(actionEvent -> {

                    System.exit(0);
                }
        );
    }
}
