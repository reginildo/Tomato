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

    static ResourceBundle resourceBundle = ResourceBundle
            .getBundle("io.github.reginildo.tomato/Labels", Locales.localeDefault);
    private JRadioButtonMenuItem radioButtonMenuItemPT_BR, radioButtonMenuItemEN_US, radioButtonMenuItemKlingon;
    private JFrameAbout jFrameAbout;

    private int confirmDialog;
    static int countInterations = 1;
    private boolean timeToShortBreak, timeToLongBreak, timeToPomodoro;


    static java.util.Timer timer = null;

    static JButton jButtonStart, jButtonPause, jButtonReset;
    private JMenuItem jMenuItemSettings, jMenuItemQuit, jMenuItemAbout;
    static JLabel jLabelTimeCounter;
    private static JFrameSettings jFrameSettings;

    static String stringValorLongBreak;
    static Calendar timerStart = Calendar.getInstance();
    static Date timerPause;
    static final SimpleDateFormat format = new SimpleDateFormat(
            "mm:ss");
    private JMenu jMenuFile, jMenuLanguage, jMenuHelp;
    private Font font = new Font("Arial", Font.BOLD, 85);
    private ButtonGroup buttonGroupLanguages = new ButtonGroup();

    private JMenuBar jMenuBar = new JMenuBar();
    private JPanel jPanelButtons;
    private JPanel jPanelTimer;


    /**
     * Método responsavel pela criação da GUI principal
     */
    JFrameGui() {
        setLookAndFeel();
        createJMenuItens();
        createJMenus();
        createRadioButtonMenus();
        createJButtons();
        createLabelsForJFrameMain();
        createPanels();
        addComponentsToJMenuLanguague();
        setActionListenersToRadioButtonMenuItens();

        setAllMenus();
        setMouseMotionListenersToMenuItens();
        setAllMenuComponentsActionListeners();
        setInitTimerStart();

        addRadioButtonMenusToButtonGroupLanguages();
        addComponentsToMenuFile();
        addComponentsToJMenuHelp();
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

    private void addComponentsToJMenuHelp() {
        jMenuHelp.add(jMenuItemAbout);
    }

    private void setInitTimerStart() {
        timerStart.set(Calendar.MINUTE,25);
        timerStart.set(Calendar.SECOND,0);
    }

    private void setJLabelTimerCounter() {
        jLabelTimeCounter.setFont(font);
        jLabelTimeCounter.setText(format.format(timerStart.getTime()));
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
        setLayout(new FlowLayout());
        setTitle("Pomodoro tempo");
        setJMenuBar(jMenuBar);
        setContentPane(jPanelTimer);
        setSize(300, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addComponentsToMenuBar() {
        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuLanguage);
        jMenuBar.add(jMenuHelp);
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
        jMenuItemAbout = new JMenuItem("About");
    }

    private void setActionListenersToRadioButtonMenuItens() {
        radioButtonMenuItemPT_BR.addActionListener(e -> {
            resourceBundle = ResourceBundle.
                    getBundle("io.github.reginildo.tomato/Labels",
                            Locales.locale_pt_BR);
            resourceBundle.keySet();
            setVisible(false);
            repaint();
            setVisible(true);
        });
        radioButtonMenuItemEN_US.addActionListener(e -> resourceBundle = ResourceBundle.
                getBundle("io.github.reginildo.tomato/Labels", Locales.locale_en_US));
        radioButtonMenuItemKlingon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resourceBundle = ResourceBundle.
                        getBundle("io.github.reginildo.tomato/Labels",
                                Locales.locale_tlh);
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
        jMenuHelp = new JMenu("Help");
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

    private void startPomodoroTimer() {

        if (timerPause == null) {
            if (jFrameSettings != null) {
                timerStart.set(Calendar.MINUTE, Tomato.getPomodoroTime());
                timerStart.set(Calendar.SECOND, 0);
            } else {
                timerStart.set(Calendar.MINUTE, Tomato.getDefaultPomodoroTime());
                timerStart.set(Calendar.SECOND, 0);
            }
        } else {
            timerStart.setTime(timerPause);
        }
        timer = new Timer();
        TimerTask tarefa = new TimerTask() {
            public void run() {
                try {
                    timerStart.set(Calendar.SECOND, (timerStart.get(Calendar.SECOND) - 1));
                    jLabelTimeCounter.setText(format.format(timerStart.getTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(isTheEnd()){
                    countInterations++;
                    System.out.println("Fim do tempo pomodoro");
                    // rever esse codigo abaixo
                    /*
                    timerStart.set(Calendar.MINUTE, JFrameSettings.jSliderShortBreak.getValue());
                    timerStart.set(Calendar.SECOND, 0);*/
                    timer.cancel();
                    confirmDialog = JOptionPane.showConfirmDialog(null,"Iniciar o intervalo curto?");
                    // Testar esse codigo abaixo
                    if (confirmDialog == JOptionPane.YES_OPTION && (isTimeToShortBreak())){
                        timeToLongBreak = false;
                        timeToPomodoro = false;
                        startShortBreakTimer();
                    }else if(confirmDialog == JOptionPane.YES_OPTION && (isTimeToLongBreak())){
                        timeToShortBreak = false;
                        timeToPomodoro = false;
                            startLongBreakTimer();
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(tarefa, 0, 1000);

    }

    private boolean isTimeToShortBreak(){
        if (countInterations == 2 ||
                countInterations == 4 ||
                countInterations == 6){
            timeToShortBreak = true;
        }
        return timeToShortBreak;
    }

    private boolean isTimeToLongBreak(){
        if (countInterations >= 8){
            timeToLongBreak = true;
        }
        return  timeToLongBreak;

    }
    private boolean isTimeToPomodoro(){
        if (countInterations == 1 ||
                countInterations == 3 ||
                countInterations == 5 ||
                countInterations == 7){
            timeToPomodoro = true;
        }
        return timeToPomodoro;
    }
    private void startShortBreakTimer(){
        if (timerPause == null) {
            if (jFrameSettings != null) {
                timerStart.set(Calendar.MINUTE, Tomato.getShortBreakTime());
                timerStart.set(Calendar.SECOND, 0);
            } else {
                timerStart.set(Calendar.MINUTE, Tomato.getDefaultShortBreakTime());
                timerStart.set(Calendar.SECOND, 0);
            }
        } else {
            timerStart.setTime(timerPause);
        }
        timer = new Timer();
        TimerTask tarefa = new TimerTask() {
            public void run() {
                try {
                    timerStart.set(Calendar.SECOND, (
                            timerStart.get(Calendar.SECOND) - 1));
                    jLabelTimeCounter.setText(format.format(timerStart.getTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(isTheEnd()){
                    countInterations++;
                    System.out.println("Fim do tempo curto");
                    // rever esse codigo abaixo
                    /*timerStart.set(Calendar.MINUTE, JFrameSettings
                            .jSliderShortBreak.getValue());
                    timerStart.set(Calendar.SECOND, 0);*/
                    timer.cancel();
                    confirmDialog = JOptionPane.showConfirmDialog(null,"Iniciar o onovo pomodoro?");
                    // Testar esse codigo abaixo
                    if (confirmDialog == JOptionPane.YES_OPTION && (isTimeToPomodoro())){
                        timeToShortBreak =false;
                        timeToLongBreak = false;
                        startPomodoroTimer();
                    }else if(confirmDialog == JOptionPane.YES_OPTION && (isTimeToLongBreak())){
                        timeToShortBreak = false;
                        timeToPomodoro = false;
                        startLongBreakTimer();
                    }
                }
            }
        };

        timer.scheduleAtFixedRate(tarefa, 0, 1000);

    }

    // depois rever este metodo
    private void startLongBreakTimer(){
        if (timerPause == null) {
            if (jFrameSettings != null) {
                timerStart.set(Calendar.MINUTE, Tomato.getLongBreakTime());
                timerStart.set(Calendar.SECOND, 0);
            } else {
                timerStart.set(Calendar.MINUTE, Tomato.getDefaultLongBreakTime());
                timerStart.set(Calendar.SECOND, 0);
            }
        } else {
            timerStart.setTime(timerPause);
        }
        timer = new Timer();
        TimerTask tarefa = new TimerTask() {
            public void run() {
                try {
                    timerStart.set(Calendar.SECOND, (timerStart.get(Calendar.SECOND) - 1));
                    jLabelTimeCounter.setText(format.format(timerStart.getTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(isTheEnd()){
                    countInterations++;
                    System.out.println("Fim do intervalo longo");
                    // rever esse codigo abaixo
                    /*timerStart.set(Calendar.MINUTE, JFrameSettings.jSliderShortBreak.getValue());
                    timerStart.set(Calendar.SECOND, 0);*/
                    timer.cancel();
                    confirmDialog = JOptionPane.showConfirmDialog(null,"Iniciar um novo ciclo?");
                    if(confirmDialog == JOptionPane.YES_OPTION && (isTimeToPomodoro())){
                        countInterations = 1;
                        timeToLongBreak = false;
                        timeToShortBreak = false;
                        startPomodoroTimer();
                    }
                }
            }
        };

        timer.scheduleAtFixedRate(tarefa, 0, 1000);

    }

    private boolean isTheEnd(){
            return ((timerStart.get(Calendar.MINUTE) == 0) && (timerStart.get(Calendar.SECOND) == 0));

    }



    private void setButtonsActionListeners() {
        jButtonStart.addActionListener(actionEvent -> {
            if (jButtonStart.isEnabled()) {
                Tomato.setPomodoroTime(timerStart.get(Calendar.MINUTE));
                jButtonStart.setEnabled(false);
                jButtonPause.setEnabled(true);
                startPomodoroTimer();
            }
        });
        jButtonPause.addActionListener(actionEvent -> {
            if (jButtonPause.isEnabled()) {
                timerPause = timerStart.getTime();
                // ñao tenho certeza desta linha
                //Tomato.setPomodoroTime(timerStart.get(Calendar.MINUTE));
                timer.cancel();
                jButtonPause.setEnabled(false);
                jButtonReset.setEnabled(true);
                jButtonStart.setEnabled(true);
            }
        });
        jButtonReset.addActionListener(actionEvent -> {
            if(isTimeToShortBreak()){
                timerStart.set(Calendar.MINUTE, Tomato.getShortBreakTime());
            }else if(isTimeToLongBreak()){
                timerStart.set(Calendar.MINUTE, Tomato.getLongBreakTime());
            }else {
                timerStart.set(Calendar.MINUTE, Tomato.getPomodoroTime());
            }
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
            if (jFrameSettings == null) {
                jFrameSettings = new JFrameSettings();
            } else {
                jFrameSettings.setVisible(true);
            }
        });
        jMenuItemQuit.addActionListener(actionEvent -> System.exit(0)
        );
        jMenuItemAbout.addActionListener(e -> jFrameAbout = new JFrameAbout());
    }
}
